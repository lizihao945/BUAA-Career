package org.buaa.career;

import java.util.ArrayList;
import java.util.List;

import org.buaa.career.note.HeadlineFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mTabFragments = new ArrayList<Fragment>();
		Bundle args = new Bundle();
		args.putInt("position", 1);
		HeadlineFragment fragment = new HeadlineFragment();
		fragment.setArguments(args);
		mTabFragments.add(fragment);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_highest, container, false);
		getChildFragmentManager().beginTransaction().add(R.id.tab_container, mTabFragments.get(0))
				.commit();
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

}
