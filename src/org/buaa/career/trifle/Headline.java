package org.buaa.career.trifle;

public class Headline {
	public final String title;
	public final int category;
	public final static int ZHUAN_CHANG = 1;
	public final static int UNDEFINED = -1;
	
	public Headline() {
		title = "not setted";
		category = -1;
	}
	
	public Headline(String title, int type) {
		this.title = title;
		this.category = type;
	}

}
