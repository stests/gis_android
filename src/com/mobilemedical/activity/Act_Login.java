package com.mobilemedical.activity;

import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.mobilemedical.bean.Msg;
import com.mobilemedical.entity.Userinfo;
import com.mobilemedical.util.HttpUtil;

/**
 * User: zhujun
 * Date: 13-7-16
 * Time: 上午10:31
 */
/**
 * @author Administrator
 *
 */
public class Act_Login extends Activity {

    private EditText et_account;
    private EditText et_password;
    private Button btn_login;
    
    private boolean isNetError;
    
	/** 登录loading提示框 */
	private ProgressDialog proDialog;
	


	/** 登录后台通知更新UI线程,主要用于登录失败,通知UI线程更新界面 */
	Handler loginHandler = new Handler() {
		public void handleMessage(Message msg) {
			isNetError = msg.getData().getBoolean("isNetError");
			if (proDialog != null) {
				proDialog.dismiss();
			}
			if (isNetError) {
				Toast.makeText(Act_Login.this, "登陆失败:\n1.请检查您网络连接.\n2.请联系我们.!",
						Toast.LENGTH_SHORT).show();
			}
			// 用户名和密码错误
			else {
				Toast.makeText(Act_Login.this, "登陆失败,请输入正确的用户名和密码!",
						Toast.LENGTH_SHORT).show();
			}
		}
	};
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        findViewsById();
        setListener();
    }

	private void findViewsById() {
        et_account = (EditText) findViewById(R.id.et_account);
        et_password = (EditText) findViewById(R.id.et_pwd);
        btn_login = (Button)findViewById(R.id.btn_login);
    }
	
	
    /**
     * 设置监听器
     */
    private void setListener() {
    	btn_login.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				proDialog = ProgressDialog.show(Act_Login.this, "连接中..",
						"连接中..请稍后....", true, true);
				// 开一个线程进行登录验证,主要是用于失败,成功可以直接通过startAcitivity(Intent)转向
				Thread loginThread = new Thread(new LoginFailureHandler());
				loginThread.start();
			}
		});
	}
    
    class LoginFailureHandler implements Runnable {
		public void run() {
			
			String user = et_account.getText().toString();
			String pass = et_password.getText().toString();
			
			String url_login = getResources().getString(R.string.url_login);
			
			HashMap map = new HashMap();
			map.put("idcode", user);
			map.put("password", pass);
			
			String msg_text = HttpUtil.get(url_login,map);
			Msg msg = (Msg)JSON.toJavaObject(JSON.parseObject(msg_text), Msg.class);
			if(msg.isType() == true){
				//验证通过
				Userinfo userinfo = JSON.toJavaObject(JSON.parseObject(msg.getMessage()),Userinfo.class);
				if(userinfo.getIsadmin()==1){
					//管理员界面
					Intent intent = new Intent();
					intent.setClass(Act_Login.this, Act_Main.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("userinfo", userinfo);
					intent.putExtras(bundle);
					// 转向登陆后的页面
					startActivity(intent);
					proDialog.dismiss();
				}else{
					//用户界面
					Intent intent = new Intent();
					intent.setClass(Act_Login.this, Act_User.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("userinfo", userinfo);
					intent.putExtras(bundle);
					// 转向登陆后的页面
					startActivity(intent);
					proDialog.dismiss();
				}
				
			}else{
				//未通过
				Message message = new Message();
				Bundle bundle = new Bundle();
				bundle.putBoolean("isNetError", isNetError);
				message.setData(bundle);
				loginHandler.sendMessage(message);
			}
			
		}
    }
    
}

