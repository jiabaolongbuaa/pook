package com.app.server.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.app.server.model.UserInfoModel;

public class LogoutAction extends AbstractAction {

	static Logger logger = Logger.getLogger(LogoutAction.class.getName());
	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
		UserInfoModel userId = user.get();


		HttpSession session = request.getSession(false);
		if (session == null) {
			return new ServerResponseBean(101, null);
		}

		userId.setDeviceToken("");
		entityPersist.saveOrUpdate(userId);
		
		try {
			session.invalidate();
		} catch (IllegalStateException e) {
			logger.info("Session already invalidated");
		}

		logger.info("logout successfully");
		return new ServerResponseBean(200, null);

	}

}
