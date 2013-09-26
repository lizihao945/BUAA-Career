package org.buaa.career;

import java.util.ArrayList;
import java.util.List;

import org.buaa.career.note.ArticleFragment;
import org.buaa.career.note.HeadlineFragment.OnHeadlineSelectedListener;
import org.buaa.career.trifle.DownloadNewsUnitTask;
import org.buaa.career.trifle.Headline;
import org.buaa.career.trifle.NewsUnit;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

public class MainActivity extends FragmentActivity implements OnHeadlineSelectedListener {
	private List<ImageView> mTabs;
	private MainFragment mMainFragment;
	/**
	 * NewUnits that will be used by the fragments.
	 */
	private ArrayList<NewsUnit> mNewsUnits;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_activity);
		// TODO initialize with 20 stored news_units
		mNewsUnits = new ArrayList<NewsUnit>();
		for (int i = 0; i < 20; i++) {
			mNewsUnits.add(new NewsUnit(new Headline("not setted", Headline.UNDEFINED)));
		}
		mMainFragment = new MainFragment();
		getSupportFragmentManager().beginTransaction().add(R.id.container, mMainFragment).commit();

		// get data from scratch
		new DownloadNewsUnitTask(this).execute();

	}

	@Override
	public void onArticleSelected(int position) {
		ArticleFragment fragment = new ArticleFragment();
		Bundle args = new Bundle();
		args.putInt("position", position);
		fragment.setArguments(args);
		getSupportFragmentManager().beginTransaction().hide(mMainFragment)
				.add(R.id.container, fragment).addToBackStack(null).commit();
	}

	private class MyOnClickListener implements OnClickListener {
		private int position;

		public MyOnClickListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			ViewPager viewPager = (ViewPager) mMainFragment.getView().findViewById(R.id.pager);
			viewPager.setCurrentItem(position, true);
		}
	}

	private void setTabListeners() {
		mTabs = new ArrayList<ImageView>();
		mTabs.add((ImageView) findViewById(R.id.img_tab_notification));
		mTabs.add((ImageView) findViewById(R.id.img_tab_jobs));
		mTabs.add((ImageView) findViewById(R.id.img_tab_calendar));
		mTabs.add((ImageView) findViewById(R.id.img_tab_more));

		mTabs.get(0).setOnClickListener(new MyOnClickListener(0));
		mTabs.get(1).setOnClickListener(new MyOnClickListener(1));
		mTabs.get(2).setOnClickListener(new MyOnClickListener(2));
		mTabs.get(3).setOnClickListener(new MyOnClickListener(3));
	}

	public void setNewsUnits(ArrayList<NewsUnit> newsUnits) {
		mNewsUnits = newsUnits;
	}

	public List<NewsUnit> getNewsUnits() {
		return mNewsUnits;
	}

	public MainFragment getMainFragment() {
		return mMainFragment;
	}
}
