package com.app.server.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.server.model.BlackListModel;
import com.app.server.model.FriendRelationModel;
import com.app.server.model.UserInfoModel;
import com.mysql.jdbc.StringUtils;

public class RemoveBlackListAction extends AbstractAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {

		String userIdSrc = request.getParameter("userId");
		String friendIdSrc = request.getParameter("friendId");

		if (StringUtils.isEmptyOrWhitespaceOnly(userIdSrc)
				|| StringUtils.isEmptyOrWhitespaceOnly(friendIdSrc)) {
			return new ServerResponseBean(1, null);
		}

		int userId = 0;
		int friendId = 0;

		try {
			userId = Integer.parseInt(userIdSrc);
			friendId = Integer.parseInt(friendIdSrc);
		} catch (Exception e) {
			return new ServerResponseBean(2, null);
		}

		if (userId <= 0 || friendId <= 0) {
			return new ServerResponseBean(3, null);
		}

		UserInfoModel friendUserInfoModel = entityQueryFactory
				.createQuery(UserInfoModel.class).eq("id", friendId, true)
				.get();

		if (friendUserInfoModel == null) {
			return new ServerResponseBean(4, null);
		}

		List<BlackListModel> models = entityQueryFactory
				.createQuery(BlackListModel.class)
				.eq("userInfoModelId", userId, true)
				.eq("friendInfoModel", friendUserInfoModel, true).list();

		if (models.size() <= 0) {
			return new ServerResponseBean(5, null);
		}

		for (BlackListModel model : models) {
			entityPersist.remove(model);
		}

		return new ServerResponseBean(200, null);
	}

}
