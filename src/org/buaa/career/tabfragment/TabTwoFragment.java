<<<<<<< HEAD
package org.buaa.career.tabfragment;

import java.sql.Date;
import java.util.Calendar;
import java.util.LinkedList;

import org.buaa.career.R;
import org.buaa.career.data.db.DBTask;
import org.buaa.career.data.model.News;
import org.buaa.career.trifle.HeadlineAdapter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.squareup.timessquare.CalendarPickerView;
import com.squareup.timessquare.CalendarPickerView.OnDateSelectedListener;
import com.squareup.timessquare.CalendarPickerView.SelectionMode;

public class TabTwoFragment extends Fragment {
	private CalendarPickerView mCalendarPickerView;
	private ListView mListView;
	private HeadlineAdapter mAdapter;
	private LinkedList<News> mListItems;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mListItems = new LinkedList<News>();
		new LoadDBDataTask(new Date(new java.util.Date().getTime())).execute();

		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.tab_two_fragment, container, false);
		final Calendar startTime = Calendar.getInstance();
		startTime.add(Calendar.MONTH, -3);
		final Calendar endTime = Calendar.getInstance();
		endTime.add(Calendar.MONTH, 3);
		mAdapter = new HeadlineAdapter(getActivity(), mListItems, R.layout.news_item, new String[] {
				"title", "time" }, new int[] { R.id.headline_title_text, R.id.headline_desc_text });

		mListView = (ListView) view.findViewById(R.id.list_view);
		mListView.setAdapter(mAdapter);
		mListView.setEnabled(true);

		mCalendarPickerView = (CalendarPickerView) view.findViewById(R.id.calendar_view);
		mCalendarPickerView.setOnDateSelectedListener(new OnDateSelectedListener() {

			@Override
			public void onDateSelected(java.util.Date date) {
				new LoadDBDataTask(new Date(mCalendarPickerView.getSelectedDate().getTime()))
						.execute();
			}
		});
		mCalendarPickerView.init(startTime.getTime(), endTime.getTime())
		.inMode(SelectionMode.SINGLE)
		.withSelectedDate(new java.util.Date());
		
		return view;
	}

	private class LoadDBDataTask extends AsyncTask<Void, Integer, LinkedList<News>> {
		Date mDate;

		public LoadDBDataTask(Date date) {
			mDate = date;
		}

		@Override
		protected LinkedList<News> doInBackground(Void... params) {
			mListItems.clear();
			mListItems.addAll(DBTask.getNewsByDate(mDate, getActivity()));
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
=======
package org.buaa.career.tabfragment;

import java.sql.Date;
import java.util.Calendar;
import java.util.LinkedList;

import org.buaa.career.R;
import org.buaa.career.data.db.DBTask;
import org.buaa.career.data.model.News;
import org.buaa.career.trifle.HeadlineAdapter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.squareup.timessquare.CalendarPickerView;
import com.squareup.timessquare.CalendarPickerView.OnDateSelectedListener;
import com.squareup.timessquare.CalendarPickerView.SelectionMode;

public class TabTwoFragment extends Fragment {
	private CalendarPickerView mCalendarPickerView;
	private ListView mListView;
	private HeadlineAdapter mAdapter;
	private LinkedList<News> mListItems;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mListItems = new LinkedList<News>();
		new LoadDBDataTask(new Date(new java.util.Date().getTime())).execute();

		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.tab_two_fragment, container, false);
		final Calendar startTime = Calendar.getInstance();
		startTime.add(Calendar.MONTH, -3);
		final Calendar endTime = Calendar.getInstance();
		endTime.add(Calendar.MONTH, 3);
		mAdapter = new HeadlineAdapter(getActivity(), mListItems, R.layout.news_item, new String[] {
				"title", "time" }, new int[] { R.id.headline_title_text, R.id.headline_desc_text });

		mListView = (ListView) view.findViewById(R.id.list_view);
		mListView.setAdapter(mAdapter);
		mListView.setEnabled(true);

		mCalendarPickerView = (CalendarPickerView) view.findViewById(R.id.calendar_view);
		mCalendarPickerView.setOnDateSelectedListener(new OnDateSelectedListener() {

			@Override
			public void onDateSelected(java.util.Date date) {
				new LoadDBDataTask(new Date(mCalendarPickerView.getSelectedDate().getTime()))
						.execute();
			}
		});
		mCalendarPickerView.init(startTime.getTime(), endTime.getTime())
		.inMode(SelectionMode.SINGLE)
		.withSelectedDate(new java.util.Date());
		
		return view;
	}

	private class LoadDBDataTask extends AsyncTask<Void, Integer, LinkedList<News>> {
		Date mDate;

		public LoadDBDataTask(Date date) {
			mDate = date;
		}

		@Override
		protected LinkedList<News> doInBackground(Void... params) {
			mListItems.clear();
			mListItems.addAll(DBTask.getNewsByDate(mDate, getActivity()));
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
>>>>>>> 0a78cd94b879cab5a1bd8f7c05826ac4ca3563b0
}