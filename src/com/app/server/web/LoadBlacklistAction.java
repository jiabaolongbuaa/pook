package com.app.server.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;

import com.app.server.model.BlackListModel;
import com.app.server.model.BlackListModel;
import com.app.server.model.UserInfoModel;
import com.app.server.web.bean.UserInfoBean;

public class LoadBlacklistAction extends AbstractAction {

	static Logger logger = Logger.getLogger(LoadBlacklistAction.class.getName());
	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {

		UserInfoModel userId = user.get();

		List<BlackListModel> relationModelList = entityQueryFactory
				.createQuery(BlackListModel.class)
				.eq("userInfoModelId", userId.getId(), true).list();
		List<UserInfoBean> returnList = new ArrayList<UserInfoBean>();
		for (int i = 0; i < relationModelList.size(); i++) {
			UserInfoBean bean = new UserInfoBean(relationModelList.get(i)
					.getFriendInfoModel());
			bean.setIsBlocked(1);
			bean.setIsFriend(0);
			returnList.add(bean);
		}
		JSONArray returnArray = JSONArray.fromObject(returnList);
		ServerResponseBean returnObj = new ServerResponseBean(200, returnArray);
		return returnObj;
	}

}
