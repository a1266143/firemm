package com.jiuwu.nhfls;

import java.util.ArrayList;
import java.util.List;

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
import com.jiuwu.fragment.Fragment_zuixin;
import com.jiuwu.utils.StaticCode;
import com.jiuwu.utils.Utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("InlinedApi")
public class MainActivity extends FragmentActivity {
	private Fragment_zuixin fragment_zx;
	private Fragment_duanzi fragment_dz;
	private Fragment_qutu fragment_qt;
	private Fragment_shipin fragment_sp;
	private Fragment_meinv fragment_mv;
	private Fragment_fenlei_qutu fragment_fl_qutu;
	private Fragment_fenlei_duanzi fragment_fl_duanzi;
	private Fragment_fenlei_shipin fragment_fl_shipin;
	private Fragment_fenlei_meinv fragment_fl_meinv;
	private Button titlebar_duanzi, titlebar_qutu, titlebar_shipin,
			titlebar_meinv, titlebar_fenlei, btn_zx, btn_dz, btn_sp, btn_qt,
			btn_mv;
	private View menuBtn;
	private ImageView activity_main_tabiv;
	private ViewPager viewpager;
	private List<Fragment> list;
	private int currentSelectItem;

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

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// 网络错误
			if (msg.what == StaticCode.MISTAKE_NET) {
				Toast.makeText(MainActivity.this, "网络错误", Toast.LENGTH_SHORT)
						.show();
			} else if (msg.what == StaticCode.MISTAKE_JSON) {
				Toast.makeText(MainActivity.this, "Json解析错误",
						Toast.LENGTH_SHORT).show();
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
		// showTableScreenAd();
		// *****************显示图文广告***********************
		// showImageTextAd();
		// ****************************************************
		// 初始化
		init();
		// 设置侧滑菜单相关属性
		// setDrawerLayout();
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
		titlebar_duanzi = (Button) findViewById(R.id.titlebar_duanzi);
		titlebar_qutu = (Button) findViewById(R.id.titlebar_qutu);
		titlebar_shipin = (Button) findViewById(R.id.titlebar_shipin);
		titlebar_meinv = (Button) findViewById(R.id.titlebar_meinv);
		titlebar_fenlei = (Button) findViewById(R.id.titlebar_fenlei);
		btn_zx = (Button) findViewById(R.id.activity_main_btn_zx);
		btn_dz = (Button) findViewById(R.id.activity_main_btn_dz);
		btn_sp = (Button) findViewById(R.id.activity_main_btn_sp);
		btn_qt = (Button) findViewById(R.id.activity_main_btn_qt);
		btn_mv = (Button) findViewById(R.id.activity_main_btn_mv);
		activity_main_tabiv = (ImageView) findViewById(R.id.activity_main_tabiv);
		// 设置导航图片的宽度为屏幕宽度的1/5
		setImageTabWidth();
		fragment_zx = new Fragment_zuixin();
		fragment_dz = new Fragment_duanzi();
		fragment_sp = new Fragment_shipin();
		fragment_qt = new Fragment_qutu();
		fragment_mv = new Fragment_meinv();
		list = new ArrayList<Fragment>();
		list.add(fragment_zx);
		list.add(fragment_dz);
		list.add(fragment_qt);
		list.add(fragment_sp);
		list.add(fragment_mv);
		viewpager = (ViewPager) findViewById(R.id.activity_main_viewpager);
		MyPagerAdapter mpAdapter = new MyPagerAdapter(
				getSupportFragmentManager(), list);
		viewpager.setAdapter(mpAdapter);
		MyPageChangeListener mpclistener = new MyPageChangeListener();
		viewpager.setOnPageChangeListener(mpclistener);
		// 设置viewpager缓存页面个数
		viewpager.setOffscreenPageLimit(4);
		/*
		 * getFragmentManager().beginTransaction() .replace(R.id.frameLayout,
		 * fragment_dz).commit();
		 */

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
		titlebar_meinv.setOnClickListener(listener);
		menuBtn.setOnClickListener(listener);
		titlebar_fenlei.setOnClickListener(listener);

		btn_zx.setOnClickListener(listener);
		btn_dz.setOnClickListener(listener);
		btn_qt.setOnClickListener(listener);
		btn_sp.setOnClickListener(listener);
		btn_mv.setOnClickListener(listener);
	}

	/**
	 * 设置tabimageView的宽度
	 */
	public void setImageTabWidth() {
		LinearLayout.LayoutParams params = (LayoutParams) activity_main_tabiv
				.getLayoutParams();
		params.width = Utils.getInstance().getWindowWidth(this) / 5;
		activity_main_tabiv.setLayoutParams(params);
	}

	/**
	 * 设置侧滑菜单
	 */
	/*
	 * public void setDrawerLayout() { // 设置侧滑菜单的fragment以及阴影 drawerLayout =
	 * (DrawerLayout) findViewById(R.id.drawerLayout);
	 * drawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
	 * GravityCompat.START); MenuFragment mf = new MenuFragment();
	 * getFragmentManager().beginTransaction().add(R.id.menuFrameLayout, mf)
	 * .commit(); }
	 */

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
			titlebar_meinv
					.setBackgroundResource(R.drawable.rightbtn_press_false);
			titlebar_duanzi.setTextColor(getResources().getColor(
					R.color.text_selected_true));
			titlebar_qutu.setTextColor(getResources().getColor(
					R.color.text_selected_false));
			titlebar_shipin.setTextColor(getResources().getColor(
					R.color.text_selected_false));
			titlebar_meinv.setTextColor(getResources().getColor(
					R.color.text_selected_false));
			/*
			 * getFragmentManager().beginTransaction()
			 * .replace(R.id.frameLayout, fragment_dz).commit();
			 */
			break;
		// 趣图按钮
		case R.id.titlebar_qutu:
			// 将趣图按钮的颜色设置为选中状态
			titlebar_duanzi
					.setBackgroundResource(R.drawable.leftbtn_press_false);
			titlebar_qutu.setBackgroundResource(R.drawable.topbtnpress_true);
			titlebar_shipin.setBackgroundResource(R.drawable.topbuttonback);
			titlebar_meinv
					.setBackgroundResource(R.drawable.rightbtn_press_false);
			titlebar_duanzi.setTextColor(getResources().getColor(
					R.color.text_selected_false));
			titlebar_qutu.setTextColor(getResources().getColor(
					R.color.text_selected_true));
			titlebar_shipin.setTextColor(getResources().getColor(
					R.color.text_selected_false));
			titlebar_meinv.setTextColor(getResources().getColor(
					R.color.text_selected_false));
			/*
			 * getFragmentManager().beginTransaction()
			 * .replace(R.id.frameLayout, fragment_qt).commit();
			 */
			break;
		// 视频按钮
		case R.id.titlebar_shipin:
			titlebar_duanzi
					.setBackgroundResource(R.drawable.leftbtn_press_false);
			titlebar_qutu.setBackgroundResource(R.drawable.topbuttonback);
			titlebar_shipin.setBackgroundResource(R.drawable.topbtnpress_true);
			titlebar_meinv
					.setBackgroundResource(R.drawable.rightbtn_press_false);
			titlebar_duanzi.setTextColor(getResources().getColor(
					R.color.text_selected_false));
			titlebar_qutu.setTextColor(getResources().getColor(
					R.color.text_selected_false));
			titlebar_shipin.setTextColor(getResources().getColor(
					R.color.text_selected_true));
			titlebar_meinv.setTextColor(getResources().getColor(
					R.color.text_selected_false));
			/*
			 * getFragmentManager().beginTransaction()
			 * .replace(R.id.frameLayout, fragment_sp).commit();
			 */
			break;
		// 专辑按钮
		case R.id.titlebar_meinv:
			titlebar_duanzi
					.setBackgroundResource(R.drawable.leftbtn_press_false);
			titlebar_qutu.setBackgroundResource(R.drawable.topbuttonback);
			titlebar_shipin.setBackgroundResource(R.drawable.topbuttonback);
			titlebar_meinv
					.setBackgroundResource(R.drawable.rightbtn_press_true);
			titlebar_duanzi.setTextColor(getResources().getColor(
					R.color.text_selected_false));
			titlebar_qutu.setTextColor(getResources().getColor(
					R.color.text_selected_false));
			titlebar_shipin.setTextColor(getResources().getColor(
					R.color.text_selected_false));
			titlebar_meinv.setTextColor(getResources().getColor(
					R.color.text_selected_true));
			/*
			 * getFragmentManager().beginTransaction()
			 * .replace(R.id.frameLayout, fragment_zj).commit();
			 */
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
			case R.id.titlebar_meinv:
				currentMenuValue = ZHUANJI;
				changeColor(R.id.titlebar_meinv);
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
			case R.id.activity_main_btn_zx:
				viewpager.setCurrentItem(0, true);
				changeTitleColor(0);
				break;
			case R.id.activity_main_btn_dz:
				viewpager.setCurrentItem(1, true);
				changeTitleColor(1);
				break;
			case R.id.activity_main_btn_qt:
				viewpager.setCurrentItem(2, true);
				changeTitleColor(2);
				break;
			case R.id.activity_main_btn_sp:
				viewpager.setCurrentItem(3, true);
				changeTitleColor(3);
				break;
			case R.id.activity_main_btn_mv:
				viewpager.setCurrentItem(4, true);
				changeTitleColor(4);
				break;
			}

		}
	}

	// 点击第一级分类，根据当前点击的分类关闭其他一级分类的二级分类菜单
	public void shutDownFenLei() {
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		if (fragment_fl_duanzi != null)
			transaction.remove(fragment_fl_duanzi);
		if (fragment_fl_qutu != null)
			transaction.remove(fragment_fl_qutu);
		if (fragment_fl_shipin != null)
			transaction.remove(fragment_fl_shipin);
		if (fragment_fl_meinv != null)
			transaction.remove(fragment_fl_meinv);
		transaction.setCustomAnimations(R.anim.enter, R.anim.out);
		transaction.commit();
		isShown = false;
	}

	// 显示分类下拉菜单
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
				transaction
						.replace(R.id.frameLayout_fenlei, fragment_fl_duanzi);
				break;
			case QUTU:
				fragment_fl_qutu = new Fragment_fenlei_qutu();
				transaction.replace(R.id.frameLayout_fenlei, fragment_fl_qutu);
				break;
			case SHIPIN:
				fragment_fl_shipin = new Fragment_fenlei_shipin();
				transaction
						.replace(R.id.frameLayout_fenlei, fragment_fl_shipin);
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

	/**
	 * 自定义PagerAdapter
	 */
	class MyPagerAdapter extends FragmentPagerAdapter {
		public static final int ZUIXIN = 0, DUANZI = 1, QUTU = 2, SHIPIN = 3,
				MEINV = 4;
		private List<Fragment> list;

		public MyPagerAdapter(android.support.v4.app.FragmentManager fm,
				List<Fragment> list) {
			super(fm);
			this.list = list;
		}

		@Override
		public Fragment getItem(int arg0) {
			return list.get(arg0);
		}

		@Override
		public int getCount() {
			return list.size();
		}

	}

	/**
	 * ViewPager页面滑动改变监听器
	 */
	class MyPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int position, float offset, int arg2) {
			int screenWidth = Utils.getInstance().getWindowWidth(
					MainActivity.this);
			int imageWidth = screenWidth / 5;
			// 获得ImageView设置margin参数的LayoutParam
			LinearLayout.LayoutParams lp = (LayoutParams) activity_main_tabiv
					.getLayoutParams();
			if (currentSelectItem == 0 && position == 0) {// 0->1
				lp.leftMargin = (int) (imageWidth * 1.0 * offset)
						+ currentSelectItem * imageWidth;
			} else if (currentSelectItem == 1 && position == 0) {// 1->0
				lp.leftMargin = (int) (-imageWidth * 1.0 * (1 - offset))
						+ currentSelectItem * imageWidth;
			} else if (currentSelectItem == 1 && position == 1) {// 1->2
				lp.leftMargin = lp.leftMargin = (int) (imageWidth * 1.0 * offset)
						+ currentSelectItem * imageWidth;
			} else if (currentSelectItem == 2 && position == 1) {// 2->1
				lp.leftMargin = (int) (-imageWidth * 1.0 * (1 - offset))
						+ currentSelectItem * imageWidth;
			} else if (currentSelectItem == 2 && position == 2) {// 2->3
				lp.leftMargin = lp.leftMargin = (int) (imageWidth * 1.0 * offset)
						+ currentSelectItem * imageWidth;
			} else if (currentSelectItem == 3 && position == 2) {// 3->2
				lp.leftMargin = (int) (-imageWidth * 1.0 * (1 - offset))
						+ currentSelectItem * imageWidth;
			} else if (currentSelectItem == 3 && position == 3) {// 3->4
				lp.leftMargin = lp.leftMargin = (int) (imageWidth * 1.0 * offset)
						+ currentSelectItem * imageWidth;
			} else if (currentSelectItem == 4 && position == 3) {// 4->3
				lp.leftMargin = (int) (-imageWidth * 1.0 * (1 - offset))
						+ currentSelectItem * imageWidth;
			}
			activity_main_tabiv.setLayoutParams(lp);
		}

		@Override
		public void onPageSelected(int position) {
			currentSelectItem = position;
			changeTitleColor(position);
		}

	}

	public void changeTitleColor(int value) {
		switch (value) {
		case MyPagerAdapter.ZUIXIN:
			btn_zx.setTextColor(Color.parseColor("#b60a0a"));
			btn_dz.setTextColor(Color.parseColor("#000000"));
			btn_sp.setTextColor(Color.parseColor("#000000"));
			btn_qt.setTextColor(Color.parseColor("#000000"));
			btn_mv.setTextColor(Color.parseColor("#000000"));
			break;
		case MyPagerAdapter.DUANZI:
			btn_zx.setTextColor(Color.parseColor("#000000"));
			btn_dz.setTextColor(Color.parseColor("#b60a0a"));
			btn_sp.setTextColor(Color.parseColor("#000000"));
			btn_qt.setTextColor(Color.parseColor("#000000"));
			btn_mv.setTextColor(Color.parseColor("#000000"));
			break;
		case MyPagerAdapter.QUTU:
			btn_zx.setTextColor(Color.parseColor("#000000"));
			btn_dz.setTextColor(Color.parseColor("#000000"));
			btn_sp.setTextColor(Color.parseColor("#000000"));
			btn_qt.setTextColor(Color.parseColor("#b60a0a"));
			btn_mv.setTextColor(Color.parseColor("#000000"));
			break;
		case MyPagerAdapter.SHIPIN:
			btn_zx.setTextColor(Color.parseColor("#000000"));
			btn_dz.setTextColor(Color.parseColor("#000000"));
			btn_sp.setTextColor(Color.parseColor("#b60a0a"));
			btn_qt.setTextColor(Color.parseColor("#000000"));
			btn_mv.setTextColor(Color.parseColor("#000000"));
			break;
		case MyPagerAdapter.MEINV:
			btn_zx.setTextColor(Color.parseColor("#000000"));
			btn_dz.setTextColor(Color.parseColor("#000000"));
			btn_sp.setTextColor(Color.parseColor("#000000"));
			btn_qt.setTextColor(Color.parseColor("#000000"));
			btn_mv.setTextColor(Color.parseColor("#b60a0a"));
			break;
		}
	}
}
