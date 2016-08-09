package com.app.server.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import net.sf.json.JSONObject;

import com.app.server.constant.ResponseCode;
import com.app.server.model.BlackListModel;
import com.app.server.model.FriendRelationModel;
import com.app.server.model.RemarkModel;
import com.app.server.model.UserInfoModel;
import com.app.server.util.DistanceCalculator;
import com.app.server.web.bean.UserInfoBean;

public class LoadProfileAction extends AbstractAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
		UserInfoModel userId = user.get();

		// userId ="1";
		String friendId = request.getParameter("friendId");
		String longitude = request.getParameter("longitude");
		String latitude = request.getParameter("latitude");

		if (StringUtils.isEmpty(friendId)) {
			// UserInfoModel query = new UserInfoModel();
			// query.setId(Integer.parseInt(userId));
			UserInfoModel model = entityQueryFactory
					.createQuery(UserInfoModel.class)
					.eq("id", userId.getId(), true).get();

			if (model == null) {
				return new ServerResponseBean(2, null);
			}

			UserInfoBean bean = new UserInfoBean(model);
			JSONObject returnObject = JSONObject.fromObject(bean);
			ServerResponseBean returnObj = new ServerResponseBean(200,
					returnObject);
			return returnObj;
		} else {

			int friend = 0;

			try {
				friend = Integer.parseInt(friendId);
			} catch (Exception e) {
				return new ServerResponseBean(3, null);
			}
			if (friend <= 0) {
				return new ServerResponseBean(3, null);
			}

			UserInfoModel model = entityQueryFactory
					.createQuery(UserInfoModel.class)
					.eq("id", Integer.parseInt(friendId), true).get();

			if (model == null) {
				return new ServerResponseBean(2, null);
			}

			UserInfoBean bean = new UserInfoBean(model);
			RemarkModel remarkModel = entityQueryFactory
					.createQuery(RemarkModel.class)
					.eq("userInfoModelId", userId.getId(), true)
					.eq("friendInfoModel", model, true).get();

			if (remarkModel != null) {

				bean.setRemark(remarkModel.getRemark());

			}

			if (!Float.isNaN(model.getLongitude()) && longitude != null
					&& latitude != null) {

				double lon = 0.0;
				double lati = 0.0;
				try {
					lon = Double.parseDouble(longitude);
					lati = Double.parseDouble(latitude);
				} catch (Exception e) {
					return new ServerResponseBean(3, null);
				}

				double distenceLocal = DistanceCalculator.getDistance(lon,
						lati, model.getLongitude(), model.getLatitude());
				java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
				String s = df.format(distenceLocal / 1000);
				bean.setDistance(s);
			} else {
				bean.setDistance("-1");
			}

			FriendRelationModel relationModel = entityQueryFactory
					.createQuery(FriendRelationModel.class)
					.eq("userInfoModelId", userId.getId(), true)
					.eq("friendInfoModel", model, true).get();
			if (relationModel != null) {
				bean.setIsFriend(1);
			} else {
				bean.setIsFriend(0);
			}
			BlackListModel blackListModel = entityQueryFactory
					.createQuery(BlackListModel.class)
					.eq("userInfoModelId", userId.getId(), true)
					.eq("friendInfoModel", model, true).get();

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
