package org.buaa.career.tabfragment;

import java.util.ArrayList;
import java.util.List;

import org.buaa.career.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TabFourFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_four_fragment, container, false);
		
		List<String> data = new ArrayList<String>();
		data.add("使用帮助");
		data.add("使用反馈");
		data.add("联系我们");
		ListView listView1 = (ListView) view.findViewById(R.id.listview1);
		listView1.setAdapter(new ArrayAdapter<String>(getActivity(),  android.R.layout.simple_list_item_1, data));
		return view;
	}
}
