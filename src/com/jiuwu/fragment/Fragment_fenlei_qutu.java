package com.jiuwu.fragment;

import com.example.nhfls.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class Fragment_fenlei_qutu extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_fenlei_qutu, null);
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
