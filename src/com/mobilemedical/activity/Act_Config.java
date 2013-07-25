package com.mobilemedical.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.mobilemedical.application.MyApplication;

public class Act_Config  extends Activity {
	
	SharedPreferences sp;
	Context context;
	Button btn_relogin;

    protected void onCreate(Bundle savedInstanceState) {
    	MyApplication.getInstance().addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_config);
        init();
    }
    
    private void init(){
    	context = this;
    	sp = context.getSharedPreferences("SP", MODE_PRIVATE);
    	
    	findViewById();
    	bindEvent();
    }
	
    private void findViewById(){
    	btn_relogin = (Button)findViewById(R.id.config_btn_relogin);
    }
    
    private void bindEvent(){
    	btn_relogin.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Editor editor = sp.edit();
				editor.remove("userinfojson");
				editor.remove("isautologin");
				editor.commit();
				
				Intent intent = new Intent();
				intent.setClass(context, Act_Login.class);
				startActivity(intent);
				
			}
		});
    }
    
}
