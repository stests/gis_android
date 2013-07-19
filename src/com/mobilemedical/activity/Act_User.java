package com.mobilemedical.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class Act_User extends Activity implements OnClickListener {
	
	Context context = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_user);
		this.context = this;
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.rl_user_position:
			intent.setClass(context, Act_Position.class);
			break;
		}
		startActivity(intent);
		
	}
	
}
