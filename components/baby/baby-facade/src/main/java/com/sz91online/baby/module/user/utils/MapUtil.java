package com.sz91online.baby.module.user.utils;

import java.text.DecimalFormat;

public class MapUtil {
	public static double getLatLngDistance(LatLng start, LatLng end) {

		double lat1 = (Math.PI / 180) * start.latitude;
		double lat2 = (Math.PI / 180) * end.latitude;

		double lon1 = (Math.PI / 180) * start.longitude;
		double lon2 = (Math.PI / 180) * end.longitude;

		// double Lat1r = (Math.PI/180)*(gp1.getLatitudeE6()/1E6);
		// double Lat2r = (Math.PI/180)*(gp2.getLatitudeE6()/1E6);
		// double Lon1r = (Math.PI/180)*(gp1.getLongitudeE6()/1E6);
		// double Lon2r = (Math.PI/180)*(gp2.getLongitudeE6()/1E6);

		// 地球半径
		double R = 6371.004;

		// 两点间距离 m，如果想要米的话，结果*1000就可以了
		double dis = Math
				.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1)) * R;
		DecimalFormat nFormat = new DecimalFormat("######0.0"); // 数字格式化对象
		return  Double.parseDouble(nFormat.format(dis));

		/*
		 * if (dis < 1) { // 当小于1千米的时候用,用米做单位保留一位小数 nFormat.setMaximumFractionDigits(0);
		 * // 已可以设置为0，这样跟百度地图APP中计算的一样 dis *= 1000; return nFormat.format(dis) + "米"; }
		 * else { nFormat.setMaximumFractionDigits(1); return nFormat.format(dis) +
		 * "公里"; }
		 */

	}
	
	public static void main(String[] args) {
		LatLng a=new LatLng(0, 0);
		LatLng b=new LatLng(0.1, 0.1);
		System.out.println(getLatLngDistance(a,b));
	}
}
