package com.app.server.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.server.model.BlackListModel;
import com.app.server.model.FriendRelationModel;
import com.app.server.model.MutedUserModel;
import com.app.server.model.UserInfoModel;
import com.mysql.jdbc.StringUtils;

public class CommitBlackListAction extends AbstractAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

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
		
		if(friendUserInfoModel== null){
			return new ServerResponseBean(4, null);
		}
		
		BlackListModel model = entityQueryFactory
				.createQuery(BlackListModel.class).eq("userInfoModelId", userId, true).eq("friendInfoModel", friendUserInfoModel, true)
				.get();
		
		if(model != null){
			return new ServerResponseBean(5, null);
		}
		model = new BlackListModel();

		model.setFriendInfoModel(friendUserInfoModel);
		model.setUserInfoModelId(userId);
		model.setCommitTime(new Date());
		entityPersist.saveOrUpdate(model);
		Date now = new Date();
		Date before = new Date(now.getTime() - 1 * 60 * 60 * 1000);

		List<BlackListModel> blackListModelList = entityQueryFactory
				.createQuery(BlackListModel.class)
				.eq("friendInfoModel", friendUserInfoModel, false)
				.between("commitTime", before, now).list();
		if (blackListModelList.size() >= 3) {
			MutedUserModel mutedUserModel = entityQueryFactory
					.createQuery(MutedUserModel.class)
					.eq("userInfoModelId", friendId, true)
					.get();
			if (mutedUserModel == null) {
				mutedUserModel = new MutedUserModel();
				mutedUserModel.setUserInfoModelId(friendId);
				mutedUserModel.setMutedTimes(1);
				Date presentTime = new Date();
				Date expireTime = new Date(presentTime.getTime() + 1 * 60 * 60
						* 1000);
				mutedUserModel.setExpireTime(expireTime);
				entityPersist.saveOrUpdate(mutedUserModel);
			} else {
				mutedUserModel
						.setMutedTimes(mutedUserModel.getMutedTimes() + 1);
				Date presentTime = new Date();
				Date expireTime = new Date(presentTime.getTime() + 1 * 60 * 60
						* 1000);
				mutedUserModel.setExpireTime(expireTime);
				entityPersist.saveOrUpdate(mutedUserModel);
			}
		}
		return new ServerResponseBean(200, null);
	}

}
