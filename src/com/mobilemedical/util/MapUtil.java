package com.mobilemedical.util;

import java.util.List;

import com.baidu.mapapi.map.Geometry;
import com.baidu.mapapi.map.Graphic;
import com.baidu.mapapi.map.GraphicsOverlay;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Symbol;
import com.baidu.mapapi.map.TextItem;
import com.baidu.mapapi.map.TextOverlay;
import com.baidu.mapapi.map.Symbol.Color;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.mobilemedical.entity.Userpoint;

public class MapUtil {

	public static void addLins(MapView mMapView, List<Userpoint> userpoint) {
		Geometry g = new Geometry();
		GeoPoint[] geoPoints = new GeoPoint[userpoint.size()];
		for (int i = 0; i < userpoint.size(); i++) {
			String pointinfo = userpoint.get(i).getPointinfo();
			String[] infoArray = pointinfo.split(",");
			geoPoints[i] = new GeoPoint(
					(int) (Double.parseDouble(infoArray[1]) * 1E6),
					(int) (Double.parseDouble(infoArray[0]) * 1E6));
		}
		g.setPolyLine(geoPoints);

		Symbol lineSymbol = new Symbol();
		Symbol.Color lineColor = lineSymbol.new Color();
		lineColor.red = 150;
		lineColor.green = 0;
		lineColor.blue = 150;
		lineColor.alpha = 50;

		lineSymbol.setLineSymbol(lineColor, 5);
		Graphic palaceGraphic = new Graphic(g, lineSymbol);
		GraphicsOverlay palaceOverlay = new GraphicsOverlay(mMapView);
		palaceOverlay.setData(palaceGraphic);
		mMapView.getOverlays().add(palaceOverlay);
		// 刷新地图使新添加的overlay生效
		mMapView.refresh();
	}

	public static void addText(MapView mMapView, GeoPoint geoPoint, String text) {
		
		Symbol sf = new Symbol();
		
		Symbol.Color tcolor = sf.new Color();  
		tcolor.alpha = 150;  
		tcolor.red = 80;  
		tcolor.blue = 80;  
		tcolor.green = 80;  
		
		TextItem textItem = new TextItem();
		textItem.pt = geoPoint;
		textItem.text = text;
		textItem.fontColor = tcolor;
		textItem.fontSize = 12;
		
		TextOverlay textOverlay = new TextOverlay(mMapView);
		mMapView.getOverlays().add(textOverlay);
		textOverlay.addText(textItem);
		mMapView.refresh();
	}
	
}
