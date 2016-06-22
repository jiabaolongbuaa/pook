package com.app.server.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import com.app.server.model.UserInfoModel;
import com.app.server.util.MD5Util;
import com.app.server.web.bean.UserInfoBean;
import com.mysql.jdbc.StringUtils;

public class LoginAction extends AbstractAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
		String email = request.getParameter("email");
		//String phonenum = request.getParameter("phonenum");
		String password = request.getParameter("password");
		String deviceToken = request.getParameter("deviceToken");

		if (null == password) {
			return new ServerResponseBean(102, null);
		}

		if (email == null ) {
			return new ServerResponseBean(103, null);
		}
		password = MD5Util.stringToMD5(password);

		UserInfoModel userInfoModel = null;

		if (!StringUtils.isEmptyOrWhitespaceOnly(email)&&email.contains("@")) {
			userInfoModel = entityQueryFactory.createQuery(UserInfoModel.class)
					.eq("email", email, true).eq("password", password, true)
					.get();
		} else if (!StringUtils.isEmptyOrWhitespaceOnly(email)) {
			userInfoModel = entityQueryFactory.createQuery(UserInfoModel.class)
					.eq("phonenum", email, true)
					.eq("password", password, true).get();
		}
		if (userInfoModel != null) {
			if (deviceToken != null) {
				deviceToken = deviceToken.replaceAll(" ", "");
				userInfoModel.setDeviceToken(deviceToken);
				entityPersist.saveOrUpdate(userInfoModel);
			}
			UserInfoBean userInfoBean = new UserInfoBean(userInfoModel);
			JSONObject returnObject = JSONObject.fromObject(userInfoBean);
			ServerResponseBean returnObj = new ServerResponseBean(200,
					returnObject);

			HttpSession session = request.getSession();
			session.setAttribute("user", userInfoModel.getId());
			return returnObj;
		}

		return new ServerResponseBean(101, null);
	}

}
