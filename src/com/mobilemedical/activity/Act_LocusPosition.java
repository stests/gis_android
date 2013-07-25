package com.mobilemedical.activity;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.alibaba.fastjson.JSON;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.mobilemedical.application.MyApplication;
import com.mobilemedical.entity.Userinfo;
import com.mobilemedical.entity.Userpoint;
import com.mobilemedical.util.MapUtil;

public class Act_LocusPosition extends Activity {

	private String[] funs = new String[] { "定位", "今日轨迹", "月轨迹" };
	private String url_getPoints = null;
	private List<Userpoint> userpoint;

	private BMapManager mBMapMan = null;
	private MapView mMapView = null;
	
	Userinfo locusUser;
	Spinner spinner;
	
	MapController mMapController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		MyApplication.getInstance().addActivity(this);
		super.onCreate(savedInstanceState);
		mBMapMan = new BMapManager(getApplication());
		mBMapMan.init("9E69D5557A25CD210C19A56FDE88A36FD754FB9E", null);
		setContentView(R.layout.act_locusposition);

		mMapView = (MapView) findViewById(R.id.locusposition_bmapsView);
		mMapView.setBuiltInZoomControls(true);

		// 设置启用内置的缩放控件
		mMapController = mMapView.getController();
		GeoPoint point = new GeoPoint((int) (33.138712 * 1E6),
				(int) (105.67746 * 1E6));
		// 用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
		mMapController.setCenter(point);// 设置地图中心点
		mMapController.setZoom(4);// 设置地图zoom级别

		initPage(mMapController);
	}

	private void initPage(MapController mMapController) {

		bindEvent();

		Bundle bundle = this.getIntent().getExtras();
		locusUser = (Userinfo) bundle.getSerializable("locusUser");
		
		url_getPoints = getResources().getString(R.string.url_getPoints);
		
		locusLastPosition(locusUser);
	}
	
	private void bindEvent(){
		spinner = (Spinner) findViewById(R.id.locusposition_sp_fun);
		ArrayAdapter<String> a_funs = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, funs);
		spinner.setAdapter(a_funs);

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adv, View arg1, int i,
					long l) {

				String itemtxt = (String) spinner.getItemAtPosition(i);
				locusUserPosition(locusUser,itemtxt);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
	}

	/**
	 * 定位到最后的出现的位置
	 */
	private void locusLastPosition(Userinfo locusUser) {
		
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url_getPoints+"?type=today&start=&end=&userid="+locusUser.getUserinfoId(), new AsyncHttpResponseHandler() {
		    public void onSuccess(String responsetxt) {
		    	
		    	userpoint = JSON.parseArray(responsetxt, Userpoint.class);
		    	
		    	if(userpoint!=null&&userpoint.size()>0){
		    		GeoPoint fpoint = null;
		    		GeoPoint lpoint = null;
		    		
		    		if(userpoint.size()==1){
		    			//今日的起点
	    				String pointinfo = userpoint.get(0).getPointinfo();
			    		String[] infoArray = pointinfo.split(",");
			    		GeoPoint point = new GeoPoint((int) (Double.parseDouble(infoArray[1]) * 1E6),
			    				(int) (Double.parseDouble(infoArray[0]) * 1E6));
			    		fpoint = point;
		    		}else{
	    				String pointinfo = userpoint.get(0).getPointinfo();
			    		String[] infoArray = pointinfo.split(",");
			    		GeoPoint point = new GeoPoint((int) (Double.parseDouble(infoArray[1]) * 1E6),
			    				(int) (Double.parseDouble(infoArray[0]) * 1E6));
			    		fpoint = point;
			    		
			    		String l_pointinfo = userpoint.get(userpoint.size()-1).getPointinfo(); 
			    		String[] l_infoArray = l_pointinfo.split(",");
			    		GeoPoint l_point = new GeoPoint((int) (Double.parseDouble(l_infoArray[1]) * 1E6),
			    				(int) (Double.parseDouble(l_infoArray[0]) * 1E6));
			    		lpoint = l_point;
		    		}
		    		
		    		MapUtil.addLins(mMapView, userpoint);
		    		// 用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
		    		mMapController.setCenter(fpoint);// 设置地图中心点
		    		MapUtil.addText(mMapView, fpoint,"起点");
		    		if(lpoint!=null){
		    			MapUtil.addText(mMapView, lpoint,"最后出现位置");
		    		}
		    		mMapController.setZoom(12);// 设置地图zoom级别
		    	}
		    	
		    }
		});
		
	}

	/**
	 * @param mMapController
	 * @param user
	 *            用户监测的
	 * @param type
	 *            类型
	 */
	private void locusUserPosition(Userinfo user,
			String type) {
		mMapView.getOverlays().clear();  
		AsyncHttpClient client = new AsyncHttpClient();
		if (type.equals("定位")) {
			client.get(url_getPoints+"?type=today&start=&end=&userid="+locusUser.getUserinfoId(), new AsyncHttpResponseHandler() {
			    public void onSuccess(String responsetxt) {
			    	userpoint = JSON.parseArray(responsetxt, Userpoint.class);
			    	if(userpoint!=null&&userpoint.size()>0){
			    		//得到最后一个点，位置
			    		String l_pointinfo = userpoint.get(userpoint.size()-1).getPointinfo(); 
			    		String[] l_infoArray = l_pointinfo.split(",");
			    		GeoPoint l_point = new GeoPoint((int) (Double.parseDouble(l_infoArray[1]) * 1E6),
			    				(int) (Double.parseDouble(l_infoArray[0]) * 1E6));
			    		MapUtil.addText(mMapView, l_point,"最后出现位置");
			    		mMapController.setCenter(l_point);
			    	}
			    }
			});
		} else if (type.equals("今日轨迹")) {
			client.get(url_getPoints+"?type=today&start=&end=&userid="+locusUser.getUserinfoId(), new AsyncHttpResponseHandler() {
			    public void onSuccess(String responsetxt) {
			    	userpoint = JSON.parseArray(responsetxt, Userpoint.class);
			    	
			    	if(userpoint!=null&&userpoint.size()>0){
			    		GeoPoint fpoint = null;
			    		GeoPoint lpoint = null;
			    		
			    		if(userpoint.size()==1){
			    			//今日的起点
		    				String pointinfo = userpoint.get(0).getPointinfo();
				    		String[] infoArray = pointinfo.split(",");
				    		GeoPoint point = new GeoPoint((int) (Double.parseDouble(infoArray[1]) * 1E6),
				    				(int) (Double.parseDouble(infoArray[0]) * 1E6));
				    		fpoint = point;
			    		}else{
		    				String pointinfo = userpoint.get(0).getPointinfo();
				    		String[] infoArray = pointinfo.split(",");
				    		GeoPoint point = new GeoPoint((int) (Double.parseDouble(infoArray[1]) * 1E6),
				    				(int) (Double.parseDouble(infoArray[0]) * 1E6));
				    		fpoint = point;
				    		
				    		String l_pointinfo = userpoint.get(userpoint.size()-1).getPointinfo(); 
				    		String[] l_infoArray = l_pointinfo.split(",");
				    		GeoPoint l_point = new GeoPoint((int) (Double.parseDouble(l_infoArray[1]) * 1E6),
				    				(int) (Double.parseDouble(l_infoArray[0]) * 1E6));
				    		lpoint = l_point;
			    		}
			    		
			    		MapUtil.addLins(mMapView, userpoint);
			    		// 用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
			    		mMapController.setCenter(fpoint);// 设置地图中心点
			    		MapUtil.addText(mMapView, fpoint,"起点");
			    		if(lpoint!=null){
			    			MapUtil.addText(mMapView, lpoint,"最后出现位置");
			    		}
			    		mMapController.setZoom(12);// 设置地图zoom级别
			    	}
			    }
			});
		} else if (type.equals("月轨迹")) {
			client.get(url_getPoints+"?type=month&start=&end=&userid="+locusUser.getUserinfoId(), new AsyncHttpResponseHandler() {
			    public void onSuccess(String responsetxt) {
			    	userpoint = JSON.parseArray(responsetxt, Userpoint.class);
			    	
			    	if(userpoint!=null&&userpoint.size()>0){
			    		GeoPoint fpoint = null;
			    		GeoPoint lpoint = null;
			    		
			    		if(userpoint.size()==1){
			    			//今日的起点
		    				String pointinfo = userpoint.get(0).getPointinfo();
				    		String[] infoArray = pointinfo.split(",");
				    		GeoPoint point = new GeoPoint((int) (Double.parseDouble(infoArray[1]) * 1E6),
				    				(int) (Double.parseDouble(infoArray[0]) * 1E6));
				    		fpoint = point;
			    		}else{
		    				String pointinfo = userpoint.get(0).getPointinfo();
				    		String[] infoArray = pointinfo.split(",");
				    		GeoPoint point = new GeoPoint((int) (Double.parseDouble(infoArray[1]) * 1E6),
				    				(int) (Double.parseDouble(infoArray[0]) * 1E6));
				    		fpoint = point;
				    		
				    		String l_pointinfo = userpoint.get(userpoint.size()-1).getPointinfo(); 
				    		String[] l_infoArray = l_pointinfo.split(",");
				    		GeoPoint l_point = new GeoPoint((int) (Double.parseDouble(l_infoArray[1]) * 1E6),
				    				(int) (Double.parseDouble(l_infoArray[0]) * 1E6));
				    		lpoint = l_point;
			    		}
			    		
			    		MapUtil.addLins(mMapView, userpoint);
			    		// 用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
			    		mMapController.setCenter(fpoint);// 设置地图中心点
			    		MapUtil.addText(mMapView, fpoint,"起点");
			    		if(lpoint!=null){
			    			MapUtil.addText(mMapView, lpoint,"最后出现位置");
			    		}
			    		mMapController.setZoom(12);// 设置地图zoom级别
			    	}
			    }
			});
		}
	}

}
