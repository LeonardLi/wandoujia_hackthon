package com.inter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FilterApplication  extends Activity implements OnClickListener{

	private Button btallapp; // 所有应用程序
	private Button btsystemapp;// 系统程序
	private Button btthirdapp; // 第三方应用程序
	private Button btsdcardapp; // 安装在SDCard的应用程序
	
	private int filter = AppMain.FILTER_ALL_APP; 
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 初始化控件并监听
		btallapp = (Button) findViewById(R.id.btallapp);
		btsystemapp = (Button) findViewById(R.id.btsystemapp);
		btthirdapp = (Button) findViewById(R.id.btthirdapp);
		btsdcardapp =(Button) findViewById(R.id.btsdcardapp);
		btallapp.setOnClickListener(this);
		btsystemapp.setOnClickListener(this);
		btthirdapp.setOnClickListener(this);
		btsdcardapp.setOnClickListener(this);
	}
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		System.out.println(""+view.getId());
		switch(view.getId()){
		case R.id.btallapp	:
			filter = AppMain.FILTER_ALL_APP ;
			break ;
		case R.id.btsystemapp:
			filter = AppMain.FILTER_SYSTEM_APP ;
			break ;
		case R.id.btthirdapp:
			filter = AppMain.FILTER_THIRD_APP ;
			break ;
		case R.id.btsdcardapp:
			filter = AppMain.FILTER_SDCARD_APP ;
			break ;
		}
		Intent intent = new Intent(getBaseContext(),AppMain.class) ;
		intent.putExtra("filter", filter) ;
		startActivity(intent);
	}

}
