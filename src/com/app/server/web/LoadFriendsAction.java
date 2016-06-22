package com.app.server.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.app.server.model.FriendRelationModel;
import com.app.server.web.bean.UserInfoBean;

public class LoadFriendsAction extends AbstractAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
		String userId = request.getParameter("userId");
//		userId = "1";
		List<FriendRelationModel> relationModelList = entityQueryFactory
				.createQuery(FriendRelationModel.class)
				.eq("userInfoModelId", Integer.parseInt(userId), true).list();
		List<UserInfoBean> returnList = new ArrayList<UserInfoBean>();
		for (int i = 0; i < relationModelList.size(); i++) {
			UserInfoBean bean = new UserInfoBean(relationModelList.get(i)
					.getFriendInfoModel());
			bean.setIsBlocked(0);
			bean.setIsFriend(1);
			returnList.add(bean);
		}
		JSONArray returnArray = JSONArray.fromObject(returnList);
		ServerResponseBean returnObj = new ServerResponseBean(200, returnArray);
		return returnObj;
	}

}
