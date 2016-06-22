package com.app.server.util;

import org.json.JSONException;

import javapns.communication.exceptions.KeystoreException;
import javapns.devices.Device;
import javapns.devices.exceptions.InvalidDeviceTokenFormatException;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServer;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.PayloadPerDevice;
import javapns.notification.PushNotificationPayload;
import javapns.notification.transmission.NotificationThread;
import javapns.notification.transmission.NotificationThreads;

public class PushServiceInstance {

	private static final int POOL_MAX_THREAD = 1;
	private static String path = "D:\\littlebird.p12";
	private static String password = "littlebird";
//	private static String token =
//	"6bb7212a6622ab7957bc2351cc41f6d2783870030b29cf02f0e424eeb6541dfb";
	private static AppleNotificationServer server = null;
	private static NotificationThreads pool = null;

	public static NotificationThreads getNotificationThreadPool() throws JSONException, InvalidDeviceTokenFormatException, KeystoreException {
		if(server==null){
			server = new AppleNotificationServerBasicImpl(
					path, password, false);
		}
		
		if(pool ==null){
			pool = new NotificationThreads(server, 10)
			.start();
		}
		
			
			
//		try {
//			String token =
//					"6bb7212a6622ab7957bc2351cc41f6d2783870030b29cf02f0e424eeb6541dfb";
//			Device device = new BasicDevice(token);
//
//			System.out.println("Thread pool started and waiting...");
//
//			System.out.println("Sleeping 5 seconds before queuing payloads...");
//			// Thread.sleep(5 * 1000);
//
//			for (int i = 1; i <= 20; i++) {
//				PushNotificationPayload payload = PushNotificationPayload
//						.alert("中文测试 " + i);
//				payload.addBadge(1);
//				payload.addSound("default");
//				NotificationThread threadForPayload = (NotificationThread) pool
//						.add(new PayloadPerDevice(payload, device));
//				System.out.println("Queued payload " + i + " to "
//						+ threadForPayload.getThreadNumber());
//				System.out
//						.println("Sleeping 10 seconds before queuing another payload...");
//				// Thread.sleep(1 * 1000);
//			}
//			System.out
//					.println("Sleeping 10 more seconds let threads enough times to push the latest payload...");
//			Thread.sleep(10 * 1000);
//
//			for (int i = 1; i <= 20; i++) {
//				PushNotificationPayload payload = PushNotificationPayload
//						.alert("Test " + i);
//				payload.addBadge(1);
//				payload.addSound("default");
//				NotificationThread threadForPayload = (NotificationThread) pool
//						.add(new PayloadPerDevice(payload, device));
//				System.out.println("Queued payload " + i + " to "
//						+ threadForPayload.getThreadNumber());
//				System.out
//						.println("Sleeping 10 seconds before queuing another payload...");
//				// Thread.sleep(1 * 1000);
//			}
//			Thread.sleep(10 * 1000);
//
//			for (int i = 1; i <= 20; i++) {
//				PushNotificationPayload payload = PushNotificationPayload
//						.alert("测试 " + i);
//				payload.addBadge(1);
//				payload.addSound("default");
//				NotificationThread threadForPayload = (NotificationThread) pool
//						.add(new PayloadPerDevice(payload, device));
//				System.out.println("Queued payload " + i + " to "
//						+ threadForPayload.getThreadNumber());
//				System.out
//						.println("Sleeping 10 seconds before queuing another payload...");
//				// Thread.sleep(1 * 1000);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return pool;
	}

}
