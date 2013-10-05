package org.buaa.career.tabfragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.buaa.career.R;
import org.buaa.career.view.widget.CheckableRelativeLayout;

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

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.viewpagerindicator.UnderlinePageIndicator;

public class TabOneFragment extends Fragment {
	private HeadlineFragment[] mFragments = new HeadlineFragment[4];;
	private TabOneFragmentPagerAdapter mPagerAdapter;
	private UnderlinePageIndicator mIndicator;
	private ViewPager mViewPager;
	private int mPosition;
	private List<CheckableRelativeLayout> mTabs;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		mPagerAdapter = new TabOneFragmentPagerAdapter(getChildFragmentManager());

		Bundle args;
		mFragments[0] = new HeadlineFragment();
		args = new Bundle();
		args.putInt("channel", HeadlineFragment.NOTIFICATION);
		mFragments[0].setArguments(args);
		mFragments[1] = new HeadlineFragment();
		args = new Bundle();
		args.putInt("channel", HeadlineFragment.RECENT);
		mFragments[1].setArguments(args);
		mFragments[2] = new HeadlineFragment();
		args = new Bundle();
		args.putInt("channel", HeadlineFragment.CENTER);
		mFragments[2].setArguments(args);
		mFragments[3] = new HeadlineFragment();
		args = new Bundle();
		args.putInt("channel", HeadlineFragment.WORKING);
		mFragments[3].setArguments(args);

		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.tab_one_fragment, container, false);

		// get reference of the category tabs in the tab bar
		mTabs = new ArrayList<CheckableRelativeLayout>();
		mTabs.add((CheckableRelativeLayout) view.findViewById(R.id.tab_one_tab_one));
		mTabs.add((CheckableRelativeLayout) view.findViewById(R.id.tab_one_tab_two));
		mTabs.add((CheckableRelativeLayout) view.findViewById(R.id.tab_one_tab_three));
		mTabs.add((CheckableRelativeLayout) view.findViewById(R.id.tab_one_tab_four));

		// display the first category by default
		mTabs.get(0).setChecked(true);

		// initialize the category tabs in the tab bar
		((TextView) mTabs.get(0).findViewById(R.id.tab_one_tab_text)).setText(getResources()
				.getString(R.string.category_importent_notification));
		((TextView) mTabs.get(1).findViewById(R.id.tab_one_tab_text)).setText(getResources()
				.getString(R.string.category_recent_recent_recruitment));
		((TextView) mTabs.get(2).findViewById(R.id.tab_one_tab_text)).setText(getResources()
				.getString(R.string.category_center_info));
		((TextView) mTabs.get(3).findViewById(R.id.tab_one_tab_text)).setText(getResources()
				.getString(R.string.category_workplace_info));

		mViewPager = (ViewPager) view.findViewById(R.id.tab_one_pager);
		mViewPager.setAdapter(mPagerAdapter);

		mIndicator = (UnderlinePageIndicator) view.findViewById(R.id.tab_one_tab_indicator);
		mIndicator.setViewPager(mViewPager);
		mIndicator.setFades(false);
		mIndicator.setSelectedColor(getResources().getColor(R.color.bc_blue));
		mIndicator.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				mTabs.get(position).setChecked(true);
				mTabs.get(mPosition).setChecked(false);
				mPosition = position;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

		// display the first category by default
		mIndicator.setCurrentItem(0);
		mPosition = 0;
		return view;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		// Basically, the child FragmentManager ends up with a broken internal state when it is
		// detached from the activity.
		try {
			Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
			childFragmentManager.setAccessible(true);
			childFragmentManager.set(this, null);

		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
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

	public int getPosition() {
		return mPosition;
	}

	public void refreshCurrTab() {
		mFragments[mPosition].setRefreshing();
	}
}
