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
import com.app.server.model.RemarkModel;
import com.app.server.model.ReportModel;
import com.app.server.model.UserInfoModel;

public class CommitRemarkAction extends AbstractAction {

	static Logger logger = Logger.getLogger(CommitRemarkAction.class
			.getName());

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

		UserInfoModel userId = user.get();

		String friendIdSrc = request.getParameter("friendId");
		String remark = request.getParameter("remark");
		

		logger.debug("friendId : " + friendIdSrc);
		logger.debug("remark : " + remark);

		if (StringUtils.isEmpty(friendIdSrc)) {
			logger.debug("friendIdSrc or remark is empty");
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

		RemarkModel model = entityQueryFactory
				.createQuery(RemarkModel.class)
				.eq("userInfoModelId", userId.getId(), true)
				.eq("friendInfoModel", friendUserInfoModel, true).get();

		if (model != null) {
			
			if(!remark.equals(model.getRemark())){
				model.setRemark(remark);
				entityPersist.saveOrUpdate(model);
			}
			
			return new ServerResponseBean(ResponseCode.POOK_SUCCESS, null);
		}
		model = new RemarkModel();

		model.setFriendInfoModel(friendUserInfoModel);
		model.setUserInfoModelId(userId.getId());
		model.setRemark(remark);
		entityPersist.saveOrUpdate(model);
		
		
		return new ServerResponseBean(ResponseCode.POOK_SUCCESS, null);
	}

}
