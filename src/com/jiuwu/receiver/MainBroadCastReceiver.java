package com.jiuwu.receiver;

import com.jiuwu.nhfls.MainActivity;
import com.jiuwu.utils.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MainBroadCastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.e("Receiver", intent.getAction());
		//判断service是否启动，没启动便启动
		if(!Utils.getInstance().isServiceRunning(context, MainActivity.SERVICE_ACTION)){
			Intent i = new Intent(MainActivity.SERVICE_ACTION);
			context.startService(i);
		}
	}

}
