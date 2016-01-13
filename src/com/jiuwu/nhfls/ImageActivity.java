package com.jiuwu.nhfls;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.nhfls.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class ImageActivity extends Activity {

	private ImageView iv;
	private ProgressBar pb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
		iv = (ImageView) findViewById(R.id.activity_image_iv);
		pb = (ProgressBar) findViewById(R.id.activity_image_pb);
		//进行图片的下载
		
		Glide.with(this).load(getIntent().getStringExtra("url")).error(R.drawable.refresh).crossFade().diskCacheStrategy(DiskCacheStrategy.NONE).listener(new RequestListener<String, GlideDrawable>() {

			@Override
			public boolean onException(Exception arg0, String arg1,
					Target<GlideDrawable> arg2, boolean arg3) {
				Toast.makeText(ImageActivity.this, "图片异常:"+arg0.getMessage(), Toast.LENGTH_SHORT).show();
				pb.setVisibility(View.GONE);
				return false;
			}

			@Override
			public boolean onResourceReady(GlideDrawable arg0, String arg1,
					Target<GlideDrawable> arg2, boolean arg3, boolean arg4) {
				pb.setVisibility(View.GONE);
				return false;
			}
		}).override(50, 50).fitCenter().into(iv);
		findViewById(R.id.lin).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

}
