package com.jiuwu.fragment;

import java.util.ArrayList;
import java.util.Collection;

import com.example.nhfls.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.jiuwu.adapter.Adapter_duanzi;
import com.jiuwu.bean.DuanziBean;
import com.jiuwu.utils.NetUtils;
import com.jiuwu.utils.StaticCode;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class Fragment_duanzi extends Fragment {

	private PullToRefreshListView listView;
	private ArrayList<DuanziBean> arr;
	private Adapter_duanzi adapter;
	private int page = 1;
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
					adapter = new Adapter_duanzi(getActivity(), arr);
					listView.setAdapter(adapter);
				}
				//如果是分页加载
				else{
					//将获取过来的列表增加到以前的列表上
					arr.addAll((Collection<? extends DuanziBean>) msg.getData().getSerializable("ArrayList"));
					adapter.notifyDataSetChanged();
				}
					
			}
			listView.onRefreshComplete();
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_layout, null);
		listView = (PullToRefreshListView) view
				.findViewById(R.id.pullToRefreshListView);
		listView.setMode(Mode.BOTH);
		listView.setOnRefreshListener(new DuanziOnRefreshListener2());
		//获取网络段子
		NetUtils.NetForDuanZi(handler, page);
		return view;
	}

	class DuanziOnRefreshListener2 implements OnRefreshListener2<ListView> {

		@Override
		public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
			page = 1;
			NetUtils.NetForDuanZi(handler, page);
		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
			page += 1;
			NetUtils.NetForDuanZi(handler, page);
		}

	}

}
