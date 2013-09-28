package org.buaa.career.trifle;

public class NewsUnit {
	public final Headline headline;

	/**
	 * Headline should be initialized so that it can be used to generate article.
	 * 
	 * @param headline
	 * @param article
	 */
	public NewsUnit(Headline headline) {
		this.headline = headline;
	}

	public String getTitle() {
		return headline.title;
	}

}
