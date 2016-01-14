package com.jiuwu.fragment;

import java.util.ArrayList;
import java.util.Collection;

import com.example.nhfls.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jiuwu.adapter.Adapter_duanzi;
import com.jiuwu.adapter.Adapter_shipin;
import com.jiuwu.bean.DuanziBean;
import com.jiuwu.nhfls.PlayActivity;
import com.jiuwu.utils.NetUtils;
import com.jiuwu.utils.StaticCode;
import com.jiuwu.utils.Utils;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Fragment_shipin extends Fragment {

	private PullToRefreshListView listView;
	private int page = 1;
	private Adapter_shipin adapter;
	private ArrayList<DuanziBean> arr;
	
	Handler handler = new Handler(){

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			//网络错误
			if(msg.what == StaticCode.MISTAKE_NET){
				Toast.makeText(getActivity(), "网络错误", Toast.LENGTH_SHORT).show();
				if(page>1)
					page -= 1;
			}else if(msg.what == StaticCode.MISTAKE_JSON){
				Toast.makeText(getActivity(), "Json解析错误", Toast.LENGTH_SHORT).show();
				if(page>1)
					page -= 1;
			}else{
				//如果是首次加载
				if(page == 1){
					arr = (ArrayList<DuanziBean>) msg.getData().getSerializable("ArrayList");
					adapter = new Adapter_shipin(getActivity(), arr);
					listView.setAdapter(adapter);
				}
				//如果是分页加载
				else{
					//将获取过来的列表增加到以前的列表上
					arr.addAll((Collection<? extends DuanziBean>) msg.getData().getSerializable("ArrayList"));
					adapter.notifyDataSetChanged();
				}
					
			}
			listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(getActivity(),PlayActivity.class);
				//将视频播放地址解析成id
				String s = Utils.analysisURL(arr.get(arg2-1).getNewstext());
				intent.putExtra("vid", s);
				startActivity(intent);
			}
		});
			listView.onRefreshComplete();
		}
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_layout, null);
		listView = (PullToRefreshListView) view.findViewById(R.id.pullToRefreshListView);
		listView.setMode(Mode.BOTH);
		NetUtils.NetForShiPin(handler, page);
		listView.setOnRefreshListener(new ShipinRefreshListener());
		return view;
	}
	
	class ShipinRefreshListener implements OnRefreshListener2<ListView>{

		@Override
		public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
			page = 1;
			NetUtils.NetForShiPin(handler, page);
		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
			page += 1;
			NetUtils.NetForShiPin(handler, page);
		}
		
	}
	

}
