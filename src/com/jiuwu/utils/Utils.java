package com.jiuwu.utils;

import java.security.MessageDigest;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jiuwu.bean.DuanziBean;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
	 
	 //解析段子bean,并且发送arraylist给handler
	 public void anaDuanziArr(Handler handler,String json){
		 try {
			 ArrayList<DuanziBean> arr = new ArrayList<DuanziBean>();
			JSONObject jo = new JSONObject(json);
			JSONObject data = jo.getJSONObject("data");
			JSONArray list = data.getJSONArray("list");
			for(int i=0;i<list.length();i++){
				DuanziBean db = new DuanziBean();
				JSONObject j = list.getJSONObject(i);
				db.setId(j.getString("id"));
				db.setNewstime(j.getString("newstime3"));
				db.setNewstext(j.getString("newstext"));
				db.setPlnum(j.getString("plnum"));
				db.setTitle(j.getString("title"));
				db.setDiggtop(j.getString("diggtop"));
				arr.add(db);
			}
			//发送arrlist给handler
			sendMessage(handler, arr);
		} catch (JSONException e) {
			e.printStackTrace();
			//发送给主界面通知json解析错误并且返回空
			handler.sendEmptyMessage(StaticCode.MISTAKE_JSON);
		}
	 }
	 
	 //解析趣图bean，并发送消息给handler
	 public void anaQuTuArr(Handler handler,String json){
		 try {
			 ArrayList<DuanziBean> arr = new ArrayList<DuanziBean>();
			JSONObject jo = new JSONObject(json);
			JSONObject data = jo.getJSONObject("data");
			JSONArray list = data.getJSONArray("list");
			for(int i=0;i<list.length();i++){
				JSONObject j = list.getJSONObject(i);
				DuanziBean db = new DuanziBean();
				db.setId(j.getString("id"));
				db.setDiggtop(j.getString("diggtop"));
				db.setDiggdown(j.getString("diggdown"));
				db.setNewstext(j.getString("newstext"));
				db.setNewstime(j.getString("newstime3"));
				db.setPlnum(j.getString("plnum"));
				db.setTitle(j.getString("title"));
				db.setTitlepic(j.getString("titlepic"));
				db.setSmalltext(j.getString("smalltext"));
				arr.add(db);
			}
			sendMessage(handler, arr);
		} catch (JSONException e) {
			handler.sendEmptyMessage(StaticCode.MISTAKE_JSON);
		}
	 }
	 
	
	 /**
	  * 通过handler以及ArrayList，发送message给handler
	  * @param handler
	  * @param arr
	  */
	 public <T> void sendMessage(Handler handler,ArrayList<T> arr){
		 	ArrayList<T> arrr = arr;
		 	Message msg = new Message();
			Bundle b = new Bundle();
			b.putSerializable("ArrayList", arrr);
			msg.setData(b);
			handler.sendMessage(msg);
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
