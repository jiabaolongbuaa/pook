package com.app.server.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternValidation {

	/*
	 *  移动:
     2G号段(GSM网络)有139,138,137,136,135,134(0-8),159,158,152,151,150
     3G号段(TD-SCDMA网络)有157,188,187
     147是移动TD上网卡专用号段.
 联通:
     2G号段(GSM网络)有130,131,132,155,156
     3G号段(WCDMA网络)有186,185
 电信:
     2G号段(CDMA网络)有133,153
     3G号段(CDMA网络)有189,180
	 * 
	 */
	 public static boolean isMobileNO(String mobiles){     
	        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");     
	        Matcher m = p.matcher(mobiles);     
	        return m.matches(); 
	 }
}
