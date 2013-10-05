package org.buaa.career.tabfragment;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;

import org.buaa.career.MainActivity;
import org.buaa.career.MainActivity.Scrollable;
import org.buaa.career.R;
import org.buaa.career.data.model.Headline;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.handmark.pulltorefresh.extras.listfragment.PullToRefreshListFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * Fragment that contains the ViewPager, replaced when one tab claims to show detailed information.
 * 
 * @author James
 * 
 */
public class HeadlineFragment extends PullToRefreshListFragment implements
		OnRefreshListener2<ListView>, Scrollable {
	public static final int NOTIFICATION = 517;
	public static final int RECENT = 518;
	public static final int CENTER = 519;
	public static final int WORKING = 520;

	private int mChannel;
	private OnHeadlineSelectedListener mCallBack;
	private final LinkedList<Headline> mListItems = new LinkedList<Headline>();;
	private SimpleAdapter mAdapter;
	private PullToRefreshListView mListView;

	private boolean isRefreshedOnce = false;

	public interface OnHeadlineSelectedListener {
		public void onHeadlineSelected(int position, String url);
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

		for (int i = 0; i < 20; i++) {
			Headline tmpMap = new Headline();
			mListItems.add(tmpMap);
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		mAdapter = new HeadlineAdapter(getActivity(), mListItems, R.layout.headline_item,
				new String[] { "title", "time" }, new int[] { R.id.headline_title_text,
						R.id.headline_desc_text });

		mListView = getPullToRefreshListView();
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mCallBack = (OnHeadlineSelectedListener) (MainActivity) getActivity();
				// Notify the main activity of selected item
				mCallBack.onHeadlineSelected(position, mListItems.get(position - 1).getUrl());
				// Set the item as checked to be highlighted when in two-pane layout
				getListView().setItemChecked(position, true);
			}
		});
		mListView.setOnRefreshListener(this);
		setListShown(true);

		if (!isRefreshedOnce)
			setRefreshing();

		super.onActivityCreated(savedInstanceState);
	}

	public class HeadlineAdapter extends SimpleAdapter {

		public HeadlineAdapter(Context context, LinkedList<Headline> data, int resource,
				String[] from, int[] to) {
			super(context, data, resource, from, to);
		}
	}

	public class DownloadHeadlineTask extends AsyncTask<Void, Integer, LinkedList<Headline>> {
		private static final String NOTIFICATION_URL = "http://career.buaa.edu.cn/website/zytz.h";
		private static final String RECENT_URL = "http://career.buaa.edu.cn/website/zphxx.h";
		private static final String CENTER_URL = "http://career.buaa.edu.cn/website/zpxx_info.h";
		private static final String WORKING_URL = "http://career.buaa.edu.cn/website/zxzpxx.h";
		private static final String ENCODE = "GB2312";

		private final String URL_STRING;

		public DownloadHeadlineTask() {
			switch (mChannel) {
			case NOTIFICATION:
				URL_STRING = NOTIFICATION_URL;
				break;
			case RECENT:
				URL_STRING = RECENT_URL;
				break;
			case CENTER:
				URL_STRING = CENTER_URL;
				break;
			case WORKING:
				URL_STRING = WORKING_URL;
				break;
			default:
				URL_STRING = null;
			}
		}

		@Override
		protected LinkedList<Headline> doInBackground(Void... args) {
			Parser parser = new Parser();

			try {
				parser.setConnection(new URL(URL_STRING).openConnection());
				parser.setEncoding(ENCODE);

				// get the tables
				AndFilter filter0 = new AndFilter();
				NodeList tableNodes = new NodeList();
				NodeFilter[] filters = { new HasAttributeFilter("border", "0"),
						new HasAttributeFilter("align", "center"),
						new HasAttributeFilter("cellpadding", "0"),
						new HasAttributeFilter("cellspacing", "0"),
						new HasAttributeFilter("width", "100%") };
				filter0.setPredicates(filters);

				// get the table with the specific tr
				AndFilter filter1 = new AndFilter(new TagNameFilter("tr"), new HasAttributeFilter(
						"align", "left"));
				AndFilter filter2 = new AndFilter(filter1, new HasParentFilter(filter0));
				for (NodeIterator e = parser.elements(); e.hasMoreNodes();)
					e.nextNode().collectInto(tableNodes, filter2);

				// titles are hyperlinks
				NodeList aNodes = new NodeList();
				// time is in <span> in <td> width="15%"
				NodeList spanNodes = new NodeList();
				Node node;
				for (NodeIterator e = tableNodes.elements(); e.hasMoreNodes();) {
					node = e.nextNode();
					node.collectInto(aNodes, new TagNameFilter("a"));
					node.collectInto(spanNodes, new TagNameFilter("span"));
				}

				// get the link text and time
				LinkedList<Headline> tmp = new LinkedList<Headline>();
				NodeIterator e1 = spanNodes.elements();
				LinkTag node1;
				Node node2;
				for (NodeIterator e = aNodes.elements(); e.hasMoreNodes();) {
					node1 = (LinkTag) e.nextNode();
					node2 = e1.nextNode();
					Headline headline = new Headline();
					headline.setTitle(node1.toPlainTextString().trim()).setUrl(node1.getLink())
							.setTime(node2.toPlainTextString().trim());
					tmp.add(headline);
				}
				return tmp;
			} catch (ParserException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO debug info sent to screen
			return null;
		}

		@Override
		protected void onPostExecute(LinkedList<Headline> result) {
			mListItems.clear();
			mListItems.addAll(result);
			mAdapter.notifyDataSetChanged();

			mListView.onRefreshComplete();
			super.onPostExecute(result);
		}
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		new DownloadHeadlineTask().execute();
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		new DownloadHeadlineTask().execute();
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

	@Override
	public void scrollToTop() {
		// TODO make action bar call this method
		mListView.scrollTo(0, 0);
	}

}
