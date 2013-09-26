package org.buaa.career.note;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.buaa.career.MainActivity;
import org.buaa.career.R;
import org.buaa.career.trifle.Headline;
import org.buaa.career.trifle.NewsUnit;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

/**
 * Fragment that contains the ViewPager, replaced when one tab claims to show detailed information.
 * 
 * @author James
 * 
 */
public class HeadlineFragment extends ListFragment {
	private OnHeadlineSelectedListener mCallBack;
	private List<Headline> mHeadlines = null;
	private int mNum;

	public interface OnHeadlineSelectedListener {
		public void onArticleSelected(int position);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mNum = getArguments() != null ? getArguments().getInt("position") : -1;
		// get Headline from NewsUnit
		List<NewsUnit> newsUnits = ((MainActivity) getActivity()).getNewsUnits().subList(0, 19);
		mHeadlines = new ArrayList<Headline>(20);
		for (NewsUnit newsUnit : newsUnits)
			mHeadlines.add(newsUnit.headline);

		setDataSource(mHeadlines);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_one, container, false);
		return view;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		mCallBack = (OnHeadlineSelectedListener) getActivity();
		// Notify the parent activity of selected item
		mCallBack.onArticleSelected(position);
		// Set the item as checked to be highlighted when in two-pane layout
		getListView().setItemChecked(position, true);
	}

	public void setDataSource(List<Headline> headlines) {
		mHeadlines = headlines;
		List<Map<String, Object>> tmp = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < headlines.size(); i++) {
			Map<String, Object> tmpMap = new HashMap<String, Object>();
			tmpMap.put("title", headlines.get(i).title);
			tmp.add(tmpMap);
		}
		setListAdapter(new HeadlineAdapter(getActivity(), tmp, R.layout.headline,
				new String[] { "title" }, new int[] { R.id.headline_title_text }));
	}

	public class HeadlineAdapter extends SimpleAdapter {

		public HeadlineAdapter(Context context, List<? extends Map<String, ?>> data, int resource,
				String[] from, int[] to) {
			super(context, data, resource, from, to);
			// TODO Auto-generated constructor stub
		}
	}
}
