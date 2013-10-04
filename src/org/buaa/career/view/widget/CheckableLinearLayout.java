package org.buaa.career.view.widget;

import org.buaa.career.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.LinearLayout;

public class CheckableLinearLayout extends LinearLayout implements Checkable {
	private Checkable headline_desc_text;
	private Checkable headline_title_text;

	public CheckableLinearLayout(Context paramContext) {
		super(paramContext);
	}

	public CheckableLinearLayout(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
	}

	public CheckableLinearLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public boolean isChecked() {
		if (headline_title_text == null)
			headline_title_text = ((Checkable) findViewById(R.id.headline_title_text));
		if (headline_desc_text == null)
			headline_desc_text = ((Checkable) findViewById(R.id.headline_desc_text));
		return headline_desc_text.isChecked();
	}

	public void setChecked(boolean paramBoolean) {
		if (headline_title_text == null)
			headline_title_text = ((Checkable) findViewById(R.id.headline_title_text));
		if (this.headline_desc_text == null)
			headline_desc_text = ((Checkable) findViewById(R.id.headline_desc_text));
		headline_desc_text.setChecked(paramBoolean);
		headline_title_text.setChecked(paramBoolean);
	}

	public void toggle() {
		if (headline_title_text == null)
			headline_title_text = ((Checkable) findViewById(R.id.headline_title_text));
		if (headline_desc_text == null)
			headline_desc_text = ((Checkable) findViewById(R.id.headline_desc_text));
		headline_desc_text.toggle();
		headline_title_text.toggle();
	}
}