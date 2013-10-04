package org.buaa.career.view.widget;

import org.buaa.career.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CheckableRelativeLayout extends RelativeLayout implements Checkable {
	private Checkable tab_one_tab_text;

	public CheckableRelativeLayout(Context paramContext) {
		super(paramContext);
	}

	public CheckableRelativeLayout(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
	}

	public CheckableRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public boolean isChecked() {
		getView();
		return tab_one_tab_text.isChecked();
	}

	public void setChecked(boolean paramBoolean) {
		getView();
		tab_one_tab_text.setChecked(paramBoolean);
		System.out.println(tab_one_tab_text.toString());
	}

	public void toggle() {
		getView();
		tab_one_tab_text.toggle();
	}

	private void getView() {
		if (tab_one_tab_text == null)
			tab_one_tab_text = (Checkable) findViewById(R.id.tab_one_tab_text);
	}

}