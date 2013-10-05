package org.buaa.career.data.model;

import java.util.HashMap;

public class Headline extends HashMap<String, Object> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2571174627939774226L;
	private String title;
	private String url;
	private String time;
	public static int UNDEFINED = -1;

	public Headline() {
		title = "not set";
	}

	public Headline setTitle(String title) {
		this.title = title;
		put("title", title);
		return this;
	}

	public Headline setUrl(String url) {
		this.url = url;
		put("url", url);
		return this;
	}

	public Headline setTime(String time) {
		this.time = time;
		put("time", time);
		return this;
	}

	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return url;
	}

	public String getTime() {
		return time;
	}

}
