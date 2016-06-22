package com.app.server.util;

import org.json.JSONException;

import org.apache.log4j.*;

import javapns.Push;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.devices.Device;
import javapns.devices.Devices;
import javapns.devices.exceptions.InvalidDeviceTokenFormatException;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServer;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.Payload;
import javapns.notification.PayloadPerDevice;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;
import javapns.notification.transmission.NotificationThread;
import javapns.notification.transmission.NotificationThreads;
import javapns.notification.transmission.PushQueue;

public class TestPushPool {

	public void send(String token, Object keystore, String password,
			boolean production) throws KeystoreException,
			InvalidDeviceTokenFormatException {

		/* Prepare a simple payload to push */
		PushNotificationPayload payload = PushNotificationPayload
				.alert("Fuck the World!");
		payload.setCharacterEncoding("UTF-8");

		/* Decide how many threads you want your queue to use */
		int threads = 30;

		/* Create the queue */
		PushQueue queue = Push.queue(keystore, password, production, threads);

		/* Start the queue (all threads and connections and initiated) */
		// queue.start();

		/* Add a notification for the queue to push */
		for (int i = 0; i < 10; i++) {

			Device device = new BasicDevice();
			device.setToken(token);

			queue.add(payload, device);
			System.err.println("send!");
		}
		// queue.start();

	}

	public static void main(String[] args) throws CommunicationException,
			KeystoreException, JSONException, InvalidDeviceTokenFormatException {

		try {
	        BasicConfigurator.configure();
	 } catch (Exception e) {
	 }
	
		
//		String path = "D:\\littlebird.p12";
		String token = "6bb7212a6622ab7957bc2351cc41f6d2783870030b29cf02f0e424eeb6541dfb";
//		System.err.println(path);
//		System.err.println(token);
		
		NotificationThreads pool =PushServiceInstance.getNotificationThreadPool();
		pool.clearPushedNotifications();
////		pool.start();
		for (int i = 1; i <= 40; i++) {
			pool.clearPushedNotifications();
			PushNotificationPayload payload = PushNotificationPayload
					.alert("贾宝龙" + i);
			payload.addBadge(1);
			payload.addSound("default");
			Device device = new BasicDevice(token);
			NotificationThread threadForPayload = (NotificationThread) pool
					.add(new PayloadPerDevice(payload, device));
			System.out.println("Queued payload " + i + " to "
					+ threadForPayload.getThreadNumber());
			System.out
					.println("Sleeping 10 seconds before queuing another payload...");
			// Thread.sleep(1 * 1000);
		}
		
//		NotificationThreads pool2 =PushServiceInstance.getNotificationThreadPool();
//		
//		
//		for (int i = 1; i <= 40; i++) {
//			PushNotificationPayload payload = PushNotificationPayload
//					.alert("中文测试 " + i);
//			payload.addBadge(1);
//			payload.addSound("default");
//			Device device = new BasicDevice(token);
//			NotificationThread threadForPayload = (NotificationThread) pool2
//					.add(new PayloadPerDevice(payload, device));
//			System.err.println("Queued payload " + i + " to "
//					+ threadForPayload.getThreadNumber());
//			System.err
//					.println("Sleeping 10 seconds before queuing another payload...");
//			// Thread.sleep(1 * 1000);
//		}
//		
//		
		
			
//		try {
//			System.out.println("");
//			System.out.println("TESTING THREAD POOL FEATURE");
//
//			AppleNotificationServer server = new AppleNotificationServerBasicImpl(
//					path, "littlebird", false);
//			NotificationThreads pool = new NotificationThreads(server, 20)
//					.start();
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

	}
}
