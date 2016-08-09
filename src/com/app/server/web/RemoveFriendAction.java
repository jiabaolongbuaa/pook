package com.app.server.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.app.server.constant.ResponseCode;
import com.app.server.model.FriendRelationModel;
import com.app.server.model.UserInfoModel;

public class RemoveFriendAction extends AbstractAction {

	static Logger logger = Logger.getLogger(RemoveFriendAction.class.getName());

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {

		UserInfoModel userId = user.get();

		String friendIdSrc = request.getParameter("friendId");

		if (StringUtils.isEmpty(friendIdSrc)) {
			return new ServerResponseBean(ResponseCode.POOK_PARAM_ERROR, null);
		}

		int friendId = 0;

		try {
			friendId = Integer.parseInt(friendIdSrc);
		} catch (Exception e) {
			return new ServerResponseBean(ResponseCode.POOK_PARAM_ERROR, null);
		}

		if (friendId <= 0) {
			return new ServerResponseBean(ResponseCode.POOK_PARAM_ERROR, null);
		}

		UserInfoModel friendUserInfoModel = entityQueryFactory
				.createQuery(UserInfoModel.class).eq("id", friendId, true)
				.get();

		if (friendUserInfoModel == null) {
			return new ServerResponseBean(
					ResponseCode.FRIEND_FRIENDID_NOT_EXITS, null);
		}

		FriendRelationModel model = entityQueryFactory
				.createQuery(FriendRelationModel.class)
				.eq("userInfoModelId", userId.getId(), true)
				.eq("friendInfoModel", friendUserInfoModel, true).get();

		if (model == null) {
			return new ServerResponseBean(
					ResponseCode.FRIEND_FRIENDID_NOT_FRIEND, null);
		}
		
		entityPersist.remove(model);

		return new ServerResponseBean(ResponseCode.POOK_SUCCESS, null);
	}

}
