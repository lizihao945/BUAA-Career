package org.buaa.career.note;

import org.buaa.career.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

public class TabOneFragment extends Fragment {
	private HeadlineFragment[] mFragments = new HeadlineFragment[4];;
	private TabOneFragmentPagerAdapter mPagerAdapter;
	private ViewPager mViewPager;
	private View mMovingTab;
	private String[] mTabTitles;
	private float mTabX;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		mPagerAdapter = new TabOneFragmentPagerAdapter(getChildFragmentManager());

		mFragments[0] = new HeadlineFragment();
		mFragments[1] = new HeadlineFragment();
		mFragments[2] = new HeadlineFragment();
		mFragments[3] = new HeadlineFragment();

		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.tab_one_fragment, container, false);

		mMovingTab = (FrameLayout) view.findViewById(R.id.tab_one_moving_tab);

		mTabTitles = new String[4];
		mTabTitles[0] = ((TextView) view.findViewById(R.id.tab_one_tab_one_text)).getText()
				.toString();
		mTabTitles[1] = ((TextView) view.findViewById(R.id.tab_one_tab_two_text)).getText()
				.toString();
		mTabTitles[2] = ((TextView) view.findViewById(R.id.tab_one_tab_three_text)).getText()
				.toString();
		mTabTitles[3] = ((TextView) view.findViewById(R.id.tab_one_tab_four_text)).getText()
				.toString();

		mViewPager = (ViewPager) view.findViewById(R.id.tab_one_pager);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				doTabAnimation(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		return view;
	}

	@Override
	public void onStart() {
		mViewPager.setAdapter(mPagerAdapter);
		super.onStart();
	}

	private void doTabAnimation(final int position) {
		float width = mMovingTab.getWidth();
		TranslateAnimation anim = new TranslateAnimation(mTabX, mTabX = width * position, 0, 0);
		anim.setDuration(200);
		anim.setFillAfter(true);
		mMovingTab.startAnimation(anim);
	}

	public class TabOneFragmentPagerAdapter extends FragmentPagerAdapter {
		public TabOneFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			return mFragments[arg0];
		}

		@Override
		public int getCount() {
			return mFragments.length;
		}
	}
}
