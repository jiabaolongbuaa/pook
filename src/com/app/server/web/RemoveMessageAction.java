package com.app.server.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.server.model.MessageModel;


public class RemoveMessageAction extends AbstractAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

		String userId = request.getParameter("userId");
		String friendId = request.getParameter("friendId");

		// MessageModel model =
		// entityQueryFactory.createQuery(MessageModel.class)
		// .eq("id", Integer.valueOf(topicId), false).get();

		List<MessageModel> messages = entityQueryFactory
				.createQuery(MessageModel.class)
				.eq("sender", Integer.valueOf(userId), true)
				.eq("receiver", Integer.valueOf(friendId), true)
				.list();

		for (MessageModel message : messages)
			entityPersist.remove(message);
		return new ServerResponseBean(200, null);

	}

}
