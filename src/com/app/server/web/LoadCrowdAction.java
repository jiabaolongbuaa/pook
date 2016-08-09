package com.app.server.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import net.sf.json.JSONArray;

import com.app.server.constant.ResponseCode;
import com.app.server.da.IEntityQuery;
import com.app.server.model.BlackListModel;
import com.app.server.model.FriendRelationModel;
import com.app.server.model.RemarkModel;
import com.app.server.model.UserInfoModel;
import com.app.server.util.CrowdUtil;
import com.app.server.util.DistanceCalculator;
import com.app.server.web.bean.UserInfoBean;

public class LoadCrowdAction extends AbstractAction {

	static Logger logger = Logger.getLogger(LoadCrowdAction.class.getName());

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
		String longitude = request.getParameter("longitude");
		String latitude = request.getParameter("latitude");
		String gender = request.getParameter("gender");
		String time = request.getParameter("time");

		String startSrc = request.getParameter("start");
		String numSrc = request.getParameter("num");

		UserInfoModel userId = user.get();

		if (StringUtils.isEmpty(longitude) || StringUtils.isEmpty(latitude)) {
			logger.debug("longitude or latitude is empty");
			return new ServerResponseBean(ResponseCode.POOK_PARAM_ERROR, null);
		}

		double latitudeValue = 0.0;
		double longitudeValue = 0.0;
		try {
			longitudeValue = Float.parseFloat(longitude);
			latitudeValue = Float.parseFloat(latitude);
		} catch (Exception e) {
			logger.info("formate error");
			return new ServerResponseBean(ResponseCode.POOK_PARAM_ERROR, null);
		}
		// if (gender == null || gender.trim().equals("")) {
		// gender = "all";
		// }

		// if (StringUtils.isEmpty(time)) {

		// Date now = new Date();
		// Date before = new Date(now.getTime() - 60 * 60 * 1000);
		// query.ge("lastUpdateTime", before, true);
		// query.desc("lastUpdateTime", true);
		// } else {
		// Long timelong = Long.parseLong(time);
		// Date now = new Date();
		// Date before = new Date(now.getTime() - timelong * 1000);
		// query.between("lastUpdateTime", before, now);
		// query.ge("lastUpdateTime", before, true);
		// query.desc("lastUpdateTime", true);
		// }
		// if (filter.equals("distance")) {
		// // TODO ADD LBS
		// }

		// query.ne("id", userId.getId(), true);

		int distanceValue = 1000;

		List<UserInfoModel> modelList;
		final int MAX_TRIAL_NUM = 10;
		int num = 0;
		IEntityQuery<UserInfoModel> query = entityQueryFactory
				.createQuery(UserInfoModel.class);

		while (true) {
			query = entityQueryFactory.createQuery(UserInfoModel.class);
			if (!StringUtils.isEmpty(gender)) {
				int genderNum = 0;
				try {
					genderNum = Integer.parseInt(gender);
				} catch (Exception e) {
					logger.debug("gender format error");
				}
				if (genderNum > 0) {
					query.eq("gender", genderNum, true);
				}

			}
			num++;
			float maxLatitude = CrowdUtil.getMaxLatitude(latitudeValue,
					longitudeValue, distanceValue);
			float minLatitude = CrowdUtil.getMinLatitude(latitudeValue,
					longitudeValue, distanceValue);
			float maxLongitude = CrowdUtil.getMaxLongitude(latitudeValue,
					longitudeValue, distanceValue);
			float minLongitude = CrowdUtil.getMinLongitude(latitudeValue,
					longitudeValue, distanceValue);

			logger.info("maxlatitude : " + maxLatitude);
			logger.info("minlatitude : " + minLatitude);
			logger.info("maxLongitude : " + maxLongitude);
			logger.info("minLongitude : " + minLongitude);

			query.between("latitude", minLatitude, maxLatitude).between(
					"longitude", minLongitude, maxLongitude);

			query.ne("id", userId.getId(), true);
			// query.isNotNull("phonenum");
			query.ne("phonenum", "", true);
			modelList = query.list();
			if (modelList.size() == 0 && num < MAX_TRIAL_NUM) {
				distanceValue += 1000;
			} else {
				break;
			}
		}

		modelList = query.list();
		System.err.println("modelList size=" + modelList.size());

		List<BlackListModel> blackListModelList = entityQueryFactory
				.createQuery(BlackListModel.class)
				.eq("userInfoModelId", userId.getId(), true).list();

		// System.err.println("blacklist size=" + blackListModelList.size());

		for (BlackListModel bm : blackListModelList) {

			// System.err.println("black id: " +
			// bm.getFriendInfoModel().getId());
			for (int i = 0; i < modelList.size(); i++) {
				// System.err.println("model id: " + modelList.get(i).getId());
				if (bm.getFriendInfoModel().getId()
						.equals(modelList.get(i).getId())) {
					// System.err.println("same!remove!");
					modelList.remove(i);

					break;
				}
			}
			// modelList.remove(bm.getFriendInfoModel());
		}

		List<UserInfoBean> returnList = new ArrayList<UserInfoBean>();
		for (int i = 0; i < modelList.size(); i++) {

			UserInfoModel model = modelList.get(i);
			if (!Float.isNaN(model.getLongitude())) {
				double distenceLocal = DistanceCalculator.getDistance(
						Double.parseDouble(longitude),
						Double.parseDouble(latitude), model.getLongitude(),
						model.getLatitude());
				UserInfoBean bean = new UserInfoBean(model);

				java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
				String s = df.format(distenceLocal / 1000);
				if (s.startsWith(".")) {
					StringBuilder sb = new StringBuilder();
					sb.append("0");
					sb.append(s);
					s = sb.toString();
				}
				bean.setDistance(s);

				RemarkModel remarkModel = entityQueryFactory
						.createQuery(RemarkModel.class)
						.eq("userInfoModelId", userId.getId(), true)
						.eq("friendInfoModel", model, true).get();

				if (remarkModel != null) {

					bean.setRemark(remarkModel.getRemark());

				}

				FriendRelationModel relationModel = entityQueryFactory
						.createQuery(FriendRelationModel.class)
						.eq("userInfoModelId", userId.getId(), true)
						.eq("friendInfoModel", model, true).get();

				if (relationModel != null) {
					bean.setIsFriend(1);
				}

				returnList.add(bean);
			}
		}

		Collections.sort(returnList, new MyComparator());

		logger.debug("returnList size=" + returnList.size());
		JSONArray returnArray = JSONArray.fromObject(returnList);
		ServerResponseBean returnObj = new ServerResponseBean(200, returnArray);
		return returnObj;
	}

	class MyComparator implements Comparator {
		public int compare(Object obj1, Object obj2) {
			UserInfoBean u1 = (UserInfoBean) obj1;
			UserInfoBean u2 = (UserInfoBean) obj2;

			float distance1 = Float.parseFloat(u1.getDistance());
			float distance2 = Float.parseFloat(u2.getDistance());
			if (distance1 > distance2) {
				return 1;
			} else if (distance1 < distance2) {
				return -1;
			} else {
				return 0;
			}

		}
	}

}
