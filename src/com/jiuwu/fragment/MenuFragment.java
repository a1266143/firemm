package com.jiuwu.fragment;

import com.example.nhfls.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MenuFragment extends Fragment {

	private Button fragment_ment_btn;
	private RelativeLayout re;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_menu, null);
		//fragment_ment_btn = (Button) view.findViewById(R.id.fragment_menu_Btn);
		re = (RelativeLayout) view.findViewById(R.id.fragment_menu_relativelayout);
		//按钮事件监听器并设置
		MenuOnclickListener listener = new MenuOnclickListener();
		//fragment_ment_btn.setOnClickListener(listener);
		re.setOnClickListener(listener);
		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	/**
	 * 按键监听器
	 * @author xiaojunLi
	 *
	 */
	class MenuOnclickListener implements OnClickListener{

		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			/*case R.id.fragment_menu_Btn:
				Toast.makeText(getActivity(), "你点击了测试按钮", Toast.LENGTH_SHORT).show();
				break;*/
			case R.id.fragment_menu_relativelayout:
				//将布局文件的点击事件重写，不然会导致可以按到菜单下面布局的监听事件上
				break;
			}
		}
		
	}
	
}
