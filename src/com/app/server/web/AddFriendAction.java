package com.app.server.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.app.server.constant.ResponseCode;
import com.app.server.model.FriendRelationModel;
import com.app.server.model.UserInfoModel;


public class AddFriendAction extends AbstractAction {

	static Logger logger = Logger.getLogger(AddFriendAction.class.getName());
	
	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {

		String friendIdSrc = request.getParameter("friendId");
		
		UserInfoModel userId = user.get();
		
		if(StringUtils.isEmpty(friendIdSrc)){
			logger.debug("friendIdSrc is empty : "+ResponseCode.FRIEND_FRIENDID_EMPTY);
			return new ServerResponseBean(ResponseCode.POOK_PARAM_ERROR,null);
		}

		int friendId = 0;
		
		try{
			friendId = Integer.parseInt(friendIdSrc);
		}catch(Exception e){
			logger.debug("friendId format error. friendId = "+ friendIdSrc +" : "+ResponseCode.FRIEND_FRIENDID_FORMAT_ERROR);
			return new ServerResponseBean(ResponseCode.POOK_PARAM_ERROR,null);
		}
		
		if(friendId <=0){
			logger.debug("friendId is less than 0. friendId = "+ friendId );
			return new ServerResponseBean(ResponseCode.POOK_PARAM_ERROR,null);
		}
		
		
		
		UserInfoModel friendUserInfoModel = entityQueryFactory
				.createQuery(UserInfoModel.class)
				.eq("id", friendId, true).get();
		
		if(friendUserInfoModel == null){
			logger.debug("friendId does not match any user. friendId = "+ friendId);
			return new ServerResponseBean(ResponseCode.FRIEND_FRIENDID_NOT_EXITS,null);
		}
	
		FriendRelationModel model = entityQueryFactory.createQuery(FriendRelationModel.class).eq("userInfoModelId", userId.getId(), true).eq("friendInfoModel", friendUserInfoModel, true).get();
		
		if(model != null){
			
			logger.debug("friendId "+friendId+" and "+userId.getId()+" are already friends");
			return new ServerResponseBean(ResponseCode.FRIEND_FRIENDID_ALREADY_FRIENDS,null);
		}
		
		
		model= new FriendRelationModel();
	

		model.setFriendInfoModel(friendUserInfoModel);
		model.setUserInfoModelId(userId.getId());
		entityPersist.saveOrUpdate(model);
		return new ServerResponseBean(200,null);
	}
}
