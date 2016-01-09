package com.jiuwu.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.SuppressLint;
import android.util.Log;


public class NetUtils {
	
	 /**
	  * 解析分类的url
	  */
	 @SuppressLint("DefaultLocale")
	public static String getUrl(String classname,String haspic,String page,String pagesize,String time,String type){
		 //约定好的secret
		 String secret = "123$%^+KO.SLmomo!99nn%jn";
		 //先将sign字符串拼接起来,最后还要加上约定好的secret
		 String signStr = "classname="+classname+"haspic="+haspic+"page="+page+"pagesize="+pagesize+"time="+time+"type="+type+secret;
		 //将sign字符串进行md5编码,并且转换成小写
		 String sign = Utils.getMD5String(signStr).toLowerCase();
		 Log.e("加密", sign);
		 //将真正的url组合好
		 String newUrl = StaticCode.URL_CLASS+"&classname="+classname+"&haspic="+haspic+"&page="+page+"&pagesize="+pagesize+"&time="+time+"&type="+type+"&sign="+sign;
		 return newUrl;
	 }
	
	
	public static String getJson(String url){
			
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
							sb.append(line);
						}
						json = sb.toString();
					}
				} catch (ClientProtocolException e) {
					e.printStackTrace();
					return null;
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
				return json;
	}
}
