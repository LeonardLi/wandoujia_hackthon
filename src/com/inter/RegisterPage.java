package com.inter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class RegisterPage extends Activity{
	private Button btn2;
	private EditText edt2,edt3;
	private String username1,password1;
	Handler h = new Handler(){
		@Override 
		public void handleMessage(Message msg){
			String i = msg.getData().getString("status");
			if(i.equals("true")){
			Intent intent = new Intent();
			intent.setClass(RegisterPage.this,MainActivity.class);
			RegisterPage.this.startActivity(intent);}
			else{
				System.out.println(i);//输出错误信息
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		
		btn2 = (Button)findViewById(R.id.button2);
		edt2 = (EditText)findViewById(R.id.edittext2);
		edt3 = (EditText)findViewById(R.id.edittext3);
		
	btn2.setOnClickListener(new OnClickListener() {
		
		@Override
					public void onClick(View v) {
			
			
				username1 = edt2.getText().toString();
				password1 = edt3.getText().toString();
				if(username1==null||password1==null){
					//信息不完整
				}else{
				Connector con = new Connector(1,username1,password1,h);
				con.start();
				}
				}
	});
		
	}

}
