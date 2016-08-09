package com.app.server.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.app.server.model.BlackListModel;
import com.app.server.model.CustomMessageContentModel;
import com.app.server.model.MessageModel;
import com.app.server.model.UserInfoModel;
import com.app.server.web.bean.MessageBean;

public class LoadCustomMessageAction extends AbstractAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {

		

		
		List<CustomMessageContentModel> messageList =  entityQueryFactory.createQuery(CustomMessageContentModel.class)
				.list();

		List<String> contents = new LinkedList<String>();
		

		for(CustomMessageContentModel message: messageList){
			contents.add(message.getContent());
		}
		
		
		JSONArray returnArray = JSONArray.fromObject(contents);
		ServerResponseBean returnObj = new ServerResponseBean(200, returnArray);
		return returnObj;
	}

}
