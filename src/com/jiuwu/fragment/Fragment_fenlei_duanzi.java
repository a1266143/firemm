package com.jiuwu.fragment;

import com.example.nhfls.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Fragment_fenlei_duanzi extends Fragment {
	private ListView listview;
	private String[] fenLeiDuanZi = {"世说新语","冷笑话","脑筋急转弯","内涵段子"};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_fenlei_duanzi, null);
		listview = (ListView) view.findViewById(R.id.fragment_fenlei_duanzi_listview);
		ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_expandable_list_item_1, fenLeiDuanZi);
		listview.setAdapter(adapter);
		return view;
	}

}
