package org.buaa.career.tabfragment;

import java.util.LinkedList;

import org.buaa.career.MainActivity;
import org.buaa.career.MainActivity.Scrollable;
import org.buaa.career.R;
import org.buaa.career.data.db.NewsArticleDBTask;
import org.buaa.career.data.model.News;
import org.buaa.career.trifle.DownloadNewsTask;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.extras.listfragment.PullToRefreshListFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * Fragment that contains the ViewPager, replaced when one tab claims to show detailed information.
 * 
 * @author James
 * 
 */
public class NewsFragment extends PullToRefreshListFragment implements OnRefreshListener<ListView>,
		Scrollable {

	private int mChannel;
	private OnHeadlineSelectedListener mCallBack;
	private LinkedList<News> mListItems;
	private SimpleAdapter mAdapter;
	private PullToRefreshListView mListView;
	private View mFooterView;
	private int currMaxPageNum;

	public interface OnHeadlineSelectedListener {
		public void onNewsSelected(News news, int position);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			mChannel = getArguments().getInt("channel");
			if (mChannel == 0)
				throw new NullPointerException();
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.err.println("No proper args set for HeadlineFragment");
		}
		mListItems = new LinkedList<News>();
		currMaxPageNum = 1;
		new LoadDBDataTask().execute();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		setEmptyText("请下拉刷新...");
		mListView = getPullToRefreshListView();
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mCallBack = (OnHeadlineSelectedListener) (MainActivity) getActivity();
				// Notify the main activity of selected item
				mCallBack.onNewsSelected(mListItems.get(position - 1), position);
				// Set the item as checked to be highlighted when in two-pane layout
				getListView().setItemChecked(position, true);
			}
		});
		mListView.setOnRefreshListener(this);

		mFooterView = LayoutInflater.from(getActivity()).inflate(R.layout.news_listview_footer,
				null);
		mFooterView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((TextView) mFooterView.findViewById(R.id.tv_refreshing))
						.setText(R.string.refreshing);
				new DownloadNewsTask(NewsFragment.this, mChannel, ++currMaxPageNum).execute();
			}
		});
		getPullToRefreshListView().getRefreshableView().addFooterView(mFooterView);

		mAdapter = new HeadlineAdapter(getActivity(), mListItems, R.layout.news_item,
				new String[] { "title", "time" }, new int[] { R.id.headline_title_text,
						R.id.headline_desc_text });
		setListAdapter(mAdapter);

		super.onActivityCreated(savedInstanceState);
	}

	@Override
	protected PullToRefreshListView onCreatePullToRefreshListView(LayoutInflater inflater,
			Bundle savedInstanceState) {
		return (PullToRefreshListView) inflater.inflate(R.layout.ptr_listview, null);
	}

	public void setRefreshing() {
		if (mListView.isRefreshing())
			return;
		mListView.setRefreshing();
	}

	public void updateList(boolean isHead, LinkedList<News> result) {
		if (isHead) {
			mListItems.clear();
			mListItems.addAll(result);
			mAdapter.notifyDataSetChanged();
			mListView.onRefreshComplete();
		} else {
			mListItems.addAll(result);
			mAdapter.notifyDataSetChanged();
		}
	}

	public void updateFailed(boolean isHead) {
		Toast.makeText(getActivity(), R.string.refresh_failed, Toast.LENGTH_SHORT).show();
		if (isHead)
			mListView.onRefreshComplete();
		else {
			((TextView) mFooterView.findViewById(R.id.tv_refreshing))
					.setText(R.string.refresh_failed_footer);
		}
	}

	@Override
	public void scrollToTop() {
		// TODO make action bar call this method
		mListView.scrollTo(0, 0);
	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		new DownloadNewsTask(this, mChannel, 1).execute();
	}

	private boolean isConnected() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager != null) {
			NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected()
					&& networkInfo.getState() == NetworkInfo.State.CONNECTED)
				return true;
		}
		return false;
	}

	private class HeadlineAdapter extends SimpleAdapter {

		public HeadlineAdapter(Context context, LinkedList<News> data, int resource, String[] from,
				int[] to) {
			super(context, data, resource, from, to);
		}
	}

	private class LoadDBDataTask extends AsyncTask<Void, Integer, LinkedList<News>> {

		@Override
		protected LinkedList<News> doInBackground(Void... params) {
			switch (mChannel) {
			case News.NOTIFICATION:
				mListItems.clear();
				mListItems.addAll(NewsArticleDBTask.getRecentNotifications(getActivity()));
				break;
			case News.RECENT_RECRUITMENT:
				mListItems.clear();
				mListItems.addAll(NewsArticleDBTask.getRecentRecentRecruitment(getActivity()));
			default:
				break;
			}
			return null;
		}

		@Override
		protected void onPostExecute(LinkedList<News> result) {
			mAdapter.notifyDataSetChanged();
			super.onPostExecute(result);
		}
	}
}
