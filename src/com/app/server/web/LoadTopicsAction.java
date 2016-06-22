package com.app.server.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.app.server.da.IEntityQuery;
import com.app.server.model.TopicModel;
import com.app.server.model.UserInfoModel;
import com.app.server.util.CrowdUtil;
import com.app.server.util.DistanceCalculator;
import com.app.server.web.bean.TopicBean;

public class LoadTopicsAction extends AbstractAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {

		String longitude = request.getParameter("longitude");
		String latitude = request.getParameter("latitude");

		String gender = request.getParameter("gender");
		String distance = request.getParameter("distance");
		String startId = request.getParameter("startId");

		int distanceValue = 100000;
		int startIdNum = 0;
		if (gender != null) {

		}
		if (startId != null && !startId.equals("")) {
			startIdNum = Integer.parseInt(startId);
		}
		if (distance != null && !distance.equals("")) {
			distanceValue = Integer.parseInt(distance);
		}
		if (longitude == null || latitude == null
				|| longitude.trim().equals("") || latitude.trim().equals("")) {
			return new ServerResponseBean(0x0101, null);
		}
		double longitudeValue = Double.parseDouble(longitude);
		double latitudeValue = Double.parseDouble(latitude);
		
		
		float maxLatitude = CrowdUtil.getMaxLatitude(latitudeValue,
				longitudeValue, distanceValue);
		float minLatitude = CrowdUtil.getMinLatitude(latitudeValue,
				longitudeValue, distanceValue);
		float maxLongitude = CrowdUtil.getMaxLongitude(latitudeValue,
				longitudeValue, distanceValue);
		float minLongitude = CrowdUtil.getMinLongitude(latitudeValue,
				longitudeValue, distanceValue);
		
		IEntityQuery<TopicModel> query = entityQueryFactory
				.createQuery(TopicModel.class)
				.between("latitude", minLatitude, maxLatitude)
				.between("longitude", minLongitude, maxLongitude);
		List<TopicModel> topicModelList =query.list();

		List<TopicBean> returnList = new ArrayList<TopicBean>();
		for (int i = 0; i < topicModelList.size(); i++) {
			TopicModel model = topicModelList.get(i);
			double distenceLocal = DistanceCalculator.getDistance(
					longitudeValue, latitudeValue,
					model.getLongitude(),
					model.getLatitude());
			
			if (distenceLocal<distanceValue) {
				TopicBean bean = new TopicBean(model);
				bean.setDistance((float)distenceLocal);

				returnList.add(bean);
			}

		}
		JSONArray returnArray = JSONArray.fromObject(returnList);
		ServerResponseBean returnObj = new ServerResponseBean(200, returnArray);
		return returnObj;
	}

}
