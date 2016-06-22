package com.app.server.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.app.server.model.TopicModel;
import com.app.server.model.UserInfoModel;
import com.app.server.web.bean.TopicBean;

public class LoadTopicsByUserAction extends AbstractAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
		String userId = request.getParameter("userId");
		
//		userId ="1";

		UserInfoModel userInfoModel = entityQueryFactory
				.createQuery(UserInfoModel.class)
				.eq("id", Integer.parseInt(userId), false).get();

		List<TopicModel> topicModelList = entityQueryFactory
				.createQuery(TopicModel.class)
				.eq("topicCreator", userInfoModel, false).desc("id", true)
				.list();

		List<TopicBean> returnList = new ArrayList<TopicBean>();
		for (int i = 0; i < topicModelList.size(); i++) {
			TopicModel model = topicModelList.get(i);
			TopicBean bean = new TopicBean(model);
			returnList.add(bean);
		}
		JSONArray returnArray = JSONArray.fromObject(returnList);
		ServerResponseBean returnObj = new ServerResponseBean(200, returnArray);
		return returnObj;
	}

}
