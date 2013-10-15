package org.buaa.career.view.widget;

import org.buaa.career.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.RelativeLayout;

public class CheckableRelativeLayout extends RelativeLayout implements Checkable {
	private Checkable tab_one_tab_text = null;

	public CheckableRelativeLayout(Context paramContext) {
		super(paramContext);
	}

	public CheckableRelativeLayout(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
	}

	public CheckableRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean isChecked() {
		getView();
		return tab_one_tab_text.isChecked();
	}

	@Override
	public void setChecked(boolean checked) {
		getView();
		tab_one_tab_text.setChecked(checked);
	}

	@Override
	public void toggle() {
		getView();
		tab_one_tab_text.toggle();
	}

	private void getView() {
		if (tab_one_tab_text == null)
			tab_one_tab_text = (Checkable) findViewById(R.id.tab_one_tab_text);
	}

}