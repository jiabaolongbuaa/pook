package com.app.server.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.server.model.UserInfoModel;

public class CommitProfileAction extends AbstractAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {

		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");
		String gender = request.getParameter("gender");
		
		UserInfoModel model = entityQueryFactory
				.createQuery(UserInfoModel.class)
				.eq("id", Integer.parseInt(userId), false).get();
		model.setGender(Integer.parseInt(gender));
		model.setName(userName);
		entityPersist.saveOrUpdate(model);
		return new ServerResponseBean(200,null);
	}

}
