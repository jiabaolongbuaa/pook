package com.app.server.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.app.server.model.FriendRelationModel;
import com.app.server.model.RemarkModel;
import com.app.server.model.UserInfoModel;
import com.app.server.web.bean.UserInfoBean;

public class LoadFriendsAction extends AbstractAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {

		UserInfoModel userId = user.get();
		// userId = "1";
		List<FriendRelationModel> relationModelList = entityQueryFactory
				.createQuery(FriendRelationModel.class)
				.eq("userInfoModelId", userId.getId(), true).list();
		List<UserInfoBean> returnList = new ArrayList<UserInfoBean>();
		for (int i = 0; i < relationModelList.size(); i++) {
			UserInfoBean bean = new UserInfoBean(relationModelList.get(i)
					.getFriendInfoModel());

			RemarkModel remarkModel = entityQueryFactory
					.createQuery(RemarkModel.class)
					.eq("userInfoModelId", userId.getId(), true)
					.eq("friendInfoModel",
							relationModelList.get(i).getFriendInfoModel(), true)
					.get();

			if (remarkModel != null) {

				bean.setRemark(remarkModel.getRemark());

			}
			bean.setIsBlocked(0);
			bean.setIsFriend(1);
			returnList.add(bean);
		}
		JSONArray returnArray = JSONArray.fromObject(returnList);
		ServerResponseBean returnObj = new ServerResponseBean(200, returnArray);
		return returnObj;
	}

}
