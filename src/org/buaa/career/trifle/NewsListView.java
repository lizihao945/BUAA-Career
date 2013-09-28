package org.buaa.career.trifle;

import org.buaa.career.R;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class NewsListView extends ListView {
	public interface OnRefreshListener {
		public void onRefresh();
	}

	private static final float PULL_RESISTANCE = 1.7f;
	private static final int BOUNCE_ANIMATION_DURATION = 700;
	private static final int BOUNCE_ANIMATION_DELAY = 100;
	private static final float BOUNCE_OVERSHOOT_TENSION = 1.4f;
	private static final int ROTATE_ARROW_ANIMATION_DURATION = 250;

	public static final int REFRESH_STATE_PULL = 517;
	public static final int REFRESH_STATE_RELEASE = 519;
	public static final int REFRESH_STATE_ING = 520;

	private OnRefreshListener mRefreshListener;
	private LinearLayout mHeaderContainer;
	private LinearLayout mHeader;
	private TextView mHeaderStaticText, mLastTimeText;
	private ProgressBar mProgressBar;
	private int mCurrScrollState;
	private int mLastRefreshState;
	private float mHeaderPadding;
	private float mLastTouchY;
	private float mScrollStartY;
	private float mMeasuredHeaderHeight;

	public NewsListView(Context context) {
		this(context, null, 0);
	}

	public NewsListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public NewsListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		setOverScrollMode(View.OVER_SCROLL_NEVER);
		setVerticalFadingEdgeEnabled(false);

		mHeaderContainer = (LinearLayout) LayoutInflater.from(context).inflate(
				R.layout.refresh_header, null);
		mHeader = (LinearLayout) mHeaderContainer.findViewById(R.id.header);
		mHeaderStaticText = (TextView) findViewById(R.id.refresh_text);
		mLastTimeText = (TextView) findViewById(R.id.last_time_text);

		addHeaderView(mHeaderContainer, null, false);

		mHeader.getViewTreeObserver().addOnGlobalLayoutListener(new TmpOnGlobalLayoutListener());
		setRefreshState(REFRESH_STATE_PULL);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (getFirstVisiblePosition() == 0)
				mScrollStartY = mLastTouchY = ev.getY();
			else
				mLastTouchY = -1;
			break;
		case MotionEvent.ACTION_UP:
			if (mLastTouchY == -1)
				break;
			else if (mLastRefreshState == REFRESH_STATE_RELEASE) {
				doBounceUp();
				setRefreshState(REFRESH_STATE_ING);
			} else if (mLastRefreshState == REFRESH_STATE_PULL || getFirstVisiblePosition() == 0) {
				doResetHeader();
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (mLastTouchY == -1 || getFirstVisiblePosition() != 0)
				break;
			float diffY = ev.getY() - mLastTouchY;
			mLastTouchY = ev.getY();
			// if (diffY > 0)
//			diffY /= 3;
			System.out.println(mHeaderPadding);
			int tmp = Math.max(Math.round(mHeaderPadding + diffY), -mHeader.getHeight());
			if (tmp != mHeaderPadding && mLastRefreshState != REFRESH_STATE_ING) {
				setHeaderPadding(tmp);
				if (mLastRefreshState == REFRESH_STATE_PULL && mHeaderPadding > 0) {
					setRefreshState(REFRESH_STATE_RELEASE);
				} else if (mLastRefreshState == REFRESH_STATE_RELEASE && mHeaderPadding < 0) {
					setRefreshState(REFRESH_STATE_PULL);
				}
			}
			break;
		}
		return super.onTouchEvent(ev);
	}

	public void setOnRefreshListener(OnRefreshListener listener) {
		mRefreshListener = listener;
	}

	private void doResetHeader() {
		// initial reset
		if (getFirstVisiblePosition() != 0) {
			setHeaderPadding(-mHeader.getHeight());
			setRefreshState(REFRESH_STATE_PULL);
		} else { // reset from pull state
			System.out.println("pull not enough");
			doBounceUp();
		}

	}

	private void doBounceUp() {
		int deltaY;
		if (mLastRefreshState == REFRESH_STATE_RELEASE)
			// bounce up so that header is just visible
			deltaY = -mHeaderContainer.getHeight() + mHeader.getHeight();
		else
			// bounce up so that header is not visible
			deltaY = -mHeader.getHeight() - mHeader.getTop();
		TranslateAnimation bounceAnimation = new TranslateAnimation(TranslateAnimation.ABSOLUTE, 0,
				TranslateAnimation.ABSOLUTE, 0, TranslateAnimation.ABSOLUTE, 0,
				TranslateAnimation.ABSOLUTE, deltaY);

		bounceAnimation.setDuration(BOUNCE_ANIMATION_DURATION);
		bounceAnimation.setFillEnabled(true);
		bounceAnimation.setFillAfter(false);
		bounceAnimation.setFillBefore(true);
		bounceAnimation.setInterpolator(new OvershootInterpolator(BOUNCE_OVERSHOOT_TENSION));
		bounceAnimation.setAnimationListener(new HeaderAnimationListener(deltaY));

		startAnimation(bounceAnimation);
	}

	private void setHeaderPadding(float padding) {
		mHeaderPadding = padding;

		MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) mHeader.getLayoutParams();
		mlp.setMargins(0, Math.round(padding), 0, 0);
		mHeader.setLayoutParams(mlp);
	}

	// @Override
	// protected void onScrollChanged(int l, int t, int oldl, int oldt) {
	// super.onScrollChanged(l, t, oldl, oldt);
	// // set when finger leave the screen
	// if (mLastRefreshState != REFRESH_STATE_ING && mMeasuredHeaderHeight > 0)
	// setHeaderPadding(-mMeasuredHeaderHeight);
	// }

	private class HeaderAnimationListener implements AnimationListener {
		private int mListViewHeight, mDeltaY;
		private int startRefreshState;

		public HeaderAnimationListener(int dalta) {
			mDeltaY = dalta;
		}

		@Override
		public void onAnimationStart(Animation animation) {
			startRefreshState = mLastRefreshState;
			// the height of the ListView is reseted,
			// so that the translation won't make it
			// look shorter as it should be
			android.view.ViewGroup.LayoutParams lp = getLayoutParams();
			mListViewHeight = lp.height;
			lp.height = getHeight() - mDeltaY;
			setLayoutParams(lp);
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			setHeaderPadding(startRefreshState == REFRESH_STATE_ING ? 0 : -mHeader.getHeight()
					- mHeaderContainer.getTop());
			// if animation is activated, then it
			// should be restored as selecting 0
			setSelection(0);

			// since it's a translation animation,
			// the height shouldn't be set wrong
			android.view.ViewGroup.LayoutParams lp = getLayoutParams();
			lp.height = mListViewHeight;
			setLayoutParams(lp);
			// pull not enough, no more animation
			if (mLastRefreshState == REFRESH_STATE_PULL)
				return;
			// do reset animation after releasing
			postDelayed(new Runnable() {
				public void run() {
					doResetHeader();
				}
			}, BOUNCE_ANIMATION_DELAY);
			// set state after reset animation
			if (mLastRefreshState == REFRESH_STATE_ING)
				setRefreshState(REFRESH_STATE_PULL);
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}
	}

	private void setRefreshState(int state) {
		switch (state) {
		case REFRESH_STATE_PULL:
			Log.v("STATE_CHANGED_TO", "下拉刷新");
			break;
		case REFRESH_STATE_RELEASE:
			Log.v("STATE_CHANGED_TO", "松开刷新");
			break;
		case REFRESH_STATE_ING:
			Log.v("STATE_CHANGED_TO", "正在刷新");
			mRefreshListener.onRefresh();
			break;
		default:
			break;
		}
		mLastRefreshState = state;
	}

	private class TmpOnGlobalLayoutListener implements OnGlobalLayoutListener {

		@SuppressWarnings("deprecation")
		@Override
		public void onGlobalLayout() {
			int initialHeaderHeight = mHeader.getHeight();

			if (initialHeaderHeight > 0) {
				mMeasuredHeaderHeight = initialHeaderHeight;

				if (mMeasuredHeaderHeight > 0 && mLastRefreshState != REFRESH_STATE_ING) {
					setHeaderPadding(-mMeasuredHeaderHeight);
					requestLayout();
				}
			}

			getViewTreeObserver().removeGlobalOnLayoutListener(this);
		}
	}
}
