package com.mobilemedical.activity;

import com.mobilemedical.entity.Userinfo;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class Act_Main extends Activity implements OnClickListener {

	Context context = null;
	RelativeLayout relative1, relative2;
	Userinfo userinfo ;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_main);
		initPage();
	}
	
	public void initPage(){
		Bundle bundle = this.getIntent().getExtras();
		userinfo = (Userinfo) bundle.getSerializable("userinfo");

		relative1 = (RelativeLayout) findViewById(R.id.main_position);
		relative2 = (RelativeLayout) findViewById(R.id.main_locus);
		this.context = this;
		relative1.setOnClickListener(this);
		relative2.setOnClickListener(this);
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
		}
		startActivity(intent);
	}

}