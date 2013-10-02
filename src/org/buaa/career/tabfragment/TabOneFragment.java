package org.buaa.career.tabfragment;

import java.util.ArrayList;
import java.util.List;

import org.buaa.career.R;
import org.buaa.career.note.HeadlineFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viewpagerindicator.UnderlinePageIndicator;

public class TabOneFragment extends Fragment {
	private HeadlineFragment[] mFragments = new HeadlineFragment[4];;
	private TabOneFragmentPagerAdapter mPagerAdapter;
	private UnderlinePageIndicator mIndicator;
	private ViewPager mViewPager;
	private int mLastTabPosition;
	private List<TextView> mTabTextViews;

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

		mTabTextViews = new ArrayList<TextView>();
		mTabTextViews.add((TextView) view.findViewById(R.id.tab_one_tab_one_text));
		mTabTextViews.add((TextView) view.findViewById(R.id.tab_one_tab_two_text));
		mTabTextViews.add((TextView) view.findViewById(R.id.tab_one_tab_three_text));
		mTabTextViews.add((TextView) view.findViewById(R.id.tab_one_tab_four_text));

		mViewPager = (ViewPager) view.findViewById(R.id.tab_one_pager);
		mViewPager.setAdapter(mPagerAdapter);

		mLastTabPosition = 0;

		mIndicator = (UnderlinePageIndicator) view.findViewById(R.id.tab_one_tab_indicator);
		mIndicator.setViewPager(mViewPager);
		mIndicator.setFades(false);
		mIndicator.setSelectedColor(getResources().getColor(R.color.bc_red));
		mIndicator.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				mTabTextViews.get(position).setTextColor(getResources().getColor(R.color.bc_red));
				mTabTextViews.get(mLastTabPosition).setTextColor(
						getResources().getColor(R.color.bc_gray));
				mLastTabPosition = position;
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
		super.onStart();
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
