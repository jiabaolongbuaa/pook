package com.app.server.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.app.server.model.FriendRelationModel;
import com.app.server.model.UserInfoModel;
import com.app.server.web.bean.UserInfoBean;

public class LoadFollowersAction extends AbstractAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {

		UserInfoModel userId = user.get();
		// userId = "1";
		List<FriendRelationModel> relationModelList = entityQueryFactory
				.createQuery(FriendRelationModel.class)
				.eq("friendInfoModel", userId, true).list();
		List<UserInfoBean> returnList = new ArrayList<UserInfoBean>();
		for (int i = 0; i < relationModelList.size(); i++) {

			UserInfoModel friendModel = entityQueryFactory
					.createQuery(UserInfoModel.class)
					.eq("id", relationModelList.get(i).getUserInfoModelId(),
							true).get();
			if (friendModel == null) {
				continue;
			}
			UserInfoBean bean = new UserInfoBean(friendModel);
			bean.setIsBlocked(0);
			bean.setIsFriend(0);
			returnList.add(bean);
		}
		JSONArray returnArray = JSONArray.fromObject(returnList);
		ServerResponseBean returnObj = new ServerResponseBean(200, returnArray);
		return returnObj;
	}

}
