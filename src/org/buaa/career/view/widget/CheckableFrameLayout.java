package org.buaa.career.view.widget;

import org.buaa.career.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.FrameLayout;

public class CheckableFrameLayout extends FrameLayout implements Checkable {
	private Checkable addToFavourite = null;

	public CheckableFrameLayout(Context context) {
		super(context);
	}

	public CheckableFrameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CheckableFrameLayout(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void setChecked(boolean checked) {
		if (addToFavourite == null)
			addToFavourite = ((Checkable) findViewById(R.id.tv_add_to_favourite));
		addToFavourite.setChecked(checked);
	}

	@Override
	public boolean isChecked() {
		if (addToFavourite == null)
			addToFavourite = ((Checkable) findViewById(R.id.tv_add_to_favourite));
		return addToFavourite.isChecked();
	}

	@Override
	public void toggle() {
		if (addToFavourite == null)
			addToFavourite = ((Checkable) findViewById(R.id.tv_add_to_favourite));
		addToFavourite.toggle();
	}

}
