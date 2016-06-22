package com.app.server.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.app.server.model.TopicFollowingModel;
import com.app.server.model.TopicModel;
import com.app.server.web.bean.TopicFollowingBean;

public class LoadTopicFollowingsAction extends AbstractAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
		String topicId = request.getParameter("topicId");
		
		TopicModel topicModel = entityQueryFactory
				.createQuery(TopicModel.class)
				.eq("id", Integer.parseInt(topicId), false).get();

		List<TopicFollowingModel> topicFollowings = topicModel
				.getFollowingList();
		List<TopicFollowingBean> returnList = new ArrayList<TopicFollowingBean>();
		for (int i = 0; i < topicFollowings.size(); i++) {
			TopicFollowingBean bean = new TopicFollowingBean(
					topicFollowings.get(i));
			returnList.add(bean);
		}

		JSONArray returnArray = JSONArray.fromObject(returnList);
		ServerResponseBean returnObj = new ServerResponseBean(200, returnArray);
		return returnObj;
	}

}
