package com.app.server.util;

import java.io.IOException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;


public class SMSService {

	private static String id ;
	private static String username ;
	private static String password;
	private static String host ;
	private static String content ;
	
	private static String MD5Encode(String content){
		String signStr = null;
		
		try{
			byte[] bytes =content.getBytes("utf-8");
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(bytes);
			byte[] md5Byte =md5.digest();
			if(md5Byte!= null){
				signStr =	HexBin.encode(md5Byte);
			}
			
		}catch(Exception e){
			
		}
		return signStr;
	}

	public static boolean sendSMS(String phonenum, String code) {

		
		//ConstantBean constant = ConfigSingleton.getInstance();
		id = ConfigSingleton.getInstance().getAccId();
		username = ConfigSingleton.getInstance().getAccName();
		password = ConfigSingleton.getInstance().getAccPwd();
		host = ConfigSingleton.getInstance().getHost();
		content = ConfigSingleton.getInstance().getContent();
		HttpClient httpclient = new DefaultHttpClient();

		
		System.err.println(id);
		System.err.println(username);
		System.err.println(password);
		System.err.println(host);
		System.err.println(content);
		// 你的URL
		HttpPost httppost = new HttpPost(host);

		String fmt = "";
		fmt = "yyyymmddhhmmss";
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		String dateStr = sdf.format(new Date());
		System.out.println(dateStr);
		try {

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

			nameValuePairs.add(new BasicNameValuePair("accId", id));

			nameValuePairs.add(new BasicNameValuePair("accName", username));
			nameValuePairs.add(new BasicNameValuePair("accPwd", MD5Encode("yototem1987").toUpperCase()));
			nameValuePairs.add(new BasicNameValuePair("aimcodes", phonenum));
			String contentToSend = content.replace("[code]", code);
			nameValuePairs
					.add(new BasicNameValuePair("content", contentToSend));

			nameValuePairs.add(new BasicNameValuePair("bizId", dateStr));
			nameValuePairs.add(new BasicNameValuePair("dataType", "string"));

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,
					HTTP.UTF_8));

			HttpResponse response;
			response = httpclient.execute(httppost);

			String strResult = EntityUtils.toString(response.getEntity());
			System.err.println(strResult);

		} catch (ClientProtocolException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		} catch (IOException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		return true;
	}

	public static void main(String[] args) {

		sendSMS("18910830717", "6666");
	}
}
