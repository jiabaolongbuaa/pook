package com.app.server.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.app.server.model.MessageModel;
import com.app.server.model.UserInfoModel;
import com.app.server.web.bean.MessageBean;

public class LoadMessageByUserIdAction extends AbstractAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String userId = request.getParameter("userId");

		UserInfoModel sender = new UserInfoModel();
		sender.setId(Integer.parseInt(userId));
		UserInfoModel receiver = new UserInfoModel();
		receiver.setId(Integer.parseInt(userId));
		
		
		List<MessageModel> messageListAsSender = entityQueryFactory
				.createQuery(MessageModel.class).eq("sender", sender, false).desc("id", true)
				.list();
		List<MessageModel> messageListAsReceiver = entityQueryFactory
				.createQuery(MessageModel.class).desc("id", true)
				.eq("receiver", receiver, false).list();

		List<MessageBean> returnList = new ArrayList<MessageBean>();
		for (int i = 0; i < messageListAsSender.size(); i++) {
			returnList.add(new MessageBean(messageListAsSender.get(i)));
		}
		for (int i = 0; i < messageListAsReceiver.size(); i++) {
			returnList.add(new MessageBean(messageListAsReceiver.get(i)));
		}

		JSONArray returnArray = JSONArray.fromObject(returnList);
		ServerResponseBean returnObj = new ServerResponseBean(200, returnArray);
		return returnObj;
		
		
	}

}
