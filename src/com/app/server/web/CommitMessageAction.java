package com.app.server.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.server.model.MessageModel;
import com.app.server.model.UserInfoModel;

public class CommitMessageAction extends AbstractAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {

		String content = request.getParameter("content");
		String senderUserId = request.getParameter("senderUserId");
		String receiverUserId = request.getParameter("receiverUserId");
		
		
		MessageModel model = new MessageModel();
		model.setContent(content);
		model.setLastUpdateTime(String.valueOf(new Date().getTime()));
		model.setReceiver(new UserInfoModel(Integer.parseInt(receiverUserId)));
		model.setSender(new UserInfoModel(Integer.parseInt(senderUserId)));
		entityPersist.saveOrUpdate(model);

		return new ServerResponseBean(200,null);
	}

}
