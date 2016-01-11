package com.jiuwu.adapter;

import java.util.List;

import com.bumptech.glide.Glide;
import com.example.nhfls.R;
import com.jiuwu.bean.DuanziBean;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapter_qutu extends BaseAdapter {

	private Activity activity;
	private List<DuanziBean> list;
	

	public Adapter_qutu(Activity activity, List<DuanziBean> list) {
		super();
		this.activity = activity;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder;
		if(arg1==null){
			holder = new ViewHolder();
			LayoutInflater inflater = activity.getLayoutInflater();
			arg1 = inflater.inflate(R.layout.listitem_duanzi, null);
			holder.image = (ImageView) arg1.findViewById(R.id.listitem_duanzi_imageView);
			holder.time = (TextView) arg1.findViewById(R.id.listitem_duanzi_time);
			holder.smalltext = (TextView) arg1.findViewById(R.id.listitem_duanzi_content);
			holder.diggtop = (Button) arg1.findViewById(R.id.listitem_duanzi_dz);
			holder.plnum = (Button) arg1.findViewById(R.id.listitem_duanzi_pl);
			arg1.setTag(holder);
		}
		holder = (ViewHolder) arg1.getTag();
		holder.image.setVisibility(View.VISIBLE);
		DuanziBean db = list.get(arg0);
		//从网络获取图片
		Glide.with(activity).load(db.getNewstext()).fitCenter().into(holder.image);
		Log.e("newstext", db.getNewstext());
		holder.time.setText(db.getNewstime());
		holder.smalltext.setText(db.getSmalltext());
		holder.plnum.setText(db.getPlnum());
		holder.diggtop.setText(db.getDiggtop());
		return arg1;
	}
	
	class ViewHolder{
		ImageView image;
		TextView time,smalltext;
		Button plnum,diggtop;
	}

}
