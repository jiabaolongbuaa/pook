package com.app.server.web;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.server.model.UserInfoModel;

public class IsEmailExistAction extends AbstractAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
		String email = request.getParameter("email");

		if (email == null || email.trim().equals("")) {
			return new ServerResponseBean(0x0101, null);
		}

		UserInfoModel userInfoModel = entityQueryFactory
				.createQuery(UserInfoModel.class).eq("email", email, true)
				.get();
		if (userInfoModel != null) {
			return new ServerResponseBean(40004, null);
		}
		return new ServerResponseBean(200, null);
	}
}
