package org.buaa.career.note;

import org.buaa.career.MainActivity;
import org.buaa.career.R;
import org.buaa.career.trifle.Article;
import org.buaa.career.trifle.Headline;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ArticleFragment extends Fragment {
	private int mPosition;
	private Headline mHeadline;
	private Article mArticle;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPosition = getArguments().getInt("position");
		mHeadline = ((HeadlineFragment) ((MainActivity) getActivity()).getMainFragment()
				.getTabFragments().get(0)).getHeadlines().get(mPosition);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onStart();
		// System.out.println("onCreateView() called for ArticleFragment" + currPosition);
		View view = inflater.inflate(R.layout.fragment_one_article, container, false);
		TextView textView = (TextView) view.findViewById(R.id.text_view);
		textView.setText(mHeadline.title);
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
