package com.app.server.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.app.server.da.IEntityQuery;
import com.app.server.model.BlackListModel;
import com.app.server.model.FriendRelationModel;
import com.app.server.model.UserInfoModel;
import com.app.server.util.DistanceCalculator;
import com.app.server.web.bean.UserInfoBean;
import com.mysql.jdbc.StringUtils;

public class LoadCrowdAction extends AbstractAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
		String longitude = request.getParameter("longitude");
		String latitude = request.getParameter("latitude");
		String gender = request.getParameter("gender");
		String time = request.getParameter("time");
		String userId = request.getParameter("userId");

		String startSrc = request.getParameter("start");
		String numSrc = request.getParameter("num");

		if (longitude == null || latitude == null
				|| longitude.trim().equals("") || latitude.trim().equals("")) {
			return new ServerResponseBean(0x0101, null);
		}

		if (gender == null || gender.trim().equals("")) {
			gender = "all";
		}
		IEntityQuery<UserInfoModel> query = entityQueryFactory
				.createQuery(UserInfoModel.class);
		if (!gender.equals("all")) {
			int genderNum = Integer.parseInt(gender);
			query.eq("gender", genderNum, true);
		}
		if (time == null || time.trim().equals("")) {

			Date now = new Date();
			Date before = new Date(now.getTime() - 60 * 60 * 1000);
			query.ge("lastUpdateTime", before, true);
			query.desc("lastUpdateTime", true);
		} else {
			Long timelong = Long.parseLong(time);
			Date now = new Date();
			Date before = new Date(now.getTime() - timelong * 1000);
			// query.between("lastUpdateTime", before, now);
			query.ge("lastUpdateTime", before, true);
			query.desc("lastUpdateTime", true);
		}
		// if (filter.equals("distance")) {
		// // TODO ADD LBS
		// }
		if (userId != null)
			query.ne("id", Integer.parseInt(userId), true);

		int start = 0;
		if (!StringUtils.isEmptyOrWhitespaceOnly(startSrc)) {
			try {
				start = Integer.parseInt(startSrc);
			} catch (Exception e) {

			}
		}

		int num = 50;

		if (!StringUtils.isEmptyOrWhitespaceOnly(numSrc)) {
			try {
				num = Integer.parseInt(numSrc);
			} catch (Exception e) {

			}
		}

		List<UserInfoModel> modelList = query.list(start, num);
		System.err.println("modelList size=" + modelList.size());

		List<BlackListModel> blackListModelList = entityQueryFactory
				.createQuery(BlackListModel.class)
				.eq("userInfoModelId", Integer.parseInt(userId), true).list();

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

		// for (Iterator<UserInfoModel> iter = modelList.iterator(); iter
		// .hasNext();) {
		// UserInfoModel um = iter.next();
		// for (BlackListModel bm : blackListModelList) {
		// if (bm.getFriendInfoModel().getId() == um.getId()) {
		// iter.remove();
		// break;
		// }
		// }
		//
		// }

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

				FriendRelationModel relationModel = entityQueryFactory
						.createQuery(FriendRelationModel.class)
						.eq("userInfoModelId", Integer.parseInt(userId), true)
						.eq("friendInfoModel", model, true).get();

				if (relationModel != null) {
					bean.setIsFriend(1);
				}

				returnList.add(bean);
			}
		}
		
		Collections.sort(returnList, new MyComparator());

		System.err.println("returnList size=" + returnList.size());
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
