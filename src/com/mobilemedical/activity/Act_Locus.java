package com.mobilemedical.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

public class Act_Locus extends Activity {
	
	private EditText chooseperson;

	String[] funs = new String[] { "定位", "今日轨迹", "月轨迹" };

	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_locus);
		init();
		// ArrayAdapter<String> a_funs = new ArrayAdapter<String>(this,
		// android.R.layout.simple_dropdown_item_1line,funs);
		// Spinner spinner = (Spinner) findViewById(R.id.sp_fun);
		// spinner.setAdapter(a_funs);

	}

	private void init() {
		bindEvent();
	}

	private void bindEvent() {
		

	}

}
