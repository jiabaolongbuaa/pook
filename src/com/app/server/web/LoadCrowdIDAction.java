package com.app.server.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;

import com.app.server.constant.ResponseCode;
import com.app.server.da.IEntityQuery;
import com.app.server.model.BlackListModel;
import com.app.server.model.UserInfoModel;
import com.app.server.util.CrowdUtil;

public class LoadCrowdIDAction extends AbstractAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
		String longitude = request.getParameter("longitude");
		String latitude = request.getParameter("latitude");
		String distance = request.getParameter("distance");
		UserInfoModel userId = user.get();

		if (StringUtils.isEmpty(longitude) || StringUtils.isEmpty(latitude)) {
			return new ServerResponseBean(ResponseCode.POOK_PARAM_ERROR, null);
		}

		float longitudeValue = Float.parseFloat(longitude);
		float latitudeValue = Float.parseFloat(latitude);

		int distanceValue;

		if (distance == null) {
			distanceValue = 1000;
		} else {
			distanceValue = Integer.parseInt(distance);
		}

		List<UserInfoModel> modelList;
		final int MAX_TRIAL_NUM = 10;
		int num = 0;

		while (true) {
			num++;
			float maxLatitude = CrowdUtil.getMaxLatitude(latitudeValue,
					longitudeValue, distanceValue);
			float minLatitude = CrowdUtil.getMinLatitude(latitudeValue,
					longitudeValue, distanceValue);
			float maxLongitude = CrowdUtil.getMaxLongitude(latitudeValue,
					longitudeValue, distanceValue);
			float minLongitude = CrowdUtil.getMinLongitude(latitudeValue,
					longitudeValue, distanceValue);

			IEntityQuery<UserInfoModel> query = entityQueryFactory
					.createQuery(UserInfoModel.class)
					.between("latitude", minLatitude, maxLatitude)
					.between("longitude", minLongitude, maxLongitude);
			query.desc("lastUpdateTime", true);
			query.ne("id", userId.getId(), true);
			modelList = query.list();
			if (modelList.size() == 0 && num < MAX_TRIAL_NUM) {
				distanceValue += 1000;
			} else {
				break;
			}
		}

		List<BlackListModel> blackListModelList = entityQueryFactory
				.createQuery(BlackListModel.class)
				.eq("userInfoModelId", userId.getId(), false).list();

		for (Iterator<UserInfoModel> iter = modelList.iterator(); iter
				.hasNext();) {
			UserInfoModel um = iter.next();
			for (BlackListModel bm : blackListModelList) {
				if (bm.getFriendInfoModel().getId() == um.getId()) {
					iter.remove();
				}
			}

		}

		List<Integer> returnList = new ArrayList<Integer>();
		for (int i = 0; i < modelList.size(); i++) {

			returnList.add(modelList.get(i).getId());
		}

		JSONArray returnArray = JSONArray.fromObject(returnList);

		// logger.info(new InfoLog(userId, 200, ""));
		ServerResponseBean returnObj = new ServerResponseBean(200, returnArray);
		return returnObj;
	}

}
