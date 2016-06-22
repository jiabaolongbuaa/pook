package com.app.server.web;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.server.model.TopicLikeModel;
import com.app.server.model.UserInfoModel;

public class IsTopicLikedAction extends AbstractAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
		
		String topicId = request.getParameter("topicId");
		String userId = request.getParameter("userId");
		
		if (topicId == null || userId == null
				|| topicId.trim().equals("") || userId.trim().equals("")) {
			return new ServerResponseBean(0x0101, null);
		}
		
		TopicLikeModel topicLikeModel = entityQueryFactory
				.createQuery(TopicLikeModel.class).eq("topicId", Integer.parseInt(topicId), true)
				.eq("userInfoId", Integer.parseInt("userId"), true)
				.get();
		
		if (topicLikeModel != null) {
			return new ServerResponseBean(40004, null);
		}
		return new ServerResponseBean(200,null);
	}
}
