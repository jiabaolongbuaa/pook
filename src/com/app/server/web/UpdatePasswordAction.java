package com.app.server.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.server.model.UserInfoModel;
import com.app.server.util.OpenfireRegistrationAgent;

public class UpdatePasswordAction extends AbstractAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
		String oldPassword = request.getParameter("oldPassword");
		String userId = request.getParameter("userId");
		String newPassword = request.getParameter("newPassword");
		
//		oldPassword ="880717";
//		userId ="1";
//		newPassword ="1234567";

		UserInfoModel model = entityQueryFactory
				.createQuery(UserInfoModel.class)
				.eq("id", Integer.parseInt(userId), false).get();

		if (!model.getPassword().equals(oldPassword)) {
			//40008: user old password error
			return new ServerResponseBean(40008, null);
		}

		model.setPassword(newPassword);
		entityPersist.saveOrUpdate(model);
		OpenfireRegistrationAgent ra =new OpenfireRegistrationAgent(String.valueOf(model.getId()),newPassword);
		ra.updatePassword();
		return new ServerResponseBean(200, null);
	}

}
