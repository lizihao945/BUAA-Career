package org.buaa.career.note;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;

import org.buaa.career.MainActivity;
import org.buaa.career.R;
import org.buaa.career.trifle.Headline;
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
import com.handmark.pulltorefresh.library.PullToRefreshBase.AnimationStyle;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * Fragment that contains the ViewPager, replaced when one tab claims to show detailed information.
 * 
 * @author James
 * 
 */
public class HeadlineFragment extends PullToRefreshListFragment implements
		OnRefreshListener2<ListView> {
	private OnHeadlineSelectedListener mCallBack;
	private final LinkedList<Headline> mListItems = new LinkedList<Headline>();;
	private SimpleAdapter mAdapter;
	private PullToRefreshListView mListView;

	public interface OnHeadlineSelectedListener {
		public void onHeadlineSelected(int position, String url);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		for (int i = 0; i < 20; i++) {
			Headline tmpMap = new Headline();
			mListItems.add(tmpMap);
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		mAdapter = new HeadlineAdapter(getActivity(), mListItems, R.layout.headline_item,
				new String[] { "title" }, new int[] { R.id.headline_title_text });
		mListView = getPullToRefreshListView();

		mListView.setAdapter(mAdapter);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mCallBack = (OnHeadlineSelectedListener) (MainActivity) getActivity();
				// Notify the parent activity of selected item
				mCallBack.onHeadlineSelected(position, mListItems.get(position).url);
				// Set the item as checked to be highlighted when in two-pane layout
				getListView().setItemChecked(position, true);
			}
		});
		mListView.setOnRefreshListener(this);
		setListShown(true);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	public class HeadlineAdapter extends SimpleAdapter {

		public HeadlineAdapter(Context context, LinkedList<Headline> data, int resource,
				String[] from, int[] to) {
			super(context, data, resource, from, to);
			// TODO Auto-generated constructor stub
		}
	}

	public class DownloadHeadlineTask extends AsyncTask<Void, Integer, LinkedList<Headline>> {
		private static final String URL_STRING = "http://career.buaa.edu.cn/website/zphxx.h";
		private static final String ENCODE = "GB2312";

		public DownloadHeadlineTask() {
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
				for (NodeIterator e = tableNodes.elements(); e.hasMoreNodes();) {
					e.nextNode().collectInto(aNodes, new TagNameFilter("a"));
				}
				// get the text out and analyze
				LinkedList<Headline> tmp = new LinkedList<Headline>();
				for (NodeIterator e = aNodes.elements(); e.hasMoreNodes();) {
					LinkTag node = (LinkTag) e.nextNode();
					Headline headline = new Headline((node.toPlainTextString().trim()),
							Headline.ZHUAN_CHANG, node.getLink());
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
		return new PullToRefreshListView(getActivity(), Mode.BOTH, AnimationStyle.FLIP);
	}

}
