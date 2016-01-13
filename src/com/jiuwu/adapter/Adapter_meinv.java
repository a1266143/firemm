package com.jiuwu.adapter;

import com.example.nhfls.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapter_meinv extends BaseAdapter {

	private Context context;
	public Adapter_meinv(Context context){
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return 20;
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
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.listitem_meinv, null);
			viewHolder.imageView = (ImageView) convertView.findViewById(R.id.listitem_zhuanji_imageview);
			viewHolder.title = (TextView) convertView.findViewById(R.id.listitem_zhuanji_title);
			viewHolder.time = (TextView) convertView.findViewById(R.id.listitem_zhuanji_time);
			viewHolder.dianzan = (TextView) convertView.findViewById(R.id.listitem_zhuanji_dianzan);
			convertView.setTag(viewHolder);
		}
		viewHolder = (ViewHolder) convertView.getTag();
		return convertView;
	}
	
	class ViewHolder{
		ImageView imageView;
		TextView title,time,dianzan;
	}

}
