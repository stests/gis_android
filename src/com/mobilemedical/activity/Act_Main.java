package com.mobilemedical.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.mobilemedical.application.MyApplication;
import com.mobilemedical.entity.Userinfo;

public class Act_Main extends Activity implements OnClickListener {

	Context context = null;
	RelativeLayout relative1, relative2,relative3;
	Userinfo userinfo ;

	protected void onCreate(Bundle savedInstanceState) {
		MyApplication.getInstance().addActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_main);
		initPage();
	}
	
	public void initPage(){
		Bundle bundle = this.getIntent().getExtras();
		userinfo = (Userinfo) bundle.getSerializable("userinfo");

		relative1 = (RelativeLayout) findViewById(R.id.main_position);
		relative2 = (RelativeLayout) findViewById(R.id.main_locus);
		relative3 = (RelativeLayout) findViewById(R.id.main_config);
		
		this.context = this;
		relative1.setOnClickListener(this);
		relative2.setOnClickListener(this);
		relative3.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putSerializable("userinfo", userinfo);
		intent.putExtras(bundle);
		switch (v.getId()) {
		case R.id.main_position:
			intent.setClass(context, Act_Position.class);
			break;
		case R.id.main_locus:
			intent.setClass(context, Act_Locus.class);
			break;
		case R.id.main_config:
			intent.setClass(context, Act_Config.class);
			break;
		}
		startActivity(intent);
	}

	public void onBackPressed() {
	    new AlertDialog.Builder(context)   
	    .setTitle("提示")  
	    .setMessage("确定退出吗？")  
	    .setPositiveButton("是", new android.content.DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which) {
				MyApplication.getInstance().exit();
			}
	    })  
	    .setNegativeButton("否",null)  
	    .show();  
	}
}