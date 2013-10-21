package com.inter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class FindGfriend extends Activity{
	private String result;
	private Button btn;
	private TextView tv;
	private static final String TAG = "DemoActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	 
		setContentView(R.layout.findgfriends);
		
		btn = (Button)findViewById(R.id.buttons);//
		tv = (TextView)findViewById(R.id.textview);
		
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent2 = new Intent();
				intent2.setClass(FindGfriend.this,AppMain.class);
				FindGfriend.this.startActivity(intent2);		
			}
		});
		
		Intent intent1 = getIntent();
		result = intent1.getStringExtra("res");
		try {
	
	            JSONObject jo = new JSONObject(result);
	            JSONArray jsonArray = (JSONArray) jo.get("GFriends");
	            result="";
	            for (int i = 0; i < jsonArray.length(); ++i) {
	            	Log.i(TAG,"result2 = "+result);
	            	JSONObject o = (JSONObject) jsonArray.get(i);
	            	Log.i(TAG,"result3 = "+result);
	                result+="»ú ÓÑ:" + o.getString("name") + "    " + "ÀëÎÒ:"+ o.getString("distance")+"Ã×"+"\n"+"\n";
	            	}
	       
	        } catch (JSONException e1) {
	            e1.printStackTrace();
	        }
		  Log.i(TAG,"result5 = "+result);
		  tv.setText(result);

	}
	
}





















