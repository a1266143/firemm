package com.jiuwu.fragment;

import com.example.nhfls.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.jiuwu.adapter.Adapter_duanzi;
import com.jiuwu.bean.DuanziBean;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class Fragment_duanzi extends Fragment {

	private PullToRefreshListView listView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_layout, null);
		listView = (PullToRefreshListView) view
				.findViewById(R.id.pullToRefreshListView);
		listView.setMode(Mode.BOTH);
		listView.setOnRefreshListener(new DuanziOnRefreshListener2() {
		});
		Adapter_duanzi adapter = new Adapter_duanzi(getActivity(),
				Adapter_duanzi.FRAGMENT_DUANZI);
		listView.setAdapter(adapter);
		return view;
	}

	class DuanziOnRefreshListener2 implements OnRefreshListener2<ListView> {

		@Override
		public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

		}

	}

}
