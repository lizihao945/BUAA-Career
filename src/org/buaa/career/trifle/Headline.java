package org.buaa.career.trifle;

import java.util.HashMap;

public class Headline extends HashMap<String, Object> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2571174627939774226L;
	public final String title;
	public final int category;
	public final String url;
	public final static int ZHUAN_CHANG = 1;
	public final static int UNDEFINED = -1;

	public Headline() {
		this("not set");
	}

	public Headline(String string) {
		this(string, ZHUAN_CHANG, "");
	}
	
	public Headline(String title, int type, String url) {
		this.title = title;
		this.category = type;
		this.url = url;
		put("title", title);
	}

}
