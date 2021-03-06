package com.app.server.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.app.server.constant.ResponseCode;
import com.app.server.model.BlackListModel;
import com.app.server.model.MutedUserModel;
import com.app.server.model.UserInfoModel;

public class CommitBlackListAction extends AbstractAction {

	static Logger logger = Logger.getLogger(CommitBlackListAction.class
			.getName());

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

		UserInfoModel userId = user.get();

		String friendIdSrc = request.getParameter("friendId");

		logger.debug("friendId : " + friendIdSrc);

		if (StringUtils.isEmpty(friendIdSrc)) {
			logger.debug("friendIdSrc is empty");
			return new ServerResponseBean(ResponseCode.POOK_PARAM_ERROR, null);
		}

		int friendId = 0;

		try {
			friendId = Integer.parseInt(friendIdSrc);
		} catch (Exception e) {
			logger.debug("friendId format error");
			return new ServerResponseBean(ResponseCode.POOK_PARAM_ERROR, null);
		}

		if (friendId <= 0) {
			logger.debug("friendId less than 0");
			return new ServerResponseBean(ResponseCode.POOK_PARAM_ERROR, null);
		}

		UserInfoModel friendUserInfoModel = entityQueryFactory
				.createQuery(UserInfoModel.class).eq("id", friendId, true)
				.get();

		if (friendUserInfoModel == null) {
			logger.debug("friendId does not match any user");
			return new ServerResponseBean(
					ResponseCode.FRIEND_FRIENDID_NOT_EXITS, null);
		}

		BlackListModel model = entityQueryFactory
				.createQuery(BlackListModel.class)
				.eq("userInfoModelId", userId.getId(), true)
				.eq("friendInfoModel", friendUserInfoModel, true).get();

		if (model != null) {
			logger.debug("friendId " + friendId + "already exists in "
					+ userId.getId() + "'s blacklist");
			return new ServerResponseBean(ResponseCode.FRIEND_FRIENDID_ALREADY_IN_BLACKLIST, null);
		}
		model = new BlackListModel();

		model.setFriendInfoModel(friendUserInfoModel);
		model.setUserInfoModelId(userId.getId());
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
					.eq("userInfoModelId", friendId, true).get();
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
		return new ServerResponseBean(ResponseCode.POOK_SUCCESS, null);
	}

}
