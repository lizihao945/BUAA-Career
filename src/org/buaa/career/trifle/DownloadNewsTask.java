package org.buaa.career.trifle;

import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;

import org.buaa.career.data.db.DBTask;
import org.buaa.career.data.model.News;
import org.buaa.career.tabfragment.NewsFragment;
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

import android.content.Context;
import android.os.AsyncTask;

public class DownloadNewsTask extends AsyncTask<Void, Integer, LinkedList<News>> {
	private static final String NOTIFICATION_URL = "http://career.buaa.edu.cn/website/zytz.h";
	private static final String RECENT_URL = "http://career.buaa.edu.cn/website/zphxx.h";
	private static final String CENTER_URL = "http://career.buaa.edu.cn/website/zpxx_info.h";
	private static final String WORKING_URL = "http://career.buaa.edu.cn/website/zxzpxx.h";

	private final String URL_STRING;
	private Context mContext;
	private NewsFragment mNewsFragment;
	private int mChannl;
	private int mPageNum;

	public DownloadNewsTask(NewsFragment father, int channel, int page) {
		mNewsFragment = father;
		mChannl = channel;
		mContext = father.getActivity();
		mPageNum = page;
		switch (mChannl) {
		case News.NOTIFICATION:
			URL_STRING = NOTIFICATION_URL + "?pageNo=" + mPageNum;
			break;
		case News.RECENT_RECRUITMENT:
			URL_STRING = RECENT_URL + "?pageNo=" + mPageNum;
			break;
		case News.CENTER_RECRUITMENT:
			URL_STRING = CENTER_URL + "?pageNo=" + mPageNum;
			break;
		case News.WORKING_RECRUITMENT:
			URL_STRING = WORKING_URL + "?pageNo=" + mPageNum;
			break;
		default:
			URL_STRING = null;
		}
	}

	@Override
	protected LinkedList<News> doInBackground(Void... args) {
		Parser parser = new Parser();

		try {
			URLConnection connection = new URL(URL_STRING).openConnection();
			connection.setConnectTimeout(5000);

			parser.setConnection(connection);
			parser.setEncoding(Constant.ENCODE);

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
			LinkedList<News> tmp = new LinkedList<News>();
			NodeIterator e1 = spanNodes.elements();
			LinkTag node1;
			Node node2;
			for (NodeIterator e = aNodes.elements(); e.hasMoreNodes();) {
				node1 = (LinkTag) e.nextNode();
				node2 = e1.nextNode();
				News news = new News();
				// toPlainTextString
				news.setTitle(node1.toPlainTextString().trim()).setUrl(node1.getLink())
						.setTime(node2.toPlainTextString().trim()).setChannel(mChannl);
				// 更新数据库中第一页的内容
				if (mPageNum == 1)
					DBTask.addNews(news, mContext);
				tmp.add(news);
			}
			return tmp;
		} catch (Exception e) {
			// Exception delayed to onPostExecute
		}
		return null;
	}

	@Override
	protected void onPostExecute(LinkedList<News> result) {
		// set a flag to determine if it's loading the first web page
		boolean flag = mPageNum == 1 ? true : false;
		if (result == null) {
			mNewsFragment.updateFailed(flag);
			return;
		}
		((NewsFragment) mNewsFragment).updateList(flag, result);
		super.onPostExecute(result);
	}
}
