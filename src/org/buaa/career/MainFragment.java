package org.buaa.career;

import java.util.ArrayList;
import java.util.List;

import org.buaa.career.tabfragment.TabOneFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Fragment that contains the ViewPager, replaced when one tab claims to show detailed information.
 * 
 * @author James
 * 
 */
public class MainFragment extends Fragment {
	private List<LinearLayout> mTabs;
	private static List<Fragment> mTabFragments;
	private int mPosition;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mTabFragments = new ArrayList<Fragment>();
		mTabFragments.add(new TabOneFragment());
		mTabFragments.add(new TabOneFragment());
		mTabFragments.add(new TabOneFragment());
		mTabFragments.add(new TabOneFragment());

		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main_fragment, container, false);
		getChildFragmentManager().beginTransaction().add(R.id.tab_container, mTabFragments.get(0))
				.commit();

		mTabs = new ArrayList<LinearLayout>();
		mTabs.add((LinearLayout) view.findViewById(R.id.tab_one));
		mTabs.add((LinearLayout) view.findViewById(R.id.tab_two));
		mTabs.add((LinearLayout) view.findViewById(R.id.tab_three));
		mTabs.add((LinearLayout) view.findViewById(R.id.tab_four));

		mTabs.get(0).setOnClickListener(new TabOnClickListener(0));
		mTabs.get(1).setOnClickListener(new TabOnClickListener(1));
		mTabs.get(2).setOnClickListener(new TabOnClickListener(2));
		mTabs.get(3).setOnClickListener(new TabOnClickListener(3));

		return view;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("position", mPosition);
	}

	public List<Fragment> getTabFragments() {
		return mTabFragments;
	}

	private class TabOnClickListener implements OnClickListener {
		private int mPosition;

		public TabOnClickListener(int position) {
			mPosition = position;
		}

		@Override
		public void onClick(View v) {
			System.out.println("change to " + mPosition + "tab");
			getChildFragmentManager().beginTransaction()
					.replace(R.id.tab_container, mTabFragments.get(mPosition)).addToBackStack(null)
					.commit();
		}
	}
}
