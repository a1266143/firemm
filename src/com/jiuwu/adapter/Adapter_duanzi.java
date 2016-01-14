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
	private List<DuanziBean> list;
	

	public Adapter_duanzi(Context context,List<DuanziBean> list) {
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
		if(convertView == null){
			viewHolder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.listitem_duanzi, null);
			viewHolder.time = (TextView) convertView.findViewById(R.id.listitem_duanzi_time);
			viewHolder.content = (TextView) convertView.findViewById(R.id.listitem_duanzi_content);
			viewHolder.dianzan = (Button) convertView.findViewById(R.id.listitem_duanzi_dz);
			viewHolder.onclick = (TextView) convertView.findViewById(R.id.listitem_duanzi_timeLiulan);
			//踩
			viewHolder.diggdown = (Button) convertView.findViewById(R.id.listitem_duanzi_cai);
			//收藏按钮
			//viewHolder.shoucang = (Button) convertView.findViewById(R.id.listitem_duanzi_sc);
			viewHolder.fenxiang = (Button) convertView.findViewById(R.id.listitem_duanzi_fx);
			viewHolder.pinglun = (Button) convertView.findViewById(R.id.listitem_duanzi_pl);
			convertView.setTag(viewHolder);
		}
		viewHolder = (ViewHolder) convertView.getTag();
		DuanziBean db = list.get(position);
		//设置发布时间
		viewHolder.time.setText(db.getNewstime());
		viewHolder.content.setText(db.getNewstext());	
		viewHolder.pinglun.setText(db.getPlnum());
		//点赞
		viewHolder.dianzan.setText(db.getDiggtop());
		//点击次数
		viewHolder.onclick.setText(db.getOnclick()+"次");
		//踩
		viewHolder.diggdown.setText(db.getDiggdown());
		return convertView;
	}
	
	class ViewHolder{
		TextView time,content,onclick;
		Button dianzan,shoucang,fenxiang,pinglun,diggdown;
	}
}
