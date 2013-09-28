package org.buaa.career.note;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.buaa.career.MainActivity;
import org.buaa.career.MainFragment;
import org.buaa.career.R;
import org.buaa.career.trifle.DownloadHeadlineTask;
import org.buaa.career.trifle.Headline;
import org.buaa.career.trifle.NewsListView;
import org.buaa.career.trifle.NewsListView.OnRefreshListener;
import org.buaa.career.trifle.NewsUnit;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.SimpleAdapter;

/**
 * Fragment that contains the ViewPager, replaced when one tab claims to show detailed information.
 * 
 * @author James
 * 
 */
public class HeadlineFragment extends Fragment {
	private OnHeadlineSelectedListener mCallBack;
	private List<Headline> mHeadlines;
	private NewsListView mListView;

	public interface OnHeadlineSelectedListener {
		public void onHeadlineSelected(int position);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mHeadlines = new ArrayList<Headline>();
		for (int i = 0; i < 20; i++)
			mHeadlines.add(new Headline());

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_one, container, false);
		mListView = (NewsListView) view.findViewById(R.id.refreshable_list);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {
		setDataSource(mHeadlines);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mCallBack = (OnHeadlineSelectedListener) (MainActivity) getActivity();
				// Notify the parent activity of selected item
				mCallBack.onHeadlineSelected(position);
				// Set the item as checked to be highlighted when in two-pane layout
				mListView.setItemChecked(position, true);

			}
		});
		mListView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				new DownloadHeadlineTask((MainFragment) getParentFragment()).execute();
			}
		});
		super.onStart();
	}

	public void setDataSource(List<Headline> headlines) {
		mHeadlines = headlines;
		List<Map<String, Object>> tmp = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < headlines.size(); i++) {
			Map<String, Object> tmpMap = new HashMap<String, Object>();
			tmpMap.put("title", headlines.get(i).title);
			tmp.add(tmpMap);
		}

		mListView.setAdapter(new HeadlineAdapter(getActivity(), tmp, R.layout.headline,
				new String[] { "title" }, new int[] { R.id.headline_title_text }));
	}

	public class HeadlineAdapter extends SimpleAdapter {

		public HeadlineAdapter(Context context, List<? extends Map<String, ?>> data, int resource,
				String[] from, int[] to) {
			super(context, data, resource, from, to);
			// TODO Auto-generated constructor stub
		}
	}

	public List<Headline> getHeadlines() {
		return mHeadlines;
	}

}
