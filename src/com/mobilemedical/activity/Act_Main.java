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

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_main);

		Bundle bundle = this.getIntent().getExtras();
		Userinfo userinfo = (Userinfo) bundle.getSerializable("userinfo");

		RelativeLayout relative1 = (RelativeLayout) findViewById(R.id.main_position);
		RelativeLayout relative2 = (RelativeLayout) findViewById(R.id.main_locus);
		this.context = this;
		relative1.setOnClickListener(this);
		relative2.setOnClickListener(this);

		if (userinfo.getIsadmin() == 1) {
			// 是管理员
		} else {
			relative2.setVisibility(8);
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
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