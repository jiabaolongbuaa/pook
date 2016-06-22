package com.app.server.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.server.model.TopicLikeModel;
import com.app.server.model.TopicModel;
import com.app.server.model.UserInfoModel;

public class RemoveTopicLikeAction extends AbstractAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
		String topicId = request.getParameter("topicId");
		String userId = request.getParameter("userId");

		if (topicId == null || userId == null
				|| topicId.trim().equals("") || userId.trim().equals("")) {
			return new ServerResponseBean(0x0101, null);
		}
		UserInfoModel userInfoModel = entityQueryFactory
				.createQuery(UserInfoModel.class)
				.eq("id", Integer.parseInt(userId), true).get();
		TopicModel topicModel = entityQueryFactory
				.createQuery(TopicModel.class)
				.eq("id", Integer.parseInt(topicId), true).get();
		
		List<TopicLikeModel> modelList = entityQueryFactory.createQuery(TopicLikeModel.class)
				.eq("topicModel", topicModel, true)
				.eq("creator",userInfoModel, true)
				.list();
		
		for(TopicLikeModel model :modelList )
			entityPersist.remove(model);
		return new ServerResponseBean(200, null);
	}

}
