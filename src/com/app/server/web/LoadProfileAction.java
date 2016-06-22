package com.app.server.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.app.server.model.BlackListModel;
import com.app.server.model.FriendRelationModel;
import com.app.server.model.UserInfoModel;
import com.app.server.util.DistanceCalculator;
import com.app.server.web.bean.UserInfoBean;

public class LoadProfileAction extends AbstractAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
		String userId = request.getParameter("userId");
		// userId ="1";
		String friendId = request.getParameter("friendId");
		String longitude = request.getParameter("longitude");
		String latitude = request.getParameter("latitude");

		if (friendId == null || friendId.trim().equals("")) {
			// UserInfoModel query = new UserInfoModel();
			// query.setId(Integer.parseInt(userId));
			UserInfoModel model = entityQueryFactory
					.createQuery(UserInfoModel.class)
					.eq("id", Integer.parseInt(userId), false).get();

			if (model == null) {
				return new ServerResponseBean(0x001, null);
			}

			UserInfoBean bean = new UserInfoBean(model);
			JSONObject returnObject = JSONObject.fromObject(bean);
			ServerResponseBean returnObj = new ServerResponseBean(200,
					returnObject);
			return returnObj;
		} else {
			// UserInfoModel query = new UserInfoModel();
			// query.setId(Integer.parseInt(friendId));
			UserInfoModel model = entityQueryFactory
					.createQuery(UserInfoModel.class)
					.eq("id", Integer.parseInt(friendId), false).get();

			UserInfoBean bean = new UserInfoBean(model);

			if (!Float.isNaN(model.getLongitude()) && longitude != null
					&& latitude != null) {

				double distenceLocal = DistanceCalculator.getDistance(
						Double.parseDouble(longitude),
						Double.parseDouble(latitude),
						model.getLongitude(),
						model.getLatitude());
				 java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");  
			        String s=df.format(distenceLocal/1000);
				bean.setDistance(s);
			} else {
				bean.setDistance("-1");
			}

			FriendRelationModel relationModel = entityQueryFactory
					.createQuery(FriendRelationModel.class)
					.eq("userInfoModelId", Integer.parseInt(userId), false)
					.eq("friendInfoModel", model, false).get();
			if (relationModel != null) {
				bean.setIsFriend(1);
			} else {
				bean.setIsFriend(0);
			}
			BlackListModel blackListModel = entityQueryFactory
					.createQuery(BlackListModel.class)
					.eq("userInfoModelId", Integer.parseInt(userId), false)
					.eq("friendInfoModel", model, false).get();

			if (blackListModel != null) {
				bean.setIsBlocked(1);
			} else {
				bean.setIsBlocked(0);
			}

			JSONObject returnObject = JSONObject.fromObject(bean);
			ServerResponseBean returnObj = new ServerResponseBean(200,
					returnObject);
			return returnObj;
		}
	}

}
