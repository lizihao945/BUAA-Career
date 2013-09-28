package org.buaa.career.trifle;

public class Headline {
	public final String title;
	public final int category;
	public final String url;
	public final static int ZHUAN_CHANG = 1;
	public final static int UNDEFINED = -1;

	public Headline() {
		title = "not set";
		category = -1;
		url = null;
	}

	public Headline(String title, int type, String url) {
		this.title = title;
		this.category = type;
		this.url = url;
	}

}
