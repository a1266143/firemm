package com.jiuwu.fragment;

import com.example.nhfls.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class Fragment_fenlei extends Fragment {

	private RelativeLayout fragment_fenlei_lin;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_fenlei, null);
		fragment_fenlei_lin = (RelativeLayout) view.findViewById(R.id.fragment_fenlei_lin);
		Fragment_fenleiOnClickListener listener = new Fragment_fenleiOnClickListener();
		fragment_fenlei_lin.setOnClickListener(listener);
		return view;
	}
	
	class Fragment_fenleiOnClickListener implements OnClickListener{

		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.fragment_fenlei_lin:
				
				break;

			default:
				break;
			}
		}
		
	}
	
	

}
