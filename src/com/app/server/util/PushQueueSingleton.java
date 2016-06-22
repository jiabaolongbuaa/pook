package com.app.server.util;

import org.apache.log4j.BasicConfigurator;

import com.app.server.web.bean.ConstantBean;

import javapns.Push;
import javapns.communication.exceptions.KeystoreException;
import javapns.notification.transmission.PushQueue;

public class PushQueueSingleton {
	static String keystore ;// 证书路径和证书名
	static String password ; // 证书密码
	static boolean production; // 设置true为正式服务地址，false为开发者地址
	static int threads;

	// 私有的默认构造子
	private PushQueueSingleton() {
	}

	// 注意，这里没有final
	private static PushQueue queue = null;

	// 静态工厂方法
	public synchronized static PushQueue getInstance() {
		if (queue == null) {
			try {

				BasicConfigurator.configure();
				ConstantBean constant =ConfigSingleton.getInstance();
				
				keystore = constant.getKeystore();
				password=constant.getKeyPassword();
				production=Boolean.parseBoolean(constant.getIsProduction());
				threads=Integer.parseInt(constant.getThreadNumber());
				queue = Push.queue(keystore, password, production, threads);
			} catch (KeystoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			queue.start();
		}
		return queue;
	}

}
