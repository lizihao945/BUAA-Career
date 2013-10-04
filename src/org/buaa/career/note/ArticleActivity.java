package org.buaa.career.note;

import org.buaa.career.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class ArticleFragment extends Fragment {
	private int mPosition;
	private String url;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPosition = getArguments().getInt("position");
		url = getArguments().getString("url");

		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onStart();
		View view = inflater.inflate(R.layout.fragment_one_article, container, false);
		WebView webView = (WebView) view.findViewById(R.id.web_view);
		webView.loadUrl(url);
		return view;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// Save the current article selection in case we need to recreate the
		// fragment
		outState.putInt("position", mPosition);
	}
}
