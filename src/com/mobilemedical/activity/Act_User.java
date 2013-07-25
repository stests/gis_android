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

public class Act_User extends Activity implements OnClickListener {

	Context context = null;
	Userinfo userinfo;
	RelativeLayout rl_user_position,rel_user_config;

	protected void onCreate(Bundle savedInstanceState) {
		MyApplication.getInstance().addActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_user);
		this.context = this;
		rl_user_position = (RelativeLayout) findViewById(R.id.rl_user_position);
		rl_user_position.setOnClickListener(this);
		rel_user_config = (RelativeLayout) findViewById(R.id.rel_user_config);
		rel_user_config.setOnClickListener(this);
		

		initPage();

	}

	private void initPage() {
		Bundle bundle = this.getIntent().getExtras();
		userinfo = (Userinfo) bundle.getSerializable("userinfo");
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putSerializable("userinfo", userinfo);
		intent.putExtras(bundle);
		switch (v.getId()) {
		case R.id.rl_user_position:
			intent.setClass(context, Act_Position.class);
			break;
		case R.id.rel_user_config:
			intent.setClass(context, Act_Config.class);
			break;
		}
		startActivity(intent);

	}

	public void onBackPressed() {
		new AlertDialog.Builder(context)
				.setTitle("提示")
				.setMessage("确定退出吗？")
				.setPositiveButton("是",
						new android.content.DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								MyApplication.getInstance().exit();
							}
						}).setNegativeButton("否", null).show();
	}

}
