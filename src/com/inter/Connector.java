package com.inter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;





import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
//import android.content.Intent;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Connector extends Thread  {
	final private int Register = 1;
	final private int Login = 2;
	final private int CheckState = 3;
	final private int TransitData = 4;
	private String name;
	private String password; 
	private String result;
	private int state = 0;
	private double latitude;
	private double longitude;
	private String radius;
	private String apps;
	private int Type = 0;
	private int StatusOfLogin = 0;
	Handler handler ;
	public Connector(){

	}
	public Connector(int type,String name,String password,Handler handler){
		this.Type = type;
		this.name = name;
		this.password = password;
		this.handler = handler;
		
	}
	public Connector(int type,String name,int state){
		this.Type = type;
		this.name = name;
		this.state = state;
	}
	public Connector(int type,String username,double latitude ,double longitude,
			String radius,String apps,Handler H){
		this.Type = type;
		this.name = username;
		this.latitude = latitude;
		this.longitude = longitude;
		this.radius = radius;
		this.apps = apps;
		this.handler= H;
		
	}
	public void run(){
		Looper.prepare();
		switch(this.Type){
		case Register:
		connect_Reg(this.name,this.password);
			break;
		case Login:
		connect_Log(this.name,this.password);
			break;
		case CheckState:
		connect_Change(this.name,this.state);
			break;
		case TransitData:
		connect_Tran(name,latitude,longitude,radius,apps);
			break;
		default:
			break;
		}
		Looper.loop();
		
	}
//	注册新用户，已存在返回false，成功返回true
	public void connect_Reg(String username,String password){
		String url = "http://hackerday.sinaapp.com/index.php/user/userRegister";
		HttpPost httpRequest = new HttpPost(url);

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("name", username));
		params.add(new BasicNameValuePair("pass", password));


		try {
			HttpEntity httpEntity = new UrlEncodedFormEntity(params,"utf-8");
		
			httpRequest.setEntity(httpEntity);
			
			HttpClient httpClient = new DefaultHttpClient();
		
			HttpResponse httpResponse = httpClient.execute(httpRequest);
		
			
			
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				result = EntityUtils.toString(httpResponse.getEntity());
				Message msg = new Message();//构造消息传给主线程
				msg.what = 2;
				Bundle bundle = new Bundle();
				bundle.putString("status", result);
				msg.setData(bundle);
				handler.sendMessage(msg);
			}else{
				System.out.println("connect failed");
				Message msg = new Message();//构造消息传给主线程
				msg.what = 2;
				Bundle bundle = new Bundle();
				bundle.putString("status", result);
				msg.setData(bundle);
				handler.sendMessage(msg);
				
			}

		} catch (UnsupportedEncodingException e) {
		
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		}
//	用户登录，成功返回用户名，否则返回false
	public void connect_Log(String username,String password){
		String url = "http://hackerday.sinaapp.com/index.php/user/userCheck";
		HttpPost httpRequest = new HttpPost(url);

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("name", username));
		params.add(new BasicNameValuePair("pass", password));


		try {
			HttpEntity httpEntity = new UrlEncodedFormEntity(params,"utf-8");
		
			httpRequest.setEntity(httpEntity);
			
			HttpClient httpClient = new DefaultHttpClient();
		
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
//	获取服务器反馈状态	
				result = EntityUtils.toString(httpResponse.getEntity());//获取的服务器回执结果
				Message msg = new Message();//构造消息传给主线程
				msg.what = 1;
				Bundle bundle = new Bundle();
				bundle.putString("status", result);
				msg.setData(bundle);
				handler.sendMessage(msg);
				
				String i =msg.getData().getString("status");
				System.out.println("1111"+i);
				//匹配返回用户名，否则返回false
				
				
				if(result=="false"){
					
				}else{}
				
		}else{
//				连接失败的处理
				System.out.println("connect failed");
				
				
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	用户更改状态，成功返回true，否则返回false
	public void connect_Change(String username,int state){
		String url = "http://hackerday.sinaapp.com/index.php/position/canbeFound";
		HttpPost httpRequest = new HttpPost(url);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("name", username));
		params.add(new BasicNameValuePair("state", "" + state));
		try {
			HttpEntity httpEntity = new UrlEncodedFormEntity(params,"utf-8");

			httpRequest.setEntity(httpEntity);

			HttpClient httpClient = new DefaultHttpClient();

			HttpResponse httpResponse = httpClient.execute(httpRequest);

			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = EntityUtils.toString(httpResponse.getEntity());
				Message msg = new Message();//构造消息传给主线程
				msg.what = 4;
				Bundle bundle = new Bundle();
				bundle.putString("result", result);
				msg.setData(bundle);
				//handler.sendMessage(msg);
			String i = msg.getData().getString("result");
			    System.out.println(i);
			} else {
				System.out.println("connect failed");

			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
//  用户上传数据，成功返回JSON数据	
	public void connect_Tran(String username,double latitude ,double longitude,
			String radius,String apps ){
		String url = "http://hackerday.sinaapp.com/index.php/position/getGFriends";
		HttpPost httpRequest = new HttpPost(url);
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("name", username));
		params.add(new BasicNameValuePair("lat", "" + latitude));
		params.add(new BasicNameValuePair("long", "" + longitude));
		params.add(new BasicNameValuePair("model",android.os.Build.MODEL));
		params.add(new BasicNameValuePair("radius", radius));
		params.add(new BasicNameValuePair("app",apps));
		try {
			HttpEntity httpEntity = new UrlEncodedFormEntity(params, "utf-8");

			httpRequest.setEntity(httpEntity);

			HttpClient httpClient = new DefaultHttpClient();

			HttpResponse httpResponse = httpClient.execute(httpRequest);

			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = EntityUtils.toString(httpResponse.getEntity());
				Message msg = new Message();//构造消息传给主线程
				msg.what = 3;
				Bundle bundle = new Bundle();
				bundle.putString("result", result);
				msg.setData(bundle);
				handler.sendMessage(msg);
			}
			else{
				System.out.println("connect failed");
			}
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int getStatusOfLogin() {
		return StatusOfLogin;
	}
	public void setStatusOfLogin(int statusOfLogin) {
		StatusOfLogin = statusOfLogin;
	}

	}
	

