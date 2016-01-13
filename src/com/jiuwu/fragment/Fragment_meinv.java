package com.jiuwu.fragment;

import com.example.nhfls.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jiuwu.adapter.Adapter_meinv;
import com.jiuwu.utils.StaticCode;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class Fragment_meinv extends Fragment {

	private PullToRefreshListView listView;
	
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			//网络错误
			if(msg.what == StaticCode.MISTAKE_NET){
				Toast.makeText(getActivity(), "网络错误", Toast.LENGTH_SHORT).show();
			}else if(msg.what == StaticCode.MISTAKE_JSON){
				Toast.makeText(getActivity(), "Json解析错误", Toast.LENGTH_SHORT).show();
			}
			
		}
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view =  inflater.inflate(R.layout.fragment_layout, null);
		listView = (PullToRefreshListView) view.findViewById(R.id.pullToRefreshListView);
		Adapter_meinv adapter = new Adapter_meinv(getActivity());
		listView.setAdapter(adapter);
		return view;
	}

}
