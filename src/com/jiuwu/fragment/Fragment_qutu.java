package com.jiuwu.fragment;

import java.util.ArrayList;
import java.util.Collection;

import com.bumptech.glide.Glide;
import com.example.nhfls.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jiuwu.adapter.Adapter_qutu;
import com.jiuwu.bean.DuanziBean;
import com.jiuwu.utils.NetUtils;
import com.jiuwu.utils.StaticCode;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

public class Fragment_qutu extends Fragment {
	private PullToRefreshListView listView;
	private Adapter_qutu adapter;
	private ArrayList<DuanziBean> arr;
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
				if(page==1){
					arr = (ArrayList<DuanziBean>) msg.getData().getSerializable("ArrayList");
					adapter = new Adapter_qutu(Fragment_qutu.this, arr);
					listView.setAdapter(adapter);
					/*listView.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
								long arg3) {
							Toast.makeText(getActivity(), "你点击了"+arg2+"项", Toast.LENGTH_SHORT).show();
							
						}
					});*/
				}
				//如果是分页加载
				else{
					arr.addAll((Collection<? extends DuanziBean>) msg.getData().getSerializable("ArrayList"));
					adapter.notifyDataSetChanged();
				}
			}
			listView.onRefreshComplete();
			listView.setOnScrollListener(new OnScrollListener() {
				
				@Override
				public void onScrollStateChanged(AbsListView arg0, int arg1) {
					
				}
				
				@Override
				public void onScroll(AbsListView arg0, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
					Log.e("第一个可见item下标", firstVisibleItem+"");
					Log.e("可见item数目", visibleItemCount+"");
					Log.e("item总和", totalItemCount+"");
				}
			});
		}
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view =  inflater.inflate(R.layout.fragment_layout, null);
		listView = (PullToRefreshListView) view.findViewById(R.id.pullToRefreshListView);
		listView.setMode(Mode.BOTH);
		listView.setOnRefreshListener(new OnrefreshListener());
		NetUtils.NetForQuTu(handler, page);
		return view;
	}
	
	class OnrefreshListener implements OnRefreshListener2<ListView>{

		@Override
		public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
			page = 1;
			NetUtils.NetForQuTu(handler, page);
		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
			page += 1;
			NetUtils.NetForQuTu(handler, page);
		}
		
	}

}
