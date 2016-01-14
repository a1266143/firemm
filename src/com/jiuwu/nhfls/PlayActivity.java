package com.jiuwu.nhfls;

import com.baseproject.utils.Logger;
import com.example.nhfls.R;
import com.youku.player.base.YoukuBasePlayerManager;
import com.youku.player.base.YoukuPlayer;
import com.youku.player.base.YoukuPlayerView;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.Configuration;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class PlayActivity extends Activity {

	private YoukuPlayerView playerView;
	private YoukuBasePlayerManager manager;
	private YoukuPlayer player;
	private String vid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		vid = getIntent().getStringExtra("vid");
		playerView = (YoukuPlayerView) findViewById(R.id.full_holder);
		manager = new YoukuBasePlayerManager(this) {

			@Override
			public void setPadHorizontalLayout() {

			}

			@Override
			public void onSmallscreenListener() {

			}

			@Override
			public void onInitializationSuccess(YoukuPlayer player) {
				// 初始化成功后需要添加该行代码
				addPlugins();

				// 实例化YoukuPlayer实例
				PlayActivity.this.player = player;

				// 通过ID播放视频
				if(vid!=null||!vid.equals(""))
					PlayActivity.this.player.playVideo(vid);
			}

			@Override
			public void onFullscreenListener() {

			}
		};
		manager.onCreate();
		// 控制竖屏和全屏时候的布局参数。这两句必填。
		playerView
				.setSmallScreenLayoutParams(new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.MATCH_PARENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT));
		playerView
				.setFullScreenLayoutParams(new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.MATCH_PARENT,
						RelativeLayout.LayoutParams.MATCH_PARENT));
		// 初始化播放器相关数据
		playerView.initialize(manager);
	}
	
	
	@Override
	public void onBackPressed() { // android系统调用
		super.onBackPressed();
		manager.onBackPressed();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		manager.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		manager.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean managerKeyDown = manager.onKeyDown(keyCode, event);
		if (manager.shouldCallSuperKeyDown()) {
			return super.onKeyDown(keyCode, event);
		} else {
			return managerKeyDown;
		}

	}

	@Override
	public void onLowMemory() { // android系统调用
		super.onLowMemory();
		manager.onLowMemory();
	}

	@Override
	protected void onPause() {
		super.onPause();
		manager.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		manager.onResume();
	}

	@Override
	public boolean onSearchRequested() { // android系统调用
		return manager.onSearchRequested();
	}

	@Override
	protected void onStart() {
		super.onStart();
		manager.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
		manager.onStop();
	}

}
