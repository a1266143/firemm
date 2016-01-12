package com.jiuwu.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.jiuwu.bean.DuanziBean;
import com.jiuwu.nhfls.MainActivity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.util.Log;


public class NetUtils {
	
	 /**
	  * 解析分类的url
	  */
	 @SuppressLint("DefaultLocale")
	 /**,String haspic*/
	public static String getUrl(String classname,String haspic,String page,String pagesize,String time,String type){
		 //约定好的secret
		 String secret = "123$%^+KO.SLmomo!99nn%jn";
		 //先将sign字符串拼接起来,最后还要加上约定好的secret
		 String signStr = "classname="+classname+"haspic="+haspic+"page="+page+"pagesize="+pagesize+"time="+time+"type="+type+secret;
		 //将sign字符串进行md5编码,并且转换成小写
		 String sign = Utils.getMD5String(signStr).toLowerCase();
		 //将真正的url组合好
		 String newUrl = StaticCode.URL_CLASS+"&classname="+classname+"&haspic="+haspic+"&page="+page+"&pagesize="+pagesize+"&time="+time+"&type="+type+"&sign="+sign;
		 return newUrl;
	 }
	 
	 //获取段子ArrayList
	 public static void NetForDuanZi(final Handler handler,final int page){
		 
		 new Thread(new Runnable() {
			
			@Override
			public void run() {
				//1.解析url
				String url = getUrl(StaticCode.LENGXIAOHUA,""+0,""+page, ""+20, ""+System.currentTimeMillis(), ""+1);
				//连接网络获取json数据
				String json = null;
				if((json = getJson(handler, url))!=null){
					//解析并发送消息给相应的handler
					Utils.getInstance().anaDuanziArr(handler, json);
				}
			}
		}).start();
		 
	 }
	 
	 //获取图片ArrayList
	 public static void NetForQuTu(final Handler handler,final int page){
		 new Thread(new Runnable() {
			
			@Override
			public void run() {
				//解析url
				String url = getUrl(StaticCode.GIF, ""+1, ""+page, ""+15, ""+System.currentTimeMillis(), ""+1);
				Log.e("url", url);
				String json = null;
				if((json = getJson(handler, url))!=null){
					//解析并发送消息给相应的handler
					Utils.getInstance().anaQuTuArr(handler, json);
				}
			}
		}).start();
	 }
	 
	
	
	 
	 /**
	  * 获取网络数据（json）
	  * @param handler
	  * @param url
	  * @return 返回json数据
	  */
	public static String getJson(Handler handler,String url){
				String json = null;
				HttpGet get = null;
				HttpClient client = new DefaultHttpClient();
				get = new HttpGet(url);
				try {
					HttpResponse response = client.execute(get);
					HttpEntity entity = response.getEntity();
					if (entity != null) {
						BufferedReader br = new BufferedReader(new InputStreamReader(
								entity.getContent()));
						StringBuffer sb = new StringBuffer();
						String line = null;
						while ((line = br.readLine()) != null) {
							sb.append(line+"\n");
						}
						json = sb.toString();
						br.close();
					}
				} catch (ClientProtocolException e) {
					return null;
				} catch (IOException e) {
					handler.sendEmptyMessage(StaticCode.MISTAKE_NET);
					return null;
				}
				return json;
				
	}
}
