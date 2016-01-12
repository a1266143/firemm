package com.jiuwu.adapter;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.example.nhfls.R;
import com.jiuwu.bean.DuanziBean;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.app.Fragment;
import android.app.Service;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapter_qutu extends BaseAdapter {

	private Fragment fragment;
	private List<DuanziBean> list;
	private ViewHolder holder;
	private List<ImageView> imageList;
	

	public Adapter_qutu(Fragment fragment, List<DuanziBean> list) {
		super();
		this.fragment = fragment;
		this.list = list;
		imageList = new ArrayList<ImageView>();
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
	public View getView(final int arg0, View arg1, ViewGroup arg2) {
		if(arg1==null){
			holder = new ViewHolder();
			LayoutInflater inflater = fragment.getActivity().getLayoutInflater();
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
		Glide.with(fragment).load(db.getNewstext()).error(R.drawable.refresh).dontAnimate().skipMemoryCache(false).override(230, 230).fitCenter().into(holder.image);
		//Picasso.with(fragment.getActivity()).load(Uri.parse(db.getNewstext())).error(R.drawable.refresh).resize(200, 200).into(holder.image);
		Log.e("url", db.getNewstext());
		holder.time.setText(db.getNewstime());
		holder.smalltext.setText(db.getTitle());
		holder.plnum.setText(db.getPlnum());
		holder.diggtop.setText(db.getDiggtop());
		imageList.add(holder.image);
		return arg1;
	}
	
	public ArrayList<ImageView> getImageList(){
		return (ArrayList<ImageView>) imageList;
	}
	
	class ViewHolder{
		ImageView image;
		TextView time,smalltext;
		Button plnum,diggtop;
	}

}
