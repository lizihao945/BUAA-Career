package org.buaa.career.note;

import org.buaa.career.R;
import org.buaa.career.trifle.Article;
import org.buaa.career.trifle.NewsUnit;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ArticleFragment extends Fragment {
	private int currPosition;
	private AsyncTask<Object, Integer, Article> mAsyncTask = null;
	private NewsUnit mNewsUnit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try {
			mNewsUnit = savedInstanceState.getParcelable("news_unit");
		} catch (NullPointerException e) {
			System.err.println("!\"!@!#!!3");
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onStart();
		// System.out.println("onCreateView() called for ArticleFragment" + currPosition);
		View view = inflater.inflate(R.layout.fragment_one_article, container, false);
		Bundle args;
		if ((args = getArguments()) != null) {
			currPosition = args.getInt("position");
		}
		TextView textView = (TextView) view.findViewById(R.id.text_view);
		textView.setText(mNewsUnit.getTitle());
		return view;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// Save the current article selection in case we need to recreate the
		// fragment
		outState.putInt("position", currPosition);
		outState.putString("headline", mNewsUnit.getTitle());
	}

	@Override
	public void onDestroy() {
		if (mAsyncTask != null)
			mAsyncTask.cancel(true);
		super.onDestroy();
	}

}
