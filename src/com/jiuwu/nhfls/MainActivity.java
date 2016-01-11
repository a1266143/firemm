package com.jiuwu.nhfls;

import com.baidu.ops.appunion.sdk.AppUnionSDK;
import com.baidu.ops.appunion.sdk.InterstitialAdListener;
import com.baidu.ops.appunion.sdk.banner.BaiduBanner;
import com.baidu.ops.appunion.sdk.banner.BannerType;
import com.example.nhfls.R;
import com.jiuwu.fragment.Fragment_duanzi;
import com.jiuwu.fragment.Fragment_fenlei_duanzi;
import com.jiuwu.fragment.Fragment_fenlei_meinv;
import com.jiuwu.fragment.Fragment_fenlei_qutu;
import com.jiuwu.fragment.Fragment_fenlei_shipin;
import com.jiuwu.fragment.Fragment_qutu;
import com.jiuwu.fragment.Fragment_shipin;
import com.jiuwu.fragment.Fragment_meinv;
import com.jiuwu.fragment.MenuFragment;
import com.jiuwu.utils.NetUtils;
import com.jiuwu.utils.StaticCode;
import com.jiuwu.utils.Utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Toast;

@SuppressLint("InlinedApi")
public class MainActivity extends Activity {
	private Fragment_duanzi fragment_dz;
	private Fragment_qutu fragment_qt;
	private Fragment_shipin fragment_sp;
	private Fragment_meinv fragment_zj;
	private Fragment_fenlei_qutu fragment_fl_qutu;
	private Fragment_fenlei_duanzi fragment_fl_duanzi;
	private Fragment_fenlei_shipin fragment_fl_shipin;
	private Fragment_fenlei_meinv fragment_fl_meinv;
	private Button titlebar_duanzi, titlebar_qutu, titlebar_shipin,
			titlebar_zhuanji, titlebar_fenlei;
	private View menuBtn;

	
	private boolean isShown;

	// 侧滑菜单FrameLayout
	// private FrameLayout menuFrameLayout;
	// private LayoutParams lp;

	// 侧滑菜单
	private DrawerLayout drawerLayout;

	// 记录当前菜单值
	public static final int DUANZI = 0, QUTU = 1, SHIPIN = 2, ZHUANJI = 3;
	private int currentMenuValue;

	// Service的Action
	public final static String SERVICE_ACTION = "com.example.startService";
	// baidubanner
	private BaiduBanner mBaiduBanner_Image_Text;
	
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			//网络错误
			if(msg.what == StaticCode.MISTAKE_NET){
				Toast.makeText(MainActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
			}else if(msg.what == StaticCode.MISTAKE_JSON){
				Toast.makeText(MainActivity.this, "Json解析错误", Toast.LENGTH_SHORT).show();
			}
		}
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ****************百度百通接口初始化******************
		AppUnionSDK.getInstance(this).initSdk();
		setContentView(R.layout.activity_main);
		// *****************显示插屏广告***********************
		//showTableScreenAd();
		// *****************显示图文广告***********************
		//showImageTextAd();
		// ****************************************************
		// 初始化
		init();
		// 设置侧滑菜单相关属性
		setDrawerLayout();
		// 启动service
		if (!Utils.getInstance().isServiceRunning(this, SERVICE_ACTION)) {
			Intent intent = new Intent(SERVICE_ACTION);
			startService(intent);
		}
	}

	/**
	 * 初始化
	 */
	public void init() {
		fragment_dz = new Fragment_duanzi();
		fragment_qt = new Fragment_qutu();
		fragment_sp = new Fragment_shipin();
		fragment_zj = new Fragment_meinv();

		getFragmentManager().beginTransaction()
				.replace(R.id.frameLayout, fragment_dz).commit();
		titlebar_duanzi = (Button) findViewById(R.id.titlebar_duanzi);
		titlebar_qutu = (Button) findViewById(R.id.titlebar_qutu);
		titlebar_shipin = (Button) findViewById(R.id.titlebar_shipin);
		titlebar_zhuanji = (Button) findViewById(R.id.titlebar_zhuanji);
		titlebar_fenlei = (Button) findViewById(R.id.titlebar_fenlei);
		// 侧滑菜单设置
		/*
		 * menuFrameLayout = (FrameLayout) findViewById(R.id.menuFrameLayout);
		 * lp = (LayoutParams) menuFrameLayout.getLayoutParams(); lp.width =
		 * Utils.getInstance().getWindowWidth(this)/2; lp.height =
		 * lp.WRAP_CONTENT; menuFrameLayout.setLayoutParams(lp);
		 */

		menuBtn = findViewById(R.id.titlebar_menu);
		TitleButtonListener listener = new TitleButtonListener();
		titlebar_duanzi.setOnClickListener(listener);
		titlebar_qutu.setOnClickListener(listener);
		titlebar_shipin.setOnClickListener(listener);
		titlebar_zhuanji.setOnClickListener(listener);
		menuBtn.setOnClickListener(listener);
		titlebar_fenlei.setOnClickListener(listener);
	}

	/**
	 * 设置侧滑菜单
	 */
	public void setDrawerLayout() {
		// 设置侧滑菜单的fragment以及阴影
		drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
		drawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		MenuFragment mf = new MenuFragment();
		getFragmentManager().beginTransaction().add(R.id.menuFrameLayout, mf)
				.commit();
	}

	/**
	 * 点击某个按钮会改变颜色,并且改变字体颜色
	 * 
	 * @param id
	 *            按钮的id
	 */
	public void changeColor(int id) {
		switch (id) {
		// 段子按钮
		case R.id.titlebar_duanzi:
			// 将段子按钮的颜色设置为选中状态，设置其他三个按钮的颜色为透明边框
			titlebar_duanzi
					.setBackgroundResource(R.drawable.leftbtn_press_true);
			titlebar_qutu.setBackgroundResource(R.drawable.topbuttonback);
			titlebar_shipin.setBackgroundResource(R.drawable.topbuttonback);
			titlebar_zhuanji
					.setBackgroundResource(R.drawable.rightbtn_press_false);
			titlebar_duanzi.setTextColor(getResources().getColor(
					R.color.text_selected_true));
			titlebar_qutu.setTextColor(getResources().getColor(
					R.color.text_selected_false));
			titlebar_shipin.setTextColor(getResources().getColor(
					R.color.text_selected_false));
			titlebar_zhuanji.setTextColor(getResources().getColor(
					R.color.text_selected_false));
			getFragmentManager().beginTransaction()
					.replace(R.id.frameLayout, fragment_dz).commit();
			break;
		// 趣图按钮
		case R.id.titlebar_qutu:
			// 将趣图按钮的颜色设置为选中状态
			titlebar_duanzi
					.setBackgroundResource(R.drawable.leftbtn_press_false);
			titlebar_qutu.setBackgroundResource(R.drawable.topbtnpress_true);
			titlebar_shipin.setBackgroundResource(R.drawable.topbuttonback);
			titlebar_zhuanji
					.setBackgroundResource(R.drawable.rightbtn_press_false);
			titlebar_duanzi.setTextColor(getResources().getColor(
					R.color.text_selected_false));
			titlebar_qutu.setTextColor(getResources().getColor(
					R.color.text_selected_true));
			titlebar_shipin.setTextColor(getResources().getColor(
					R.color.text_selected_false));
			titlebar_zhuanji.setTextColor(getResources().getColor(
					R.color.text_selected_false));
			getFragmentManager().beginTransaction()
					.replace(R.id.frameLayout, fragment_qt).commit();
			break;
		// 视频按钮
		case R.id.titlebar_shipin:
			titlebar_duanzi
					.setBackgroundResource(R.drawable.leftbtn_press_false);
			titlebar_qutu.setBackgroundResource(R.drawable.topbuttonback);
			titlebar_shipin.setBackgroundResource(R.drawable.topbtnpress_true);
			titlebar_zhuanji
					.setBackgroundResource(R.drawable.rightbtn_press_false);
			titlebar_duanzi.setTextColor(getResources().getColor(
					R.color.text_selected_false));
			titlebar_qutu.setTextColor(getResources().getColor(
					R.color.text_selected_false));
			titlebar_shipin.setTextColor(getResources().getColor(
					R.color.text_selected_true));
			titlebar_zhuanji.setTextColor(getResources().getColor(
					R.color.text_selected_false));
			getFragmentManager().beginTransaction()
					.replace(R.id.frameLayout, fragment_sp).commit();
			break;
		// 专辑按钮
		case R.id.titlebar_zhuanji:
			titlebar_duanzi
					.setBackgroundResource(R.drawable.leftbtn_press_false);
			titlebar_qutu.setBackgroundResource(R.drawable.topbuttonback);
			titlebar_shipin.setBackgroundResource(R.drawable.topbuttonback);
			titlebar_zhuanji
					.setBackgroundResource(R.drawable.rightbtn_press_true);
			titlebar_duanzi.setTextColor(getResources().getColor(
					R.color.text_selected_false));
			titlebar_qutu.setTextColor(getResources().getColor(
					R.color.text_selected_false));
			titlebar_shipin.setTextColor(getResources().getColor(
					R.color.text_selected_false));
			titlebar_zhuanji.setTextColor(getResources().getColor(
					R.color.text_selected_true));
			getFragmentManager().beginTransaction()
					.replace(R.id.frameLayout, fragment_zj).commit();
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		// 点击菜单键
		case KeyEvent.KEYCODE_MENU:
			if (drawerLayout.isDrawerOpen(GravityCompat.START))
				drawerLayout.closeDrawer(GravityCompat.START);
			else
				drawerLayout.openDrawer(GravityCompat.START);
			break;

		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		// ***********销毁百通sdk*************
		AppUnionSDK.getInstance(this).quitSdk();
		super.onDestroy();
	}

	/**
	 * topbar的按钮事件监听类
	 * 
	 * @author xiaojunLi
	 * 
	 */
	class TitleButtonListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			// 段子按钮
			case R.id.titlebar_duanzi:
				currentMenuValue = DUANZI;
				changeColor(R.id.titlebar_duanzi);
				shutDownFenLei();
				break;
			// 趣图按钮
			case R.id.titlebar_qutu:
				currentMenuValue = QUTU;
				changeColor(R.id.titlebar_qutu);
				shutDownFenLei();
				break;
			// 视频按钮
			case R.id.titlebar_shipin:
				currentMenuValue = SHIPIN;
				changeColor(R.id.titlebar_shipin);
				shutDownFenLei();
				break;
			// 专辑按钮
			case R.id.titlebar_zhuanji:
				currentMenuValue = ZHUANJI;
				changeColor(R.id.titlebar_zhuanji);
				shutDownFenLei();
				break;
			// 菜单按钮
			case R.id.titlebar_menu:
				switch (currentMenuValue) {
				case DUANZI:
				case QUTU:
				case SHIPIN:
					PopupMenu pm = new PopupMenu(MainActivity.this, menuBtn);
					getMenuInflater().inflate(R.menu.main, pm.getMenu());
					PopupMenuListener listener = new PopupMenuListener();
					pm.setOnMenuItemClickListener(listener);
					pm.show();
					break;
				case ZHUANJI:
					PopupMenu p = new PopupMenu(MainActivity.this, menuBtn);
					getMenuInflater().inflate(R.menu.second, p.getMenu());
					PopupMenuListener listener1 = new PopupMenuListener();
					p.setOnMenuItemClickListener(listener1);
					p.show();
					break;
				}
				break;
			// 分类按钮
			case R.id.titlebar_fenlei:
				showFenLei();
				break;
			}
		}

	}

	
	//点击第一级分类，根据当前点击的分类关闭其他一级分类的二级分类菜单
	public void shutDownFenLei(){
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		if(fragment_fl_duanzi!=null)
			transaction.remove(fragment_fl_duanzi);
		if(fragment_fl_qutu!=null)
			transaction.remove(fragment_fl_qutu);
		if(fragment_fl_shipin!=null)
			transaction.remove(fragment_fl_shipin);
		if(fragment_fl_meinv!=null)
			transaction.remove(fragment_fl_meinv);
		transaction.setCustomAnimations(R.anim.enter, R.anim.out);
		transaction.commit();
		isShown = false;
	}
	
	//显示分类下拉菜单
	public void showFenLei() {
		FragmentManager manager = getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.setCustomAnimations(R.anim.enter, R.anim.out);
		if (isShown) {
			switch (currentMenuValue) {
			case DUANZI:
				if (fragment_fl_duanzi != null)
					transaction.remove(fragment_fl_duanzi);
				break;
			case QUTU:
				if (fragment_fl_qutu != null)
					transaction.remove(fragment_fl_qutu);
				break;
			case SHIPIN:
				if (fragment_fl_shipin != null)
					transaction.remove(fragment_fl_shipin);
				break;
			// 美女
			case ZHUANJI:
				if (fragment_fl_meinv != null)
					transaction.remove(fragment_fl_meinv);
				break;
			}
			isShown = false;
		} else {
			switch (currentMenuValue) {
			case DUANZI:
				fragment_fl_duanzi = new Fragment_fenlei_duanzi();
				transaction.replace(R.id.frameLayout_fenlei, fragment_fl_duanzi);
				break;
			case QUTU:
				fragment_fl_qutu = new Fragment_fenlei_qutu();
				transaction.replace(R.id.frameLayout_fenlei, fragment_fl_qutu);
				break;
			case SHIPIN:
				fragment_fl_shipin = new Fragment_fenlei_shipin();
				transaction.replace(R.id.frameLayout_fenlei, fragment_fl_shipin);
				break;
			// 美女
			case ZHUANJI:
				fragment_fl_meinv = new Fragment_fenlei_meinv();
				transaction.replace(R.id.frameLayout_fenlei, fragment_fl_meinv);
				break;
			}
			isShown = true;
		}
		transaction.commit();
	}

	class PopupMenuListener implements OnMenuItemClickListener {

		@Override
		public boolean onMenuItemClick(MenuItem item) {
			switch (item.getItemId()) {
			// 随机穿越按钮
			case R.id.random:
				Toast.makeText(MainActivity.this, "你点击了随机穿越按钮",
						Toast.LENGTH_SHORT).show();
				break;
			// 选择日期按钮
			case R.id.chooseDate:
				Toast.makeText(MainActivity.this, "你点击了选择日期按钮",
						Toast.LENGTH_SHORT).show();
				break;
			// 收藏按钮
			case R.id.shoucang:
				Toast.makeText(MainActivity.this, "你点击了收藏按钮",
						Toast.LENGTH_SHORT).show();
				break;
			// 设置按钮
			case R.id.set:
				Toast.makeText(MainActivity.this, "你点击了设置按钮",
						Toast.LENGTH_SHORT).show();
				break;
			// 公告牌按钮
			case R.id.gonggaopai:
				Toast.makeText(MainActivity.this, "你点击了公告牌按钮",
						Toast.LENGTH_SHORT).show();
				break;
			// 精彩应用推荐按钮
			case R.id.recommand:
				// 进入精彩应用推荐页面
				AppUnionSDK.getInstance(MainActivity.this).showAppList();
				break;
			}
			return true;
		}

	}

	/**
	 * 显示插屏广告方法
	 */
	public void showTableScreenAd() {
		AppUnionSDK.getInstance(this).loadInterstitialAd(this,
				new InterstitialAdListener() {

					@Override
					public void onAdReady() {
						Toast.makeText(MainActivity.this, "广告准备好了1",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onAdPresent() {

					}

					@Override
					public void onAdFailed(String arg0) {
						Toast.makeText(MainActivity.this, "广告准备失败1",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onAdDismissed() {
						Toast.makeText(MainActivity.this, "广告消失了1",
								Toast.LENGTH_SHORT).show();
					}
				}, false);

		// 如果插屏广告准备完毕，就直接显示广告
		if (AppUnionSDK.getInstance(this).isInterstitialAdReady()) {
			AppUnionSDK.getInstance(this).showInterstitialAd();
		}
		// 否则再次调用load函数一次
		else {
			AppUnionSDK.getInstance(this).loadInterstitialAd(this,
					new InterstitialAdListener() {

						@Override
						public void onAdReady() {
							Toast.makeText(MainActivity.this, "广告准备好了2",
									Toast.LENGTH_SHORT).show();
							AppUnionSDK.getInstance(MainActivity.this)
									.showInterstitialAd();
						}

						@Override
						public void onAdPresent() {

						}

						@Override
						public void onAdFailed(String arg0) {
							Toast.makeText(MainActivity.this, "广告失败了2",
									Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onAdDismissed() {
							Toast.makeText(MainActivity.this, "广告消失了2",
									Toast.LENGTH_SHORT).show();
						}
					}, false);
		}
	}

	/**
	 * 显示图文广告
	 */
	public void showImageTextAd() {
		if (mBaiduBanner_Image_Text == null
				|| mBaiduBanner_Image_Text.getVisibility() == View.GONE) {

			FrameLayout.LayoutParams lytp = new FrameLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			lytp.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
			mBaiduBanner_Image_Text = new BaiduBanner(this);
			mBaiduBanner_Image_Text.setBannerType(BannerType.IMAGE_TEXT);
			addContentView(mBaiduBanner_Image_Text, lytp);
		}
	}
}
