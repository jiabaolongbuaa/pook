package com.app.server.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.app.server.model.UserImageModel;
import com.app.server.model.UserInfoModel;
import com.app.server.web.bean.UserIconBean;
import com.app.server.web.bean.UserImageBean;


public class LoadAvatarAction extends AbstractAction{

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		String userId = request.getParameter("userId");
		
		UserInfoModel userInfoModel =entityQueryFactory.createQuery(UserInfoModel.class).eq("id", Integer.parseInt(userId), false).get();
		
		UserIconBean bean = new UserIconBean(userInfoModel);
		
		JSONObject returnObject = JSONObject.fromObject(bean);
		ServerResponseBean returnObj = new ServerResponseBean(200,
				returnObject);
		return returnObj;
	}

}
