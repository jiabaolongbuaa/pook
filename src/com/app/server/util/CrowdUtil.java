package com.app.server.util;

public class CrowdUtil {
	/**
	 * 
	 * @param latitude
	 *            纬度
	 * @param longitude
	 *            经度
	 * @param raidus
	 *            半径 单位米
	 * @return 返回数组 分别为 左上角x坐标 ，y坐标 右下角x坐标，y坐标
	 */
	public static float[] getAround(double latitude, double longitude,
			double raidus) {

		double degree = (24901 * 1609) / 360.0;
		double raidusMile = raidus;

		double dpmLat = 1 / degree;
		double radiusLat = dpmLat * raidusMile;
		double minLat = latitude - radiusLat;
		double maxLat = latitude + radiusLat;

		double mpdLng = degree * Math.cos(latitude * (Math.PI / 180));
		double dpmLng = 1 / mpdLng;
		double radiusLng = dpmLng * raidusMile;
		double minLng = longitude - radiusLng;
		double maxLng = longitude + radiusLng;
		return new float[] { (float)minLat,(float) minLng, (float)maxLat, (float)maxLng };
	}

	public static float getMinLatitude(double latitude, double longitude,
			double raidus) {

		double degree = (24901 * 1609) / 360.0;
		double raidusMile = raidus;
		double dpmLat = 1 / degree;
		double radiusLat = dpmLat * raidusMile;
		double minLat = latitude - radiusLat;
		return (float)minLat;
	}

	public static float getMaxLatitude(double latitude, double longitude,
			double raidus) {

		double degree = (24901 * 1609) / 360.0;
		double raidusMile = raidus;
		double dpmLat = 1 / degree;
		double radiusLat = dpmLat * raidusMile;
		double maxLat = latitude + radiusLat;
		return (float)maxLat;
	}

	public static float getMinLongitude(double latitude, double longitude,
			double raidus) {

		double degree = (24901 * 1609) / 360.0;
		double raidusMile = raidus;
		double mpdLng = degree * Math.cos(latitude * (Math.PI / 180));
		double dpmLng = 1 / mpdLng;
		double radiusLng = dpmLng * raidusMile;
		double minLng = longitude - radiusLng;
		return (float)minLng;
	}

	public static float getMaxLongitude(double latitude, double longitude,
			double raidus) {

		double degree = (24901 * 1609) / 360.0;
		double raidusMile = raidus;
		double mpdLng = degree * Math.cos(latitude * (Math.PI / 180));
		double dpmLng = 1 / mpdLng;
		double radiusLng = dpmLng * raidusMile;
		double maxLng = longitude + radiusLng;
		return (float)maxLng;
	}
}
