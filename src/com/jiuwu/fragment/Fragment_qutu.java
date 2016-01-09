package com.jiuwu.fragment;

import com.example.nhfls.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jiuwu.adapter.Adapter_duanzi;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment_qutu extends Fragment {
	private PullToRefreshListView listView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view =  inflater.inflate(R.layout.fragment_layout, null);
		listView = (PullToRefreshListView) view.findViewById(R.id.pullToRefreshListView);
		Adapter_duanzi adapter = new Adapter_duanzi(getActivity(),Adapter_duanzi.FRAGMENT_QUTU);
		listView.setAdapter(adapter);
		return view;
	}

}