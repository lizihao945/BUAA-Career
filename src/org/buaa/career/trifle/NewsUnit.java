package org.buaa.career.trifle;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsUnit implements Parcelable {
	public final Headline headline;
	private Article article;

	/**
	 * Headline should be initialized so that it can be used to generate article.
	 * 
	 * @param headline
	 * @param article
	 */
	public NewsUnit(Headline headline) {
		this.headline = headline;
	}

	public NewsUnit proceed() {

		return this;
	}

	public String getTitle() {
		return headline.title;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}

}
