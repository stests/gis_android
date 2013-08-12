package com.mobilemedical.activity;

import java.util.HashMap;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.mobilemedical.application.MyApplication;
import com.mobilemedical.bean.Msg;
import com.mobilemedical.entity.Userinfo;
import com.mobilemedical.util.Constant;

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
    
    private CheckBox ck_rember;
    private CheckBox ck_autologin;
    
    private boolean isNetError;
    
	/** 登录loading提示框 */
	private ProgressDialog proDialog;
	
	private Context context;
	
	SharedPreferences sp;
	


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
    	MyApplication.getInstance().addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        findViewsById();
        init();
        loginCheck();
        setListener();
    }
    
    private void init(){
    	context = this;
    	sp = context.getSharedPreferences("SP", MODE_PRIVATE);
    }
    
    private void loginCheck(){
    	String userinfojson = sp.getString("userinfojson","");
    	if(!"".equals(userinfojson)&&userinfojson!=null){
    		Userinfo userinfo = JSON.toJavaObject(JSON.parseObject(userinfojson),Userinfo.class);
    		Constant.userinfo = userinfo;
    		et_account.setText(userinfo.getIdcode());
    		et_password.setText(userinfo.getPassword());
    	}
    	Boolean isautologin = sp.getBoolean("isautologin", false);
    	if(isautologin){
    		//是自动登入
    		Userinfo userinfo = JSON.toJavaObject(JSON.parseObject(userinfojson),Userinfo.class);
    		Constant.userinfo = userinfo;
    		loginDo(userinfo);
    	}
    }

	private void findViewsById() {
        et_account = (EditText) findViewById(R.id.et_account);
        et_password = (EditText) findViewById(R.id.et_pwd);
        btn_login = (Button)findViewById(R.id.btn_login);
        ck_rember = (CheckBox)findViewById(R.id.ck_rember);
        ck_autologin = (CheckBox)findViewById(R.id.ck_autologin);
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
			
			AsyncHttpClient client = new AsyncHttpClient();
			client.get(url_login+"?idcode="+user+"&password="+pass, new AsyncHttpResponseHandler() {
			    public void onSuccess(String response) {
			    	Msg msg = (Msg)JSON.toJavaObject(JSON.parseObject(response), Msg.class);
					if(msg.isType() == true){
						//验证通过
						Userinfo userinfo = JSON.toJavaObject(JSON.parseObject(msg.getMessage()),Userinfo.class);
						Constant.userinfo = userinfo;
						Editor editor = sp.edit();
						//记住密码
						if(ck_rember.isChecked()){
							editor.putString("userinfojson",msg.getMessage());
						}else{
							editor.remove("userinfojson");	
						}
						//自动登入
						if(ck_autologin.isChecked()){
							editor.putString("userinfojson",msg.getMessage());
							editor.putBoolean("isautologin", true);
						}else{
							editor.putBoolean("isautologin", false);
						}
						editor.commit();
						
						loginDo(userinfo);
						
					}else{
						//未通过
						Message message = new Message();
						Bundle bundle = new Bundle();
						bundle.putBoolean("isNetError", isNetError);
						message.setData(bundle);
						loginHandler.sendMessage(message);
					}
			    }
			    public void onFailure(Throwable e, String response) {
			    	Toast.makeText(Act_Login.this, "登陆失败:\n1.请检查您网络连接.\n2.请联系我们.!",
							Toast.LENGTH_SHORT).show();
			    }
			    public void onFinish() {
			    	proDialog.dismiss();
			    }
			});
		}
    }
    
    public void loginDo(Userinfo userinfo){
    	if(userinfo.getIsadmin()==1){
			//管理员界面
			Intent intent = new Intent();
			intent.setClass(Act_Login.this, Act_Main.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("userinfo", userinfo);
			intent.putExtras(bundle);
			// 转向登陆后的页面
			startActivity(intent);
		}else{
			//用户界面
			Intent intent = new Intent();
			intent.setClass(Act_Login.this, Act_User.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("userinfo", userinfo);
			intent.putExtras(bundle);
			// 转向登陆后的页面
			startActivity(intent);
		}
    	if(proDialog!=null){
    		proDialog.dismiss();
    	}
    }
    
}

