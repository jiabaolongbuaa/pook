package com.app.server.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.server.model.FriendRelationModel;
import com.app.server.model.UserInfoModel;
import com.mysql.jdbc.StringUtils;

public class AddFriendAction extends AbstractAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
		String userIdSrc = request.getParameter("userId");
		String friendIdSrc = request.getParameter("friendId");
		
		
		if(StringUtils.isEmptyOrWhitespaceOnly(userIdSrc)|| StringUtils.isEmptyOrWhitespaceOnly(friendIdSrc)){
			return new ServerResponseBean(1,null);
		}
		
		int userId = 0;
		int friendId = 0;
		
		try{
			userId = Integer.parseInt(userIdSrc);
			friendId = Integer.parseInt(friendIdSrc);
		}catch(Exception e){
			return new ServerResponseBean(2,null);
		}
		
		if(userId <=0 || friendId <=0){
			return new ServerResponseBean(3,null);
		}
		
		
		
		UserInfoModel friendUserInfoModel = entityQueryFactory
				.createQuery(UserInfoModel.class)
				.eq("id", friendId, true).get();
		
		if(friendUserInfoModel == null){
			return new ServerResponseBean(4,null);
		}
		
		
		
		FriendRelationModel model = entityQueryFactory.createQuery(FriendRelationModel.class).eq("userInfoModelId", userId, true).eq("friendInfoModel", friendUserInfoModel, true).get();
		
		if(model != null){
			return new ServerResponseBean(5,null);
		}
		
		
		model= new FriendRelationModel();
	

		model.setFriendInfoModel(friendUserInfoModel);
		model.setUserInfoModelId(userId);
		entityPersist.saveOrUpdate(model);
		return new ServerResponseBean(200,null);
	}
}
