package com.app.server.util;

public class DistanceCalculator {

	//单位为米
	public static double getDistance(double lng1,double lat1,double lng2,double lat2){

		double radLat1 = lat1 * Math.PI / 180;
        double radLat2 = lat2 * Math.PI / 180;
        double a = radLat1 - radLat2;
        double b = lng1 * Math.PI / 180 - lng2 * Math.PI / 180;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1)
                * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * 6378137.0;// ȡWGS84��׼�ο������еĵ��򳤰뾶(��λ:m)
        s = Math.round(s * 10000) / 10000;
        
       

        return s;
		

	}
	
	public static void main(String[] argv){
		System.out.println(getDistance(121,31,121.4,31.2));
	}
}
