package org.buaa.career.data.model;

import java.util.HashMap;

public class News extends HashMap<String, Object> {
	public static final int NOTIFICATION = 517;
	public static final int RECENT_RECRUITMENT = 518;
	public static final int CENTER_RECRUITMENT = 519;
	public static final int WORKING_RECRUITMENT = 520;
	/**
	 * 
	 */
	private static final long serialVersionUID = 2571174627939774226L;
	private String title;
	private String url;
	private String time;
	public static int UNDEFINED = -1;

	public News() {
		title = "not set";
	}

	public News setTitle(String title) {
		this.title = title;
		put("title", title);
		return this;
	}

	public News setUrl(String url) {
		this.url = url;
		put("url", url);
		return this;
	}

	public News setTime(String time) {
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
