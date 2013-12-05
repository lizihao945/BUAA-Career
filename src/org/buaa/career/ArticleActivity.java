package org.buaa.career;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;

import org.buaa.career.data.db.DBTask;
import org.buaa.career.trifle.Constant;
import org.buaa.career.view.widget.CheckableFrameLayout;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.lexer.Page;
import org.htmlparser.tags.Div;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class ArticleActivity extends SherlockActivity {
	public static String TAG = "ARTICLE_ACTIVITY";

	private String mFilePath;
	private String mFileName;
	private int mChannel;
	private int mPosition;
	private WebView mWebView;
	private String mUrl;
	private ActionBar mActionBar;
	private CheckableFrameLayout mAddToFavourite;
	private Node titleNode;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.article_activity);

		Bundle args = getIntent().getExtras();
		mChannel = args.getInt("channel");
		mPosition = args.getInt("position");
		mUrl = args.getString("url");

		mFileName = mChannel + "_" + mPosition + ".html";
		mFilePath = "file://" + getFilesDir().getAbsolutePath() + "/" + mFileName;
		
		mWebView = (WebView) findViewById(R.id.web_view);
		mWebView.getSettings().setDefaultTextEncodingName(Constant.ENCODE);

		File[] files = getFilesDir().listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String filename) {
				if (filename.equals(mFileName))
					return true;
				return false;
			}
		});
		if (files.length != 0 && files[0].exists())
			mWebView.loadUrl(mFilePath);
		else
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
		mActionBar = getSupportActionBar();
		mActionBar.setBackgroundDrawable(getResources().getDrawable(R.color.bc_blue));
		mActionBar.setCustomView(R.layout.article_activity_action_bar);
		mActionBar.setDisplayShowCustomEnabled(true);
		mActionBar.setHomeButtonEnabled(true);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowHomeEnabled(true);

		mAddToFavourite = (CheckableFrameLayout) findViewById(R.id.add_to_favourite);
		mAddToFavourite.setChecked(DBTask.isStarred(mUrl, this));
		mAddToFavourite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mAddToFavourite.isChecked())
					DBTask.removeStarredNews(mUrl, mChannel, ArticleActivity.this);
				else {
					Log.v(TAG, "article in channel: " + mChannel);
					DBTask.addStarrdNews(mUrl, mChannel, ArticleActivity.this);
				}

				mAddToFavourite.toggle();
			}
		});
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public class DownloadArticleTask extends AsyncTask<Void, Integer, Node> {

		public DownloadArticleTask() {
		}

		@Override
		protected Node doInBackground(Void... args) {
			Parser parser = new Parser();

			try {
				parser.setConnection(new URL(mUrl).openConnection());
				parser.setEncoding(Constant.ENCODE);

				// get the tables
				AndFilter highestFilter = new AndFilter();
				NodeList divNodes = new NodeList();
				NodeFilter[] filters = { new HasAttributeFilter("border", "0"),
						new HasAttributeFilter("align", "center"),
						new HasAttributeFilter("cellpadding", "0"),
						new HasAttributeFilter("cellspacing", "0"),
						new HasAttributeFilter("width", "939"), new HasAttributeFilter("id", "__") };
				highestFilter.setPredicates(filters);
				
				// get the div in the table
				AndFilter andFilter01 = new AndFilter(new TagNameFilter("div"),
						new HasAttributeFilter("style", "width: 100%"));
				AndFilter andFilter02 = new AndFilter(new TagNameFilter("div"),
						new HasAttributeFilter("class", "list pic_news"));

				OrFilter orFilter = new OrFilter(andFilter01, andFilter02);

				AndFilter filter2 = new AndFilter(orFilter,
						new HasParentFilter(highestFilter, true));
				for (NodeIterator e = parser.elements(); e.hasMoreNodes();)
					e.nextNode().collectInto(divNodes, filter2);
				
				// get the title div
				NodeList title = new NodeList();
				HasAttributeFilter titleFilter = new HasAttributeFilter("class", "ctitle ctitle1");
				for (NodeIterator e = divNodes.elements(); e.hasMoreNodes();)
					e.nextNode().collectInto(title, titleFilter);
				Div div = (Div) title.elementAt(0);
				div.setAttribute("style", "font-size:25px");
				
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
			try {
				FileOutputStream outputStream = openFileOutput(mFileName, Context.MODE_PRIVATE);
				outputStream.write(result.toHtml().getBytes(Constant.ENCODE));
				outputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			mWebView.loadUrl(mFilePath);
			super.onPostExecute(result);
		}
	}

}
