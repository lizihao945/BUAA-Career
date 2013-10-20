package org.buaa.career.tabfragment;

import org.buaa.career.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

public abstract class AbstractNewsListFragment extends Fragment {
	protected PullToRefreshListView mPullToRefreshListView;
	protected TextView mNewMessageTextView;
	protected TextView mEmptyTextView;
	protected SimpleAdapter mNewsListAdapter;
	protected View mFooterView;
	protected ProgressBar mProgressBar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.news_listview, container, false);
		mEmptyTextView = (TextView) view.findViewById(R.id.tv_empty);
		mProgressBar = (ProgressBar) view.findViewById(R.id.progressbar);
		mPullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.list);
		mNewMessageTextView = (TextView) view.findViewById(R.id.tv_new_message_tip_bar);

		getListView().setHeaderDividersEnabled(false);
		getListView().setScrollingCacheEnabled(true);

		mFooterView = inflater.inflate(R.layout.news_listview_footer, null);
		getListView().addFooterView(mFooterView);

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	private void hideFooterView() {
		mFooterView.findViewById(R.id.iv_refreshing).setVisibility(View.GONE);
		mFooterView.findViewById(R.id.iv_refreshing).clearAnimation();
		mFooterView.findViewById(R.id.tv_refreshing).setVisibility(View.GONE);
	}

	protected abstract void listViewItemClick(AbsListView parent, View view, int position, long id);

	protected abstract void scrollToTop();

	protected void showListView() {
		mProgressBar.setVisibility(View.INVISIBLE);
	}

	public ListView getListView() {
		return mPullToRefreshListView.getRefreshableView();
	}

	public PullToRefreshListView getPullToRefreshListView() {
		return mPullToRefreshListView;
	}

	public SimpleAdapter getNewsListAdapter() {
		return mNewsListAdapter;
	}

	public View getFooterView() {
		return mFooterView;
	}

}
