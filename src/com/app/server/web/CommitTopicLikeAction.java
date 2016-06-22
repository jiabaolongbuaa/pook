package com.app.server.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.server.model.TopicFollowingModel;
import com.app.server.model.TopicLikeModel;
import com.app.server.model.TopicModel;
import com.app.server.model.UserInfoModel;

public class CommitTopicLikeAction extends AbstractAction {

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

		TopicLikeModel model = new TopicLikeModel();
		model.setCreator(userInfoModel);
		model.setLastUpdateTime(new Date());
		model.setTopicModel(topicModel);

		entityPersist.saveOrUpdate(model);

		return new ServerResponseBean(200,null);
	
	}

}
