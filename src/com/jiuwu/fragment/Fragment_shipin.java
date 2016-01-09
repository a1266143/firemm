package com.jiuwu.fragment;

import com.example.nhfls.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jiuwu.adapter.Adapter_shipin;
import com.jiuwu.nhfls.PlayActivity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Fragment_shipin extends Fragment {

	private PullToRefreshListView listView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_layout, null);
		listView = (PullToRefreshListView) view.findViewById(R.id.pullToRefreshListView);
		Adapter_shipin adapter = new Adapter_shipin(getActivity());
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(getActivity(), "你点击了item"+arg2, Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(getActivity(),PlayActivity.class);
				startActivity(intent);
			}
		});
		return view;
	}
	

}
