package com.mobilemedical.activity;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.mobilemedical.entity.Userinfo;

public class Act_Locus extends Activity {

	private EditText locus_et_idcode;
	private Button locus_btn_search, locus_btn_his;
	private LinearLayout locus_ll_content;
	Context context = null;
	String url_searchUser;
	List<Userinfo> userinfos = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_locus);
		init();
	}

	private void init() {
		this.context = this;
		url_searchUser = getResources().getString(R.string.url_searchUser);
		findView();
		bindEvent();
	}

	private void findView() {
		locus_et_idcode = (EditText) findViewById(R.id.locus_et_idcode);
		locus_btn_search = (Button) findViewById(R.id.locus_btn_search);
		locus_btn_his = (Button) findViewById(R.id.locus_btn_his);
		locus_ll_content = (LinearLayout) findViewById(R.id.locus_ll_content);
	}

	private void bindEvent() {
		// 查询结果集
		locus_btn_search.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String idcode = locus_et_idcode.getText().toString();
				if (!"".equals(idcode)) {
					AsyncHttpClient client = new AsyncHttpClient();
					client.get(url_searchUser+"?code="+idcode, new AsyncHttpResponseHandler() {
					    public void onSuccess(String responsetxt) {
					    	userinfos = JSON.parseArray(responsetxt, Userinfo.class);
					    	if(userinfos.size()>0){
					    		locus_ll_content.removeViews(1, locus_ll_content.getChildCount()-1);
					    		
					    		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
					    		
					    		LayoutParams lp_tv = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
					    		lp_tv.addRule(RelativeLayout.ALIGN_PARENT_LEFT,RelativeLayout.TRUE);
					    		lp_tv.addRule(RelativeLayout.CENTER_VERTICAL,RelativeLayout.TRUE);
					    		lp_tv.rightMargin = 4;
					    		
					    		LayoutParams lp_tv_r = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
					    		lp_tv_r.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
					    		lp_tv_r.addRule(RelativeLayout.CENTER_VERTICAL,RelativeLayout.TRUE);
					    		
					    		for(int i=0;i<userinfos.size();i++){
					    			
					    			RelativeLayout rl = new RelativeLayout(context);
					    			rl.setLayoutParams(lp);
					    			rl.setMinimumHeight(34);
					    			rl.setPadding(3, 0, 3, 0);
					    			if(i%2!=0){
					    				rl.setBackgroundResource(R.color.gray);
					    			}
					    			
					    			TextView tv_id = new TextView(context);
					    			tv_id.setLayoutParams(lp_tv);
					    			tv_id.setText(i+"");
					    			tv_id.setVisibility(8);
					    			rl.addView(tv_id);
					    			
					    			
					    			TextView tv_name = new TextView(context);
					    			tv_name.setLayoutParams(lp_tv);
					    			tv_name.setSingleLine();
					    			tv_name.setText(userinfos.get(i).getUsername());
					    			tv_name.setTextSize(14);
					    			rl.addView(tv_name);
					    			
					    			
					    			TextView tv_group = new TextView(context);
					    			tv_group.setLayoutParams(lp_tv_r);
					    			tv_group.setText(userinfos.get(i).getGroupname());
					    			tv_group.setTextSize(14);
					    			rl.addView(tv_group);
					    			
					    			rl.setOnClickListener(new OnClickListener() {
										public void onClick(View v) {
											
											RelativeLayout crl = (RelativeLayout)v;
											TextView t = (TextView)crl.getChildAt(0);
											
											
											Intent intent = new Intent();
											Bundle bundle = new Bundle();
											bundle.putSerializable("locusUser",userinfos.get(Integer.parseInt(t.getText().toString())));
											intent.putExtras(bundle);
											intent.setClass(context, Act_LocusPosition.class);
											startActivity(intent);
										}
									});
					    			
					    			locus_ll_content.addView(rl);
					    		}
					    	}else{
					    		locus_ll_content.removeViews(1, locus_ll_content.getChildCount()-1);
					    		Toast.makeText(getApplicationContext(), "没有查询到记录！",
										Toast.LENGTH_SHORT).show();
					    	}
					    }
					});
					
				} else {
					Toast.makeText(getApplicationContext(), "请输入身份证号码！",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		// 得到历史记录
		locus_btn_his.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
			}
		});
	}

}
