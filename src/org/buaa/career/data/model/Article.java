package org.buaa.career.data.model;

import android.graphics.Bitmap;

public class Article {
	private String title;
	private String startTime;
	private String endTime;
	private String place;
	private String requirements;
	private String numOfPeople;
	private String intro;
	private Bitmap bitmap;

	public Article() {
	}

	public Article setTitle(String title) {
		this.title = title;
		return this;
	}

	public Article setStartTime(String startTime) {
		this.startTime = startTime;
		return this;
	}

	public Article setEndTime(String endTime) {
		this.endTime = endTime;
		return this;
	}

	public Article setPlace(String place) {
		this.place = place;
		return this;
	}

	public Article setRequirements(String requirements) {
		this.requirements = requirements;
		return this;
	}

	public Article setNumOfPeople(String numOfPeople) {
		this.numOfPeople = numOfPeople;
		return this;
	}

	public Article setIntro(String intro) {
		this.intro = intro;
		return this;
	}

	public Article setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
		return this;
	}

}
