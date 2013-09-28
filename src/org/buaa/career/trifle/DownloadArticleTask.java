package org.buaa.career.trifle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import org.buaa.career.MainActivity;
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

import android.os.AsyncTask;

public class DownloadArticleTask extends AsyncTask<Void, Integer, Article> {
	private final String URL_STRING;
	private static final String ENCODE = "GB2312";
	private MainActivity mActivity;

	public DownloadArticleTask(MainActivity activity, String url) {
		mActivity = activity;
		URL_STRING = url;
	}

	@Override
	protected Article doInBackground(Void... args) {
		Parser parser = new Parser();

		try {
			parser.setConnection(new URL(URL_STRING).openConnection());
			parser.setEncoding(ENCODE);

			// get the div class="list pic_news"
			NodeList divNodes = new NodeList();
			for (NodeIterator e = parser.elements(); e.hasMoreNodes();)
				e.nextNode().collectInto(
						divNodes,
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class",
								"list pic_news")));
			// get the title
			NodeList titleNodes = new NodeList();
			for (NodeIterator e = divNodes.elements(); e.hasMoreNodes();)
				e.nextNode().collectInto(
						titleNodes,
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class",
								"ctitle ctitle1")));

			// get the context
			NodeList contextNodes = new NodeList();
			for (NodeIterator e = divNodes.elements(); e.hasMoreNodes();)
				e.nextNode().collectInto(
						titleNodes,
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class",
								"pbox_data")));
			// get the details
			NodeList detailNodes = new NodeList();
			for (NodeIterator e = contextNodes.elements(); e.hasMoreNodes();)
				e.nextNode().collectInto(detailNodes, new TagNameFilter("b"));
			Article tmp = new Article();

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
	protected void onPostExecute(Article result) {
		if (result != null)
			mActivity.getMainFragment().onPostDataLoding(result);
		super.onPostExecute(result);
	}

}
