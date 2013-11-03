package org.buaa.career.trifle;

import java.util.LinkedList;

import org.buaa.career.data.model.News;

import android.content.Context;
import android.widget.SimpleAdapter;

public class HeadlineAdapter extends SimpleAdapter {

	public HeadlineAdapter(Context context, LinkedList<News> data, int resource, String[] from,
			int[] to) {
		super(context, data, resource, from, to);
	}
}
