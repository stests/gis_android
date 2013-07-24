package com.mobilemedical.activity;

import com.mobilemedical.entity.Userinfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class Act_User extends Activity implements OnClickListener {
	
	Context context = null;
	Userinfo userinfo ;
	RelativeLayout rl_user_position;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_user);
		this.context = this;
		rl_user_position = (RelativeLayout) findViewById(R.id.rl_user_position);
		rl_user_position.setOnClickListener(this);
		
		initPage();
		
	}
	
	private void initPage(){
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
		}
		startActivity(intent);
		
	}
	
}
