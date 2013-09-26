package org.buaa.career;

import java.util.ArrayList;
import java.util.List;

import org.buaa.career.note.HeadlineFragment;
import org.buaa.career.trifle.Headline;
import org.buaa.career.trifle.NewsUnit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment that contains the ViewPager, replaced when one tab claims to show detailed information.
 * 
 * @author James
 * 
 */
public class MainFragment extends Fragment {
	private static List<Fragment> mTabFragments;
	private int mPosition;
	private MainPagerAdapter mPagerAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mTabFragments = new ArrayList<Fragment>();
		Bundle args = new Bundle();
		args.putInt("position", 1);
		HeadlineFragment fragment = new HeadlineFragment();
		fragment.setArguments(args);
		mTabFragments.add(fragment);

		args = new Bundle();
		args.putInt("position", 2);
		fragment = new HeadlineFragment();
		fragment.setArguments(args);
		mTabFragments.add(fragment);

		mPagerAdapter = new MainPagerAdapter(getActivity().getSupportFragmentManager(),
				mTabFragments);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_highest, container, false);
		ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
		viewPager.setAdapter(mPagerAdapter);
		return view;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("position", mPosition);
	}

	/**
	 * As the manager of tab fragments, MainFragment provides the method
	 * in order that AsyncTask can do callback to invoke on HeadlineFragment.
	 * @param newsUnits
	 */
	public void onPostDataLoding(List<NewsUnit> newsUnits) {
		ArrayList<Headline> headlines = new ArrayList<Headline>();
		for (NewsUnit newsUnit : newsUnits)
			headlines.add(newsUnit.headline);

		((HeadlineFragment)mTabFragments.get(0)).setDataSource(headlines);

	}
}
