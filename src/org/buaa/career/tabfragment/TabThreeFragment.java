package org.buaa.career.tabfragment;

import java.util.LinkedList;

import org.buaa.career.MainActivity;
import org.buaa.career.R;
import org.buaa.career.data.db.DBTask;
import org.buaa.career.data.model.News;
import org.buaa.career.tabfragment.NewsFragment.OnHeadlineSelectedListener;
import org.buaa.career.trifle.HeadlineAdapter;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class TabThreeFragment extends Fragment {
	public static String TAG = "TAB_INFO";

	private OnHeadlineSelectedListener mCallBack;
	ListView mListView;
	HeadlineAdapter mAdapter = null;
	private LinkedList<News> mListItems;

	/**
	 * refresh the favorite list
	 */
	public void refresh() {
		new LoadDBDataTask().execute();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				mCallBack = (OnHeadlineSelectedListener) (MainActivity) getActivity();
				mCallBack.onNewsSelected(mListItems.get(position), position);
			}
		});
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onResume() {
		refresh();
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_three_fragment, container,
				false);
		mListItems = new LinkedList<News>();

		mAdapter = new HeadlineAdapter(getActivity(), mListItems,
				R.layout.news_item, new String[] { "title", "time" },
				new int[] { R.id.headline_title_text, R.id.headline_desc_text });

		mListView = (ListView) view.findViewById(R.id.favourite_list);
		mListView.setAdapter(mAdapter);
		mListView.setEnabled(true);

		new LoadDBDataTask().execute();
		return view;
	}

	private class LoadDBDataTask extends
			AsyncTask<Void, Integer, LinkedList<News>> {

		public LoadDBDataTask() {
		}

		@Override
		protected LinkedList<News> doInBackground(Void... params) {
			mListItems.clear();
			mListItems.addAll(DBTask.getFavouriteNews(getActivity()));
			return null;
		}

		@Override
		protected void onPostExecute(LinkedList<News> result) {
			if (mAdapter == null)
				return;
			mAdapter.notifyDataSetChanged();
			super.onPostExecute(result);
		}
	}
}
