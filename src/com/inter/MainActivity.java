package com.inter;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	private Button btn,btn1;
	private TextView tv;
	private String result;
	private EditText edt,edt1;
	private String username,password;
	private static final String TAG= "DemoActivity";
	private int StatusOfLogin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tv = (TextView)findViewById(R.id.textview1);
		btn1 = (Button)findViewById(R.id.button1);
		btn = (Button)findViewById(R.id.button);
		edt = (EditText)findViewById(R.id.edittext);
		edt1 = (EditText)findViewById(R.id.edittext1);
		
	btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//登录跳转点击
				Intent intent = new Intent();
				intent.setClass(MainActivity.this,RegisterPage.class);
				MainActivity.this.startActivity(intent);				
			}
		});
	
	btn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

/////////////////////
			username = edt.getText().toString();
			password = edt1.getText().toString();
				if(username.equals("") || password.equals("")){
					tv.setText("请输入用户名或者密码");
					tv.setTextColor(Color.RED);
				}else{
						Handler h = new Handler(){
						@Override
						public void handleMessage(Message msg){
							String i =msg.getData().getString("status");
							 System.out.println("2222"+i);
							if(i.equals("false")){
								tv.setText("用户名或密码错误");
								tv.setTextColor(Color.RED);
							}else{
								
								Intent intent = new Intent();
								intent.putExtra("user", username);
								intent.setClass(MainActivity.this,BombPhone.class);
								MainActivity.this.startActivity(intent);
								finish();
							}
												}
					};
					Connector con = new Connector(2,username,password,h);
						con.start();
						}
						}
						});
	
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	
	@Override
	protected void onRestart(){
		super.onRestart();
	}
	@Override
	protected void onPause(){
		super.onPause();
	}
	
	@Override
	protected void onResume(){
		super.onResume();
	}
	@Override
	protected void onStart(){
		super.onStart();
	}
	protected void onDestroy(){
		super.onDestroy();
	}
}
