package org.buaa.career.trifle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import org.buaa.career.MainActivity;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import android.os.AsyncTask;

public class DownloadNewsUnitTask extends AsyncTask<Void, Integer, ArrayList<NewsUnit>> {
	private static final String URL_STRING = "http://career.buaa.edu.cn/website/zphxx.h";
	private static final String ENCODE = "GB2312";
	private MainActivity mActivity;

	public DownloadNewsUnitTask(MainActivity activity) {
		mActivity = activity;
	}

	@Override
	protected ArrayList<NewsUnit> doInBackground(Void... args) {
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
			ArrayList<NewsUnit> tmp = new ArrayList<NewsUnit>();
			for (NodeIterator e = aNodes.elements(); e.hasMoreNodes();) {
				Headline headline = new Headline((e.nextNode().toPlainTextString().trim()),
						Headline.ZHUAN_CHANG);
				tmp.add(new NewsUnit(headline));
				System.out.println(tmp.get(tmp.size() - 1).getTitle());
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
	protected void onPostExecute(ArrayList<NewsUnit> result) {
		if (result != null)
			mActivity.getMainFragment().onPostDataLoding(result);
		super.onPostExecute(result);
	}

}
