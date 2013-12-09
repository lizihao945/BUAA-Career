package org.buaa.career.tabfragment;

import java.util.ArrayList;
import java.util.List;

import org.buaa.career.MainActivity;
import org.buaa.career.R;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TabFourFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_four_fragment, container,
				false);

		List<String> data = new ArrayList<String>();
		data.add("使用帮助");
		data.add("邮件反馈");
		data.add("浏览网站");
		ListView listView1 = (ListView) view.findViewById(R.id.listview1);
		listView1.setAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, data));
		listView1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				switch (position) {
				case 0:
					new AlertDialog.Builder(getActivity())
							.setTitle("使用帮助")
							.setMessage(
									"本软件的内容与北航就业信息网的内容一致，用户可以收藏感兴趣的内容。如果您觉得详细内容界面的浏览体验不好，可以通过选项跳转到手机浏览器进行浏览。")
							.show();
					break;
				case 1:
					((MainActivity) getActivity()).sendEmail();
					break;
				case 2:
					((MainActivity) getActivity()).explorer();
					break;
				}
			}
		});
		return view;
	}
}
