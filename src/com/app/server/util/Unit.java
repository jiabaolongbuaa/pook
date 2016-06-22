package com.app.server.util;


public class Unit {

	/**
	 * 
	 * @param latitude  纬度
	 * @param longitude  经度
	 * @param raidus  半径 单位米
	 * @return  返回数组 分别为    左上角x坐标 ，y坐标    右下角x坐标，y坐标
	 */
	  public static double[] getAround(double latitude, double longitude, double raidus) {

	      Double degree = (24901 * 1609) / 360.0;
	      double raidusMile = raidus;

	      Double dpmLat = 1 / degree;
	      Double radiusLat = dpmLat * raidusMile;
	      Double minLat = latitude - radiusLat;
	      Double maxLat = latitude + radiusLat;

	      Double mpdLng = degree * Math.cos(latitude * (Math.PI / 180));
	      Double dpmLng = 1 / mpdLng;
	      Double radiusLng = dpmLng * raidusMile;
	      Double minLng = longitude - radiusLng;
	      Double maxLng = longitude + radiusLng;
	      return new double[]{minLat, minLng, maxLat, maxLng};
	  }

	
	public static void main(String[] args){
		double[] a=null;
		a=getAround(39.90960456049752, 116.3972282409668, 500);
		for (double d : a) 
		System.out.println(d);
	}
}
