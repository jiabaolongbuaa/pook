package com.app.server.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.app.server.model.TopicModel;
import com.app.server.model.UserInfoModel;
import com.app.server.web.bean.TopicBean;

public class CommitTopicAction extends AbstractAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
		String userId = request.getParameter("userId");
		String longitude = request.getParameter("longitude");
		String latitude = request.getParameter("latitude");
		String content = request.getParameter("content");
		String hideUser = request.getParameter("hideUser");
		String type = request.getParameter("type");
		
		if (longitude == null || latitude == null
				|| longitude.trim().equals("") || latitude.trim().equals("")) {
			return new ServerResponseBean(0x0101, null);
		}
		
		
		TopicModel topicModel = new TopicModel();
		topicModel.setContent(content);
		topicModel.setLastUpdateTime(new Date());
		topicModel.setLatitude(Float.parseFloat(latitude));
		topicModel.setLongitude(Float.parseFloat(longitude));
		topicModel.setHideUser(Boolean.valueOf(hideUser));
		if(type== null){
			topicModel.setType(0);
		}else{
			topicModel.setType(Integer.parseInt(type));
		}
		topicModel.setTopicCreator(entityQueryFactory
				.createQuery(UserInfoModel.class)
				.eq("id", Integer.parseInt(userId), false).get());

		entityPersist.saveOrUpdate(topicModel);
		return new ServerResponseBean(200, JSONObject.fromObject(new TopicBean(topicModel)));
	}

}
