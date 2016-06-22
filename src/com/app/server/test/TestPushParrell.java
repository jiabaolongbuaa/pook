package com.app.server.test;

import java.util.ArrayList;
import java.util.List;

import javapns.Push;
import javapns.communication.exceptions.KeystoreException;
import javapns.devices.exceptions.InvalidDeviceTokenFormatException;
import javapns.notification.AppleNotificationServer;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.PayloadPerDevice;
import javapns.notification.PushNotificationPayload;
import javapns.notification.transmission.NotificationThreads;
import javapns.notification.transmission.PushQueue;
import org.apache.log4j.*;

import com.app.server.util.PushQueueSingleton;
import com.app.server.util.StringUtils;

public class TestPushParrell {

	// public void send(String token, Object keystore, String password,
	// boolean production) {
	//
	// /* Prepare a simple payload to push */
	// PushNotificationPayload payload = PushNotificationPayload
	// .alert("Hello World!");
	//
	// /* Decide how many threads you want your queue to use */
	// int threads = 30;
	//
	// /* Create the queue */
	// PushQueue queue;
	// try {
	// queue = Push.queue(keystore, password, production, threads);
	//
	// /* Start the queue (all threads and connections and initiated) */
	// queue.start();
	//
	// /* Add a notification for the queue to push */
	// queue.add(payload, token);
	// } catch (KeystoreException | InvalidDeviceTokenFormatException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// }

	public static void main(String[] args) throws InterruptedException {
		
		String email = "wish@126.com";
		System.out.println(StringUtils.isEmail(email));
		
////		 try {
////		        BasicConfigurator.configure();
////		 } catch (Exception e) {
////		 }
////		String keystore = "D:/littlebird.p12";// 证书路径和证书名
////		String password = "littlebird"; // 证书密码
//		String token = "80ccd1294b9f15d87e541e6567abf711a8d4ce06e395e5309243212da52c097f";// 手机唯一标识
////		boolean production = false; // 设置true为正式服务地址，false为开发者地址
////		int threads = 10;
////		PushQueue queue = null;
////		try {
////			queue = Push.queue(keystore, password, production, threads);
////			queue.start();
////		} catch (KeystoreException e1) {
////			// TODO Auto-generated catch block
////			e1.printStackTrace();
////		}
//		for (int i = 0; i < 100; i++) {
//			System.out.println(i);
//			/* Prepare a simple payload to push */
//			PushNotificationPayload payload = PushNotificationPayload
//					.alert("Hello World!" + i);
//			PushQueue queue=PushQueueSingleton.getInstance();
//			/* Decide how many threads you want your queue to use */
//			try {
//				queue.add(payload,token);
//			} catch (InvalidDeviceTokenFormatException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			/* Create the queue */
//
////			try {
////
////				/* Start the queue (all threads and connections and initiated) */
////
////				/* Add a notification for the queue to push */
////				queue.add(payload, token);
////			} catch (InvalidDeviceTokenFormatException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
////			Thread.sleep(1000);
//		}
//		Thread.sleep(1000000000);
	}
}
