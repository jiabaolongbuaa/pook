package com.app.server.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.app.server.web.bean.ConstantBean;

public class ConfigSingleton {

	// 私有的默认构造子
	private ConfigSingleton() {
	}

	// 注意，这里没有final
	private static ConstantBean constantBean = null;

	// 静态工厂方法
	public synchronized static ConstantBean getInstance() {
		if (constantBean == null) {
				ApplicationContext context = new ClassPathXmlApplicationContext("constantconfig.xml");   
				constantBean = (ConstantBean)context.getBean("constantBean");  
		}
		return constantBean;
	}

}
