package com.app.server.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.server.model.MutedUserModel;

public class IsUserMutedAction extends AbstractAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
		String userId = request.getParameter("userId");

		if (userId == null || userId.trim().equals("")) {
			return new ServerResponseBean(0x0101, null);
		}

		MutedUserModel mutedUserModel = entityQueryFactory
				.createQuery(MutedUserModel.class).eq("userInfoModelId", Integer.parseInt(userId), true)
				.get();
		if (mutedUserModel == null) {
			return new ServerResponseBean(40004, null);
		}else{
			Date now = new Date();
			if(now.after(mutedUserModel.getExpireTime())){
				return new ServerResponseBean(40004, null);
			}
		}
		return new ServerResponseBean(200, null);
	}
}
