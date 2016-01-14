package com.jiuwu.adapter;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.nhfls.R;
import com.jiuwu.bean.DuanziBean;
import com.jiuwu.nhfls.ImageActivity;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
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
			holder.gif = (ImageView) arg1.findViewById(R.id.listitem_duanzi_gif);
			holder.time = (TextView) arg1.findViewById(R.id.listitem_duanzi_time);
			holder.title = (TextView) arg1.findViewById(R.id.listitem_duanzi_content);
			holder.diggtop = (Button) arg1.findViewById(R.id.listitem_duanzi_dz);
			holder.plnum = (Button) arg1.findViewById(R.id.listitem_duanzi_pl);
			holder.diggdown = (Button) arg1.findViewById(R.id.listitem_duanzi_cai);
			holder.onclick = (TextView) arg1.findViewById(R.id.listitem_duanzi_timeLiulan);
			arg1.setTag(holder);
		}
		holder = (ViewHolder) arg1.getTag();
		holder.image.setVisibility(View.VISIBLE);
		DuanziBean db = list.get(arg0);
		//如果是动态图片，将gif图标显示出来
		/*if(db.getNewstext().endsWith(".gif")){
			holder.gif.setVisibility(View.VISIBLE);
		}*/
		//从网络获取图片
		Glide.with(fragment).load(db.getNewstext()).error(R.drawable.refresh).crossFade().diskCacheStrategy(DiskCacheStrategy.NONE).fitCenter().into(holder.image);
		//Picasso.with(fragment.getActivity()).load(Uri.parse(db.getNewstext())).error(R.drawable.refresh).into(holder.image);
		holder.image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(fragment.getActivity(),ImageActivity.class);
				intent.putExtra("url", list.get(arg0).getNewstext());
				fragment.getActivity().startActivity(intent);
				
			}
		});
		holder.time.setText(db.getNewstime());
		holder.title.setText(db.getTitle());
		holder.plnum.setText(db.getPlnum());
		holder.diggtop.setText(db.getDiggtop());
		holder.diggdown.setText(db.getDiggdown());
		holder.onclick.setText(db.getOnclick()+"次");
		imageList.add(holder.image);
		return arg1;
	}
	
	public ArrayList<ImageView> getImageList(){
		return (ArrayList<ImageView>) imageList;
	}
	
	class ViewHolder{
		ImageView image,gif;
		TextView time,title,onclick;
		Button plnum,diggtop,diggdown;
	}

}
