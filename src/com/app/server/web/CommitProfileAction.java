package com.app.server.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.server.model.UserInfoModel;

public class CommitProfileAction extends AbstractAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {

		UserInfoModel userId = user.get();
		

		String userName = request.getParameter("userName");
		String gender = request.getParameter("gender");
		
		
		userId.setGender(Integer.parseInt(gender));
		userId.setName(userName);
		entityPersist.saveOrUpdate(userId);
		return new ServerResponseBean(200,null);
	}

}
