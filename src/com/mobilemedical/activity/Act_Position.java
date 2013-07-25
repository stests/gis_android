package com.mobilemedical.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Spinner;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.mobilemedical.application.MyApplication;
import com.mobilemedical.entity.Userinfo;
import com.mobilemedical.listener.LocationListener;

/**
 * User: zhujun Date: 13-7-16 Time: 上午10:59
 */
public class Act_Position extends Activity {

	private BMapManager mBMapMan = null;
	private MapView mMapView = null;

	public LocationClient mLocationClient = null;
	public LocationListener locationListener = new LocationListener();

	Userinfo userinfo;

	Spinner spinner;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		MyApplication.getInstance().addActivity(this);
		super.onCreate(savedInstanceState);
		mBMapMan = new BMapManager(getApplication());
		mBMapMan.init("9E69D5557A25CD210C19A56FDE88A36FD754FB9E", null);
		setContentView(R.layout.act_position);

		initPage();

		mMapView = (MapView) findViewById(R.id.bmapsView);
		mMapView.setBuiltInZoomControls(true);

		// 设置启用内置的缩放控件
		MapController mMapController = mMapView.getController();

		locationListener.setMapController(mMapController);
		locationListener.setMapView(mMapView);

		// 定位，将定位坐标替换point,坐标系
		mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
		mLocationClient.registerLocationListener(locationListener); // 注册监听函数
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setAddrType("all");// 返回的定位结果包含地址信息
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(30000);// 设置发起定位请求的间隔时间为5000ms
		option.disableCache(true);// 禁止启用缓存定位
		option.setPoiNumber(5); // 最多返回POI个数
		option.setPoiDistance(1000); // poi查询距离
		option.setPoiExtraInfo(true); // 是否需要POI的电话和地址等详细信息
		mLocationClient.setLocOption(option);

		// //设置启用内置的缩放控件
		// MapController mMapController=mMapView.getController();
		// // 得到mMapView的控制权,可以用它控制和驱动平移和缩放
		// GeoPoint point =new GeoPoint((int)(39.915* 1E6),(int)(116.404* 1E6));
		// //用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
		// mMapController.setCenter(point);//设置地图中心点
		// mMapController.setZoom(12);//设置地图zoom级别

	}

	private void initPage() {

	}

	@Override
	protected void onDestroy() {
		mMapView.destroy();
		if (mBMapMan != null) {
			mBMapMan.destroy();
			mBMapMan = null;
		}
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		if (mBMapMan != null) {
			mBMapMan.stop();
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		if (mBMapMan != null) {
			mBMapMan.start();
		}
		super.onResume();
	}

}