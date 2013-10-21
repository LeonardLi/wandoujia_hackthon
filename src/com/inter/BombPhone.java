package com.inter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
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

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.inter.R;
import com.inter.BombPhone.MyBDLocationListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BombPhone extends Activity {

	private Button tbtn, swbtn;
	private Toast toast;
	private TextView tv, tv1;
	private Drawable dr;
	private int available = 0;
	private String result, result1, username;
	private double latitude, longitude;
	private LocationClient mLocationClient;
	private MyBDLocationListener mBDLocationListener;
	private static final String TAG = "DemoActivity";
	Handler handler = new Handler(){
		@Override 
		public void handleMessage(Message msg){
			 result =msg.getData().getString("result");
			 if (msg.what==3){//��ȡjson����
			 if(result.equals(null)){
				 
			 }else{
				System.out.println(result);
				Intent intent1 = new Intent();
				intent1.putExtra("res", result);
				intent1.setClass(BombPhone.this,FindGfriend.class);
				BombPhone.this.startActivity(intent1); 
			 }}
			 if(msg.what==4){//�ı�״̬
				 if(result.equals("true")){
					
				 }else{
					 
				 }
				 
			 }
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bombphone);
		swbtn = (Button) findViewById(R.id.swbutton);
		tbtn = (Button) findViewById(R.id.tance);
		tv = (TextView) findViewById(R.id.textview);
		tv1 = (TextView) findViewById(R.id.textview1);
		Intent intent = getIntent();
		username = intent.getStringExtra("user");
		tbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(available == 1){
					Connector con = new Connector(4,username,latitude,longitude,"1500","0",handler);
					con.start();
				}
		
				else {
					toast = Toast.makeText(getApplicationContext(), "���ȴ򿪿��أ�",Toast.LENGTH_SHORT);
					LinearLayout toastView = (LinearLayout) toast.getView();
					toast.show();
				}
			}
		});

		swbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				available^=1;
				//Toast
				if(available == 0){
					toast = Toast.makeText(getApplicationContext(), "���Ѷ�������...", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					LinearLayout toastView = (LinearLayout) toast.getView();
					ImageView imageCodeProject = new ImageView(getApplicationContext());
					imageCodeProject.setImageResource(R.drawable.no);
					toastView.addView(imageCodeProject, 0);
					toast.show();
				}else{
					toast = Toast.makeText(getApplicationContext(), "ȥ�һ����...", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					LinearLayout toastView = (LinearLayout) toast.getView();
					ImageView imageCodeProject = new ImageView(getApplicationContext());
					imageCodeProject.setImageResource(R.drawable.yes);
					toastView.addView(imageCodeProject, 0);
					toast.show();
				}
				
				Intent intent3 = getIntent();
				username = intent3.getStringExtra("user");
				Connector con = new Connector(3,username,available);
				   con.start();
				}
			// imgbtn.setBackgroundDrawable(dr);
		});
		// ////////////////////////////////////////////////////////////

		mLocationClient = new LocationClient(this.getApplicationContext());

		mBDLocationListener = new MyBDLocationListener();
		mLocationClient.registerLocationListener(mBDLocationListener);

		LocationClientOption option = new LocationClientOption();

		// ��Ҫ��ַ��Ϣ������Ϊ�����κ�ֵ��string���ͣ��Ҳ���Ϊnull��ʱ������ʾ�޵�ַ��Ϣ��
		option.setAddrType("all");
		// �����Ƿ񷵻�POI�ĵ绰�͵�ַ����ϸ��Ϣ��Ĭ��ֵΪfalse����������POI�ĵ绰�͵�ַ��Ϣ��
		option.setPoiExtraInfo(false);

		// ���ò�Ʒ�����ơ�ǿ�ҽ�����ʹ���Զ���Ĳ�Ʒ�����ƣ����������Ժ�Ϊ���ṩ����Ч׼ȷ�Ķ�λ����
		option.setProdName("��ǰλ��");

		// ��GPS��ʹ��gpsǰ�����û�Ӳ����gps��Ĭ���ǲ���gps�ġ�
		option.setOpenGps(false);

		// ��λ��ʱ��������λ��ms
		// �����������ֵ���ڵ���1000��ms��ʱ����λSDK�ڲ�ʹ�ö�ʱ��λģʽ��
		// option.setScanSpan(1000);

		// ��ѯ��Χ��Ĭ��ֵΪ500�����Ե�ǰ��λλ��Ϊ���ĵİ뾶��С��
		option.setPoiDistance(500);
		// �������û��涨λ����
		option.disableCache(true);

		// ����ϵ���ͣ��ٶ��ֻ���ͼ����ӿ��е�����ϵĬ����bd09ll
		option.setCoorType("bd0911");

		// �������ɷ��ص�POI������Ĭ��ֵΪ3������POI��ѯ�ȽϺķ�������������෵�ص�POI�������Ա��ʡ������
		option.setPoiNumber(3);

		// ���ö�λ��ʽ�����ȼ���
		// ��ʹ��GPS�����ҿ��ã�Ҳ�Ծɻᷢ�������������ѡ���ʺ϶Ծ�ȷ���겻���ر����У�����ϣ���õ�λ���������û���
		option.setPriority(LocationClientOption.NetWorkFirst);

		mLocationClient.setLocOption(option);
		mLocationClient.start();
	}

	final class MyBDLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			Log.e(TAG, "---------onReceiveLocation()---------");

			if (location == null) {
				Log.e(TAG,
						"---------onReceiveLocation------location is NULL----");
				return;
			}

			int type = location.getLocType();
			Log.i(TAG, "��ǰ��λ���õ������ǣ�type = " + type);

			String coorType = location.getCoorType();
			Log.i(TAG, "����ϵ����:coorType = " + coorType);

			// �ж��Ƿ��ж�λ���Ȱ뾶
			if (location.hasRadius()) {
				// ��ȡ��λ���Ȱ뾶����λ����
				float accuracy = location.getRadius();
				Log.i(TAG, "accuracy = " + accuracy);
			}

			if (location.hasAddr()) {
				// ��ȡ��������롣 ֻ��ʹ�����綨λ������£����ܻ�ȡ��ǰλ�õķ��������������
				String address = location.getAddrStr();
				Log.i(TAG, "address = " + address);
			}

			/*
			 * String province = location.getProvince(); // ��ȡʡ����Ϣ String city =
			 * location.getCity(); // ��ȡ������Ϣ String district =
			 * location.getDistrict(); // ��ȡ������Ϣ
			 * 
			 * Log.i(TAG, "province = " + province); Log.i(TAG, "city = " +
			 * city); Log.i(TAG, "district = " + district);
			 */
			latitude = location.getLatitude();
			longitude = location.getLongitude();
			Log.i(TAG, "latitude = " + latitude);
			Log.i(TAG, "longitude = " + longitude);
			tv.setText("����ǰλ�õ�γ����" + latitude + "��");
			tv1.setText("����ǰλ�õľ�����" + longitude + "��");
		}

		@Override
		public void onReceivePoi(BDLocation poiLocation) {

			Log.e(TAG, "---------onReceivePoi()---------");

			if (poiLocation == null) {
				Log.e(TAG, "---------onReceivePoi------location is NULL----");
				return;
			}

			if (poiLocation.hasPoi()) {
				String poiStr = poiLocation.getPoi();
				Log.i(TAG, "poiStr = " + poiStr);

			}

			if (poiLocation.hasAddr()) {
				// ��ȡ��������롣 ֻ��ʹ�����綨λ������£����ܻ�ȡ��ǰλ�õķ��������������
				String address = poiLocation.getAddrStr();
				Log.i(TAG, "address = " + address);
			}
		}

	}
}
