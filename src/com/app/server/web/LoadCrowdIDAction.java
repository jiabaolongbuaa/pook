package com.app.server.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
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

public class LoadCrowdIDAction extends AbstractAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
		String longitude = request.getParameter("longitude");
		String latitude = request.getParameter("latitude");
		String userId = request.getParameter("userId");

		if (longitude == null || latitude == null
				|| longitude.trim().equals("") || latitude.trim().equals("")) {
			return new ServerResponseBean(0x0101, null);
		}

		
		IEntityQuery<UserInfoModel> query = entityQueryFactory
				.createQuery(UserInfoModel.class);
	
		if (userId != null)
			query.ne("id", Integer.parseInt(userId), true);

		List<UserInfoModel> modelList = query.list();
		System.err.println("modelList size=" + modelList.size());

		List<BlackListModel> blackListModelList = entityQueryFactory
				.createQuery(BlackListModel.class)
				.eq("userInfoModelId", Integer.parseInt(userId), true).list();
		
		//System.err.println("blacklist size=" + blackListModelList.size());

		for (BlackListModel bm : blackListModelList) {

			//System.err.println("black id: " + bm.getFriendInfoModel().getId());
			for (int i = 0; i < modelList.size(); i++) {
				//System.err.println("model id: " + modelList.get(i).getId());
				if (bm.getFriendInfoModel().getId()
						.equals(modelList.get(i).getId())) {
					//System.err.println("same!remove!");
					modelList.remove(i);

					break;
				}
			}
			// modelList.remove(bm.getFriendInfoModel());
		}


		List<Integer> returnList = new LinkedList<Integer>();
		for (int i = 0; i < modelList.size(); i++) {

				returnList.add(modelList.get(i).getId());
			}
		

		JSONArray returnArray = JSONArray.fromObject(returnList);
		ServerResponseBean returnObj = new ServerResponseBean(200, returnArray);
		return returnObj;
	}

}
