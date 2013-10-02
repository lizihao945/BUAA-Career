package org.buaa.career;

import org.buaa.career.note.ArticleFragment;
import org.buaa.career.note.HeadlineFragment.OnHeadlineSelectedListener;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

public class MainActivity extends FragmentActivity implements OnHeadlineSelectedListener {
	private MainFragment mMainFragment;
	private ArticleFragment mArticleFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_activity);

		mArticleFragment = new ArticleFragment();
		mMainFragment = new MainFragment();
		getSupportFragmentManager().beginTransaction().add(R.id.container, mMainFragment).commit();
	}

	@Override
	public void onHeadlineSelected(int position, String url) {
		Bundle args = new Bundle();
		args.putInt("position", position);
		args.putString("url", url);
		mArticleFragment.setArguments(args);
		getSupportFragmentManager().beginTransaction()
				.setCustomAnimations(R.anim.slide_in_from_right, 0, 0, R.anim.slide_out_to_right)
				.add(R.id.container, mArticleFragment).addToBackStack(null).commit();
	}
}
