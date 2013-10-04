package org.buaa.career.note;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import org.buaa.career.R;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class ArticleActivity extends SherlockActivity {
	private String mFilePath;
	private String mFileName;
	private int mPosition;
	private WebView mWebView;
	private String url;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.article_activity);
		Bundle args = getIntent().getExtras();
		mFileName = "article" + mPosition + ".html";
		mFilePath = "file://" + getFilesDir().getAbsolutePath() + "/" + mFileName;
		mPosition = args.getInt("position");
		url = args.getString("url");
		mWebView = (WebView) findViewById(R.id.web_view);
		getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.bc_blue));
		new DownloadArticleTask().execute();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// Save the current article selection in case we need to recreate the
		// fragment
		outState.putInt("position", mPosition);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.article_fragment_menu, menu);
		return true;
	}

	public class DownloadArticleTask extends AsyncTask<Void, Integer, Node> {
		private static final String ENCODE = "GB2312";

		public DownloadArticleTask() {
		}

		@Override
		protected Node doInBackground(Void... args) {
			Parser parser = new Parser();

			try {
				parser.setConnection(new URL(url).openConnection());
				parser.setEncoding(ENCODE);

				// get the tables
				AndFilter filter0 = new AndFilter();
				NodeList divNodes = new NodeList();
				NodeFilter[] filters = { new HasAttributeFilter("border", "0"),
						new HasAttributeFilter("align", "center"),
						new HasAttributeFilter("cellpadding", "0"),
						new HasAttributeFilter("cellspacing", "0"),
						new HasAttributeFilter("width", "939"), new HasAttributeFilter("id", "__") };
				filter0.setPredicates(filters);

				// get the div in the table
				AndFilter filter1 = new AndFilter(new TagNameFilter("div"), new HasAttributeFilter(
						"style", "width: 100%"));
				AndFilter filter2 = new AndFilter(filter1, new HasParentFilter(filter0, true));
				for (NodeIterator e = parser.elements(); e.hasMoreNodes();)
					e.nextNode().collectInto(divNodes, filter2);
				return divNodes.elementAt(0);
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
		protected void onPostExecute(Node result) {
			// position starts from zero
			try {
				FileOutputStream outputStream = openFileOutput(mFileName, Context.MODE_PRIVATE);
				outputStream.write(result.toHtml().getBytes(ENCODE));
				outputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			mWebView.getSettings().setDefaultTextEncodingName(ENCODE);
			mWebView.loadUrl(mFilePath);
			super.onPostExecute(result);
		}
	}

}
