package com.mobilemedical.listener;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.mobilemedical.activity.R;
import com.mobilemedical.util.Constant;

public class LocationListener implements BDLocationListener {
	
	private MapController mapController;
	public void setMapController(MapController mapController) {
		this.mapController = mapController;
	}

	private BDLocation poilocation;
	public BDLocation getPoilocation() {
		return poilocation;
	}
	
	private MapView mapView;
	public void setMapView(MapView mapView) {
		this.mapView = mapView;
	}
	
	private String url_insertpoint;
	public void setUrl_insertpoint(String url_insertpoint) {
		this.url_insertpoint = url_insertpoint;
	}

	private Boolean first = true;
	

	@Override
	public void onReceiveLocation(BDLocation location) {
		if (location == null)
			return;
		
		poilocation = location;
		
		GeoPoint point =new GeoPoint((int)(poilocation.getLatitude()* 1E6),(int)(poilocation.getLongitude()* 1E6));
		mapController.setZoom(12);//设置地图zoom级别
		
		MyLocationOverlay myLocationOverlay = new MyLocationOverlay(mapView);  
		LocationData locData = new LocationData();  
		//手动将位置源置为天安门，在实际应用中，请使用百度定位SDK获取位置信息，要在SDK中显示一个位置，需要使用百度经纬度坐标（bd09ll）  
		locData.latitude = poilocation.getLatitude();  
		locData.longitude = poilocation.getLongitude();  
		locData.direction = 2.0f;  
		myLocationOverlay.setData(locData);  
		mapView.getOverlays().add(myLocationOverlay);  
		mapView.refresh();  
		
		mapController.animateTo(point);
		
		//userinfoId,pointinfo
		
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url_insertpoint+"?userinfoId="+Constant.userinfo.getUserinfoId()+"&pointinfo="+locData.longitude+","+locData.latitude, new AsyncHttpResponseHandler() {
		    public void onSuccess(String responsetxt) {
		    	
		    }
		});
		
		
		
		
//		StringBuffer sb = new StringBuffer(256);
//		sb.append("time : ");
//		sb.append(location.getTime());
//		sb.append("\nerror code : ");
//		sb.append(location.getLocType());
//		sb.append("\nlatitude : ");
//		sb.append(location.getLatitude());
//		sb.append("\nlontitude : ");
//		sb.append(location.getLongitude());
//		sb.append("\nradius : ");
//		sb.append(location.getRadius());
//		if (location.getLocType() == BDLocation.TypeGpsLocation) {
//			sb.append("\nspeed : ");
//			sb.append(location.getSpeed());
//			sb.append("\nsatellite : ");
//			sb.append(location.getSatelliteNumber());
//		} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
//			sb.append("\naddr : ");
//			sb.append(location.getAddrStr());
//		}
//		Log.e("t", sb.toString());

	}

	@Override
	public void onReceivePoi(BDLocation poiLocation) {
		if (poiLocation == null) {
			return;
		}
		
		poilocation = poiLocation;
		
		GeoPoint point =new GeoPoint((int)(poiLocation.getLatitude()* 1E6),(int)(poiLocation.getLongitude()* 1E6));
		mapController.setZoom(12);//设置地图zoom级别
		
		MyLocationOverlay myLocationOverlay = new MyLocationOverlay(mapView);  
		LocationData locData = new LocationData();  
		//手动将位置源置为天安门，在实际应用中，请使用百度定位SDK获取位置信息，要在SDK中显示一个位置，需要使用百度经纬度坐标（bd09ll）  
		locData.latitude = poiLocation.getLatitude();  
		locData.longitude = poiLocation.getLongitude();  
		locData.direction = 2.0f;  
		myLocationOverlay.setData(locData);  
		mapView.getOverlays().add(myLocationOverlay);  
		mapView.refresh();  
		
		mapController.animateTo(point);
		
		
//		StringBuffer sb = new StringBuffer(256);
//		sb.append("Poi time : ");
//		sb.append(poiLocation.getTime());
//		sb.append("\nerror code : ");
//		sb.append(poiLocation.getLocType());
//		sb.append("\nlatitude : ");
//		sb.append(poiLocation.getLatitude());
//		sb.append("\nlontitude : ");
//		sb.append(poiLocation.getLongitude());
//		sb.append("\nradius : ");
//		sb.append(poiLocation.getRadius());
//		if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
//			sb.append("\naddr : ");
//			sb.append(poiLocation.getAddrStr());
//		}
//		if (poiLocation.hasPoi()) {
//			sb.append("\nPoi:");
//			sb.append(poiLocation.getPoi());
//		} else {
//			sb.append("noPoi information");
//		}
//		Log.e("t", sb.toString());
	}

	
}
