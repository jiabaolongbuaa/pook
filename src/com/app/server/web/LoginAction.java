package com.app.server.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import net.sf.json.JSONObject;

import com.app.server.model.UserInfoModel;
import com.app.server.util.MD5Util;
import com.app.server.util.SimonUtils;
import com.app.server.web.bean.UserInfoBean;

public class LoginAction extends AbstractNoneStateAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
		String email = request.getParameter("phone");
		// String phonenum = request.getParameter("phonenum");
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		String deviceToken = request.getParameter("deviceToken");

		if (null == password) {
			return new ServerResponseBean(102, null);
		}

		if (StringUtils.isEmpty(email) && StringUtils.isEmpty(userName)) {
			return new ServerResponseBean(103, null);
		}
		password = SimonUtils.MD5Encode(password);// MD5Util.stringToMD5(password);

		UserInfoModel userInfoModel = null;

		if (StringUtils.isEmpty(userName)) {

			if (!StringUtils.isEmpty(email) && email.contains("@")) {
				userInfoModel = entityQueryFactory
						.createQuery(UserInfoModel.class)
						.eq("email", email, true)
						.eq("password", password, true).get();
			} else if (!StringUtils.isEmpty(email)) {
				userInfoModel = entityQueryFactory
						.createQuery(UserInfoModel.class)
						.eq("phonenum", email, true)
						.eq("password", password, true).get();
			}
		} else {
			userInfoModel = entityQueryFactory
					.createQuery(UserInfoModel.class)
					.eq("name", userName, true)
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
			session.setAttribute("user", userInfoModel);
			dealwithResponse(response, session, userInfoModel);
			return returnObj;
		}

		return new ServerResponseBean(101, null);
	}

	private void dealwithResponse(HttpServletResponse response,
			HttpSession session, UserInfoModel userInfoModel) {

		Cookie cookie1 = new Cookie("JSESSIONID", session.getId());
		cookie1.setMaxAge(365 * 24 * 3600);
		cookie1.setPath("/");

		Cookie cookie2 = new Cookie("user", String.valueOf(userInfoModel
				.getId()));
		cookie2.setMaxAge(365 * 24 * 3600);
		cookie2.setPath("/");

		response.addCookie(cookie1);
		response.addCookie(cookie2);
		session.setAttribute("user", userInfoModel);
	}

}
