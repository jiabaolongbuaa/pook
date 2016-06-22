package com.app.server.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.app.server.model.FriendRelationModel;
import com.app.server.model.UserInfoModel;
import com.app.server.util.ConfigSingleton;
import com.app.server.util.MemcachedUtil;
import com.app.server.util.OpenFireUtils;
import com.app.server.web.bean.ConstantBean;
import com.app.server.web.bean.TopicBean;

public class TestAction extends AbstractAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
		ConstantBean helloBean = null;
		if (MemcachedUtil.get("ConstantBean") == null) {
			helloBean = ConfigSingleton.getInstance();
			System.out.println("get from file");
			MemcachedUtil.add("ConstantBean", helloBean);
		} else {
			helloBean = (ConstantBean) MemcachedUtil.get("ConstantBean");
			System.out.println("get from memcache");
		}

		// ApplicationContext context = new
		// ClassPathXmlApplicationContext("constantconfig.xml");
		// ConstantBean helloBean =
		// (ConstantBean)context.getBean("constantBean");
		System.out.println(helloBean.getOpenfireMySQLPassword());
		System.out.println(helloBean.getOpenfireMySQLUrl());
		System.out.println(helloBean.getAccPwd());
		System.out.println(helloBean.getUserIconLink());
		// JSONObject returnObject =
		// JSONObject.fromObject(helloBean.getOpenfireMySQLPassword());
		// ServerResponseBean returnObj = new ServerResponseBean(200,
		// returnObject);

		boolean isSuccess = OpenFireUtils.createUser("simon123", "simon123@me.com",
				"123456");

		if (isSuccess) {
			System.out.println("successfull");
			return new ServerResponseBean(200, null);
		}else{
			System.out.println("failed");
			return new ServerResponseBean(201,null);
		}
	}

}
