package com.jiuwu.adapter;

import java.util.List;

import com.example.nhfls.R;
import com.jiuwu.bean.DuanziBean;
import com.jiuwu.nhfls.ImageActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapter_duanzi extends BaseAdapter {
	
	private Context context;
	private int fragment_what;
	public static final int FRAGMENT_DUANZI=0,FRAGMENT_QUTU=1;
	//private List<DuanziBean> list;
	

	public Adapter_duanzi(Context context,int fragment_what) {
		this.context = context;
		//this.list = list;
		this.fragment_what = fragment_what;
	}

	@Override
	public int getCount() {
		return 30;
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
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder viewHolder;
		if(convertView == null){
			viewHolder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.listitem_duanzi, null);
			viewHolder.time = (TextView) convertView.findViewById(R.id.listitem_duanzi_time);
			viewHolder.content = (TextView) convertView.findViewById(R.id.listitem_duanzi_content);
			viewHolder.dianzan = (Button) convertView.findViewById(R.id.listitem_duanzi_dz);
			viewHolder.shoucang = (Button) convertView.findViewById(R.id.listitem_duanzi_sc);
			viewHolder.fenxiang = (Button) convertView.findViewById(R.id.listitem_duanzi_fx);
			viewHolder.pinglun = (Button) convertView.findViewById(R.id.listitem_duanzi_pl);
			viewHolder.imageView = (ImageView) convertView.findViewById(R.id.listitem_duanzi_imageView);
			convertView.setTag(viewHolder);
		}
		viewHolder = (ViewHolder) convertView.getTag();
		if(fragment_what == FRAGMENT_QUTU){
			viewHolder.imageView.setVisibility(View.VISIBLE);
			viewHolder.imageView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(context,ImageActivity.class);
					context.startActivity(intent);
				}
			});
		}
			
		return convertView;
	}
	
	class ViewHolder{
		TextView time,content;
		Button dianzan,shoucang,fenxiang,pinglun;
		ImageView imageView;
	}
}
