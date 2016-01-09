package com.jiuwu.utils;

import java.security.MessageDigest;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

public class Utils {
	private static Utils utils = new Utils();
	private Utils(){	}
	public static Utils getInstance(){
		return utils;
	}
	
	/**
	 * MD5加密算法
	 * @param s传入字符串
	 * @return 返回加密后的MD5值
	 */
	 public final static String getMD5String(String s) {  
	        char hexDigits[] = { '0', '1', '2', '3', '4',  
	                '5', '6', '7', '8', '9',  
	                'A', 'B', 'C', 'D', 'E', 'F' };  
	        try {  
	            byte[] btInput = s.getBytes();  
	            //获得MD5摘要算法的 MessageDigest 对象  
	            MessageDigest mdInst = MessageDigest.getInstance("MD5");  
	            //使用指定的字节更新摘要  
	            mdInst.update(btInput);  
	            //获得密文  
	            byte[] md = mdInst.digest();  
	            //把密文转换成十六进制的字符串形式  
	            int j = md.length;  
	            char str[] = new char[j * 2];  
	            int k = 0;  
	            for (int i = 0; i < j; i++) {  
	                byte byte0 = md[i];  
	                str[k++] = hexDigits[byte0 >>> 4 & 0xf];  
	                str[k++] = hexDigits[byte0 & 0xf];  
	            }  
	            return new String(str);  
	        }  
	        catch (Exception e) {  
	            e.printStackTrace();  
	            return null;  
	        }  
	    }
	 
	
	
	/**
	 * 检查Service是否在运行
	 * @return 运行中返回true，未运行返回false
	 */
	public boolean isServiceRunning(Context context,String serviceAction) {
	    ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	        if (serviceAction.equals(service.service.getClassName())) {
	            return true;
	        }
	    }
	    return false;
	}
	/**
	 * 连接网络获得json文本String并且返回
	 * @param url 网址
	 * @param handler 主线程的handler
	 * @return json String
	 */
	public String getJson(String url,Handler handler){
		
		return null;
	}
	
	/**
	 * 获取屏幕宽度
	 * @return 屏幕宽度
	 */
	public int getWindowWidth(Context context){
		DisplayMetrics metric = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
	}
}
