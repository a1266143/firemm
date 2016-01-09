package com.jiuwu.service;

import com.jiuwu.nhfls.MainActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

public class MainService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("Service", "Service创建了（onCreate）");
		//获取手机闹钟服务管理器,每五秒启动一次服务
		/*AlarmManager am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(MainActivity.SERVICE_ACTION);
		PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);
		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 5000, pendingIntent);*/
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("Service", "Service正在运行（onStartCommand）");
		//Toast.makeText(this, "Service正在运行", Toast.LENGTH_SHORT).show();
		return super.onStartCommand(intent, flags, Service.START_NOT_STICKY);
	}

	//服务被销毁，立即重新启动服务
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d("Service", "Service被销毁了（onDestroy）");
		Intent intent = new Intent(MainActivity.SERVICE_ACTION);
		startService(intent);
	}

	
}
