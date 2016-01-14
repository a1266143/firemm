package com.jiuwu.adapter;

import java.util.List;

import com.example.nhfls.R;
import com.jiuwu.bean.DuanziBean;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapter_shipin extends BaseAdapter {

	private Context context;
	private List<DuanziBean> list;
	public Adapter_shipin(Context context,List<DuanziBean> list){
		this.context = context;
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
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder viewHolder;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(convertView==null){
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.listitem_shipin, null);
			viewHolder.imageView = (ImageView) convertView.findViewById(R.id.listitem_shipin_imageview);
			viewHolder.title = (TextView) convertView.findViewById(R.id.listitem_shipin_title);
			viewHolder.time = (TextView) convertView.findViewById(R.id.listitem_shipin_time);
			viewHolder.playNumber = (TextView) convertView.findViewById(R.id.listitem_shipin_play);
			viewHolder.pinglun = (TextView) convertView.findViewById(R.id.listitem_shipin_pinglun);
			convertView.setTag(viewHolder);
		}
		viewHolder = (ViewHolder) convertView.getTag();
		DuanziBean db = list.get(position);
		Picasso.with(context).load(Uri.parse(db.getTitlepic())).into(viewHolder.imageView);
		viewHolder.title.setText(db.getTitle());
		viewHolder.time.setText(db.getNewstime());
		viewHolder.pinglun.setText(db.getPlnum());
		viewHolder.playNumber.setText(db.getOnclick()+"次");
		return convertView;
	}
	
	class ViewHolder{
		ImageView imageView;
		TextView title,time,pinglun,playNumber;
	}

}
