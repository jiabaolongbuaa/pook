package com.app.server.web;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.server.model.UserInfoModel;
/**
 * 
 * 判断电话是否注册
 * 
 * @author Simon
 *
 */
public class IsPhoneExistAction extends AbstractAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
		String email = request.getParameter("phone");

		if (email == null || email.trim().equals("")) {
			return new ServerResponseBean(0x0101, null);
		}

		UserInfoModel userInfoModel = entityQueryFactory
				.createQuery(UserInfoModel.class).eq("phonenum", email, true)
				.get();
		if (userInfoModel != null) {
			return new ServerResponseBean(40004, null);
		}
		return new ServerResponseBean(200, null);
	}
}
