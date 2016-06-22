package com.app.server.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.app.server.model.BlackListModel;
import com.app.server.model.MessageModel;
import com.app.server.model.UserInfoModel;
import com.app.server.web.bean.MessageBean;

public class LoadMessagesAction extends AbstractAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {

		String userId = request.getParameter("userId");
		String friendId = request.getParameter("friendId");

		// ¹ýÂËblacklist
		List<BlackListModel> blackList = null;
		blackList = entityQueryFactory.createQuery(BlackListModel.class)
				.eq("userInfoModelId",Integer.parseInt(userId), false).list();

		UserInfoModel sender = new UserInfoModel();
		sender.setId(Integer.parseInt(userId));
		UserInfoModel receiver = new UserInfoModel();
		receiver.setId(Integer.parseInt(friendId));

		List<MessageModel> messageListAsSender = entityQueryFactory
				.createQuery(MessageModel.class).eq("sender", sender, false)
				.list();
		List<MessageModel> messageListAsReceiver = entityQueryFactory
				.createQuery(MessageModel.class)
				.eq("receiver", receiver, false).list();

		List<MessageBean> returnList = new ArrayList<MessageBean>();
		int flag = 0;
		for (int i = 0; i < messageListAsSender.size(); i++) {
			flag = 0;
			for (int j = 0; j < blackList.size(); j++) {
				if (messageListAsSender.get(i).getReceiver()
						.equals(blackList.get(j).getFriendInfoModel().getId())) {
					flag = 1;
					break;
				}
			}
			if (flag == 0)
				returnList.add(new MessageBean(messageListAsSender.get(i)));
		}

		for (int i = 0; i < messageListAsReceiver.size(); i++) {
			flag = 0;
			for (int j = 0; j < blackList.size(); j++) {
				if (messageListAsReceiver.get(i).getSender()
						.equals(blackList.get(j).getFriendInfoModel().getId())) {
					flag = 1;
					break;
				}
			}
			if (flag == 0)
				returnList.add(new MessageBean(messageListAsReceiver.get(i)));

		}

		messageListAsSender = entityQueryFactory
				.createQuery(MessageModel.class).eq("sender", receiver, false)
				.list();
		messageListAsReceiver = entityQueryFactory
				.createQuery(MessageModel.class).eq("receiver", sender, false)
				.list();

		for (int i = 0; i < messageListAsSender.size(); i++) {
			flag = 0;
			for (int j = 0; j < blackList.size(); j++) {
				if (messageListAsSender.get(i).getReceiver()
						.equals(blackList.get(j).getFriendInfoModel().getId())) {
					flag = 1;
					break;
				}
			}
			if (flag == 0)
				returnList.add(new MessageBean(messageListAsSender.get(i)));
		}
		for (int i = 0; i < messageListAsReceiver.size(); i++) {
			flag = 0;
			for (int j = 0; j < blackList.size(); j++) {
				if (messageListAsReceiver.get(i).getSender()
						.equals(blackList.get(j).getFriendInfoModel().getId())) {
					flag = 1;
					break;
				}
			}
			if (flag == 0)
				returnList.add(new MessageBean(messageListAsReceiver.get(i)));

		}

		Collections.sort(returnList, new Comparator() {
			public int compare(Object a, Object b) {
				int one = ((MessageBean) a).getId();
				int two = ((MessageBean) b).getId();
				return two - one;
			}
		});

		JSONArray returnArray = JSONArray.fromObject(returnList);
		ServerResponseBean returnObj = new ServerResponseBean(200, returnArray);
		return returnObj;
	}

}
