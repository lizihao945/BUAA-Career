package org.buaa.career.tabfragment;

import org.buaa.career.data.model.News;

import android.os.Bundle;

public class TabOneFragment extends NewsFragment {
	public TabOneFragment() {
		mChannel = News.RECENT_RECRUITMENT;
	}
}