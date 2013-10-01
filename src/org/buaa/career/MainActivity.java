package org.buaa.career;

import java.util.ArrayList;
import java.util.List;

import org.buaa.career.note.ArticleFragment;
import org.buaa.career.note.HeadlineFragment.OnHeadlineSelectedListener;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class MainActivity extends SherlockFragmentActivity implements OnHeadlineSelectedListener {
	private List<ImageView> mTabs;
	private MainFragment mMainFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		mMainFragment = new MainFragment();
		getSupportFragmentManager().beginTransaction().add(R.id.container, mMainFragment).commit();
	}

	private class TabOnClickListener implements OnClickListener {
		private int position;

		public TabOnClickListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			// TODO tab listener
		}
	}

	private void setTabListeners() {
		mTabs = new ArrayList<ImageView>();
		mTabs.add((ImageView) findViewById(R.id.img_tab_notification));
		mTabs.add((ImageView) findViewById(R.id.img_tab_jobs));
		mTabs.add((ImageView) findViewById(R.id.img_tab_calendar));
		mTabs.add((ImageView) findViewById(R.id.img_tab_more));

		mTabs.get(0).setOnClickListener(new TabOnClickListener(0));
		mTabs.get(1).setOnClickListener(new TabOnClickListener(1));
		mTabs.get(2).setOnClickListener(new TabOnClickListener(2));
		mTabs.get(3).setOnClickListener(new TabOnClickListener(3));
	}

	public MainFragment getMainFragment() {
		return mMainFragment;
	}

	@Override
	public void onHeadlineSelected(int position, String url) {
		// note that position 0 is header
		ArticleFragment fragment = new ArticleFragment();
		Bundle args = new Bundle();
		args.putInt("position", position);
		args.putString("url", url);
		fragment.setArguments(args);

		// getSupportFragmentManager().beginTransaction().add(R.id.tab_container, fragment)
		// .hide(mMainFragment).addToBackStack(null).commit();
		getSupportFragmentManager().beginTransaction().replace(R.id.tab_container, fragment)
				.addToBackStack(null).commit();
	}

}
