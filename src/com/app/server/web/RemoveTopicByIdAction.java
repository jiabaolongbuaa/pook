package com.app.server.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.server.model.TopicModel;

public class RemoveTopicByIdAction extends AbstractAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
		String topicId = request.getParameter("topicId");
//		topicId ="3";

		TopicModel model = entityQueryFactory.createQuery(TopicModel.class)
				.eq("id", Integer.valueOf(topicId), false).get();
		entityPersist.remove(model);
		return new ServerResponseBean(200, null);
	}

}
