package org.buaa.career;

import java.util.ArrayList;
import java.util.List;

import org.buaa.career.data.model.News;
import org.buaa.career.tabfragment.NewsFragment.OnHeadlineSelectedListener;
import org.buaa.career.tabfragment.TabFourFragment;
import org.buaa.career.tabfragment.TabTwoFragment;
import org.buaa.career.tabfragment.TabThreeFragment;
import org.buaa.career.tabfragment.TabOneFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;

public class MainActivity extends SherlockFragmentActivity implements
		OnHeadlineSelectedListener {

	public static interface Scrollable {
		public void scrollToTop();
	}

	private List<LinearLayout> mTabs;
	private List<ImageView> mTabImgs;
	private List<TextView> mTabTexts;
	private static List<Fragment> mTabFragments;
	private int mPosition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		mPosition = 0;

		mTabFragments = new ArrayList<Fragment>();
		mTabFragments.add(new TabOneFragment());
		mTabFragments.add(new TabTwoFragment());
		mTabFragments.add(new TabThreeFragment());
		mTabFragments.add(new TabFourFragment());

		mTabs = new ArrayList<LinearLayout>();
		mTabImgs = new ArrayList<ImageView>();
		mTabTexts = new ArrayList<TextView>();
		mTabs.add((LinearLayout) findViewById(R.id.tab_one));
		mTabImgs.add((ImageView) findViewById(R.id.tab_one_img));
		mTabTexts.add((TextView) findViewById(R.id.tab_one_text));
		mTabs.add((LinearLayout) findViewById(R.id.tab_two));
		mTabImgs.add((ImageView) findViewById(R.id.tab_two_img));
		mTabTexts.add((TextView) findViewById(R.id.tab_two_text));
		mTabs.add((LinearLayout) findViewById(R.id.tab_three));
		mTabImgs.add((ImageView) findViewById(R.id.tab_three_img));
		mTabTexts.add((TextView) findViewById(R.id.tab_three_text));
		mTabs.add((LinearLayout) findViewById(R.id.tab_four));
		mTabImgs.add((ImageView) findViewById(R.id.tab_four_img));
		mTabTexts.add((TextView) findViewById(R.id.tab_four_text));

		mTabs.get(0).setOnClickListener(new TabOnClickListener(0));
		mTabs.get(1).setOnClickListener(new TabOnClickListener(1));
		mTabs.get(2).setOnClickListener(new TabOnClickListener(2));
		mTabs.get(3).setOnClickListener(new TabOnClickListener(3));

		// Notice that when dealing with re-creating issues,
		// savedInstanceStata shoud be considered.
		getSupportFragmentManager().beginTransaction()
				.add(R.id.tab_container, mTabFragments.get(0)).commit();
		getSupportFragmentManager().beginTransaction()
				.add(R.id.tab_container, mTabFragments.get(1)).commit();
		getSupportFragmentManager().beginTransaction()
				.add(R.id.tab_container, mTabFragments.get(2)).commit();
		getSupportFragmentManager().beginTransaction()
				.add(R.id.tab_container, mTabFragments.get(3)).commit();

		getSupportFragmentManager().beginTransaction()
				.hide(mTabFragments.get(1)).commit();
		getSupportFragmentManager().beginTransaction()
				.hide(mTabFragments.get(2)).commit();
		getSupportFragmentManager().beginTransaction()
				.hide(mTabFragments.get(3)).commit();

		getSupportActionBar().setBackgroundDrawable(
				getResources().getDrawable(R.color.bc_blue));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main_activity_menu, menu);
		return true;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("position", mPosition);
	}

	@Override
	public void onNewsSelected(News news, int position) {
		Bundle args = new Bundle();
		args.putInt("channel", news.getChannel());
		args.putInt("position", position);
		args.putString("url", news.getUrl());
		args.putString("title", news.getTitle());
		args.putString("time", news.getTime());

		Intent intent = new Intent();
		intent.putExtras(args);
		intent.setClass(MainActivity.this, ArticleActivity.class);
		startActivity(intent);
	}

	private class TabOnClickListener implements OnClickListener {
		private int mPosition;

		public TabOnClickListener(int position) {
			mPosition = position;
		}

		@Override
		public void onClick(View v) {
			if (MainActivity.this.mPosition == mPosition) {
				if (mTabFragments.get(mPosition) instanceof TabTwoFragment) {
					((TabTwoFragment) mTabFragments.get(mPosition))
							.refreshCurrTab();
					return;
				} else if (mTabFragments.get(mPosition) instanceof TabThreeFragment) {
					((TabThreeFragment) mTabFragments.get(mPosition)).refresh();
				}
			}
			switch (mPosition) {
			case 0:
				mTabImgs.get(0).setImageDrawable(
						getResources()
								.getDrawable(R.drawable.ic_three_men_blue));
				mTabTexts.get(0).setTextColor(
						getResources().getColor(R.color.bc_blue));
				mTabImgs.get(1).setImageDrawable(
						getResources().getDrawable(R.drawable.ic_notification));
				mTabTexts.get(1).setTextColor(
						getResources().getColor(R.color.bc_gray));
				mTabImgs.get(2).setImageDrawable(
						getResources().getDrawable(R.drawable.ic_important));
				mTabTexts.get(2).setTextColor(
						getResources().getColor(R.color.bc_gray));
				mTabImgs.get(3).setImageDrawable(
						getResources().getDrawable(R.drawable.ic_more));
				mTabTexts.get(3).setTextColor(
						getResources().getColor(R.color.bc_gray));
				break;
			case 1:
				mTabImgs.get(0).setImageDrawable(
						getResources().getDrawable(R.drawable.ic_three_men));
				mTabTexts.get(0).setTextColor(
						getResources().getColor(R.color.bc_gray));
				mTabImgs.get(1)
						.setImageDrawable(
								getResources().getDrawable(
										R.drawable.ic_notification_blue));
				mTabTexts.get(1).setTextColor(
						getResources().getColor(R.color.bc_blue));
				mTabImgs.get(2).setImageDrawable(
						getResources().getDrawable(R.drawable.ic_important));
				mTabTexts.get(2).setTextColor(
						getResources().getColor(R.color.bc_gray));
				mTabImgs.get(3).setImageDrawable(
						getResources().getDrawable(R.drawable.ic_more));
				mTabTexts.get(3).setTextColor(
						getResources().getColor(R.color.bc_gray));
				break;
			case 2:
				((TabThreeFragment)mTabFragments.get(2)).refresh();
				mTabImgs.get(0).setImageDrawable(
						getResources().getDrawable(R.drawable.ic_three_men));
				mTabTexts.get(0).setTextColor(
						getResources().getColor(R.color.bc_gray));
				mTabImgs.get(1).setImageDrawable(
						getResources().getDrawable(R.drawable.ic_notification));
				mTabTexts.get(1).setTextColor(
						getResources().getColor(R.color.bc_gray));
				mTabImgs.get(2).setImageDrawable(
						getResources()
								.getDrawable(R.drawable.ic_important_blue));
				mTabTexts.get(2).setTextColor(
						getResources().getColor(R.color.bc_blue));
				mTabImgs.get(3).setImageDrawable(
						getResources().getDrawable(R.drawable.ic_more));
				mTabTexts.get(3).setTextColor(
						getResources().getColor(R.color.bc_gray));
				break;
			case 3:
				mTabImgs.get(0).setImageDrawable(
						getResources().getDrawable(R.drawable.ic_three_men));
				mTabTexts.get(0).setTextColor(
						getResources().getColor(R.color.bc_gray));
				mTabImgs.get(1).setImageDrawable(
						getResources().getDrawable(R.drawable.ic_notification));
				mTabTexts.get(1).setTextColor(
						getResources().getColor(R.color.bc_gray));
				mTabImgs.get(2).setImageDrawable(
						getResources().getDrawable(R.drawable.ic_important));
				mTabTexts.get(2).setTextColor(
						getResources().getColor(R.color.bc_gray));
				mTabImgs.get(3).setImageDrawable(
						getResources().getDrawable(R.drawable.ic_more_blue));
				mTabTexts.get(3).setTextColor(
						getResources().getColor(R.color.bc_blue));
			}
			getSupportFragmentManager().beginTransaction()
					.hide(mTabFragments.get(0)).hide(mTabFragments.get(1))
					.hide(mTabFragments.get(2)).hide(mTabFragments.get(3))
					.show(mTabFragments.get(mPosition)).commit();
			MainActivity.this.mPosition = mPosition;
		}
	}

	@Deprecated
	public List<Fragment> getTabFragments() {
		return mTabFragments;
	}

}
