package org.buaa.career.trifle;

import android.graphics.Bitmap;

public class Article {
	public final String headline;
	public final Bitmap bitmap;

	public Article(String headline) {
		this.headline = headline;
		this.bitmap = null;
	}

	public Article(String headline, Bitmap bitmap) {
		this.headline = headline;
		this.bitmap = bitmap;
	}

}
