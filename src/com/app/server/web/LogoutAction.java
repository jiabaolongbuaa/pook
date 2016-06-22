package com.app.server.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.app.server.model.UserInfoModel;
import com.app.server.web.bean.UserInfoBean;

public class LogoutAction extends AbstractAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
		String userIdSrc = request.getParameter("userId");
		//String deviceToken = request.getParameter("deviceToken");

		int userId = 0;

		try {
			userId = Integer.parseInt(userIdSrc);
		} catch (Exception e) {
			return new ServerResponseBean(1, null);
		}

		UserInfoModel userInfoModel = entityQueryFactory
				.createQuery(UserInfoModel.class).eq("id", userId, false).get();
		if (userInfoModel != null) {
			userInfoModel.setDeviceToken("");
			entityPersist.saveOrUpdate(userInfoModel);
		}

		return new ServerResponseBean(200, null);

	}

}
