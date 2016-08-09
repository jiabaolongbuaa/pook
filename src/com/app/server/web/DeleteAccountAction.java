package com.app.server.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import com.app.server.model.ActiveCodeModel;
import com.app.server.model.UserInfoModel;
import com.app.server.util.MD5Util;
import com.app.server.util.OpenFireUtils;
import com.app.server.util.SimonUtils;
import com.app.server.web.bean.UserInfoBean;
import com.mysql.jdbc.StringUtils;

public class DeleteAccountAction extends AbstractNoneStateAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
		String email = request.getParameter("phone");
		String password = request.getParameter("password");


		if (null == password) {
			return new ServerResponseBean(102, null);
		}

		if (email == null ) {
			return new ServerResponseBean(103, null);
		}
		password = SimonUtils.MD5Encode(password);//MD5Util.stringToMD5(password);
		
		
		ActiveCodeModel code = entityQueryFactory
				.createQuery(ActiveCodeModel.class)
				.eq("phonenum", email, true).get();

		if (code != null) {
			entityPersist.remove(code);
		}

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
			
			entityPersist.remove(userInfoModel);
			
			OpenFireUtils.deleteUser(email);
			return new ServerResponseBean(200, null);
		}

		return new ServerResponseBean(101, null);
	}
	


}
