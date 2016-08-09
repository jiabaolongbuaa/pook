package com.app.server.web;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javapns.devices.exceptions.InvalidDeviceTokenFormatException;
import javapns.notification.PushNotificationPayload;
import javapns.notification.transmission.PushQueue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;

import com.app.server.da.IEntityQuery;
import com.app.server.model.BlackListModel;
import com.app.server.model.FriendRelationModel;
import com.app.server.model.PushSettingModel;
import com.app.server.model.UserInfoModel;
import com.app.server.util.CrowdUtil;
import com.app.server.util.PushQueueSingleton;

public class UpdateLocationAction extends AbstractAction {

	static Logger logger = Logger.getLogger(UpdateLocationAction.class.getName());

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {

		UserInfoModel userId = user.get();
		String longitude = request.getParameter("longitude");
		String latitude = request.getParameter("latitude");

		if (StringUtils.isEmpty(longitude)|| StringUtils.isEmpty(latitude)) {
			return new ServerResponseBean(0x0101, null);
		}

	

		userId.setLongitude(Float.parseFloat(longitude));
		userId.setLatitude(Float.parseFloat(latitude));
		userId.setLastUpdateTime(new Date());
		entityPersist.saveOrUpdate(userId);
		// NoticeFriendsTask noticeFriendsTask = new
		// NoticeFriendsTask(userInfoModel);
		// taskExecutor.execute(noticeFriendsTask);

		float maxLatitude = CrowdUtil
				.getMaxLatitude(userId.getLatitude(),
						userId.getLongitude(), 2000);
		float minLatitude = CrowdUtil
				.getMinLatitude(userId.getLatitude(),
						userId.getLongitude(), 2000);
		float maxLongitude = CrowdUtil
				.getMaxLongitude(userId.getLatitude(),
						userId.getLongitude(), 2000);
		float minLongitude = CrowdUtil
				.getMinLongitude(userId.getLatitude(),
						userId.getLongitude(), 2000);

		IEntityQuery<UserInfoModel> query = entityQueryFactory
				.createQuery(UserInfoModel.class)
				.between("latitude", minLatitude, maxLatitude)
				.between("longitude", minLongitude, maxLongitude);
		query.desc("lastUpdateTime", true);
		query.ne("id", userId.getId(), true);
		List<UserInfoModel> modelList = query.list();

		List<BlackListModel> blackListModelList = entityQueryFactory
				.createQuery(BlackListModel.class)
				.eq("userInfoModelId",userId.getId(), true).list();

		for (Iterator<UserInfoModel> iter = modelList.iterator(); iter
				.hasNext();) {
			UserInfoModel um = iter.next();
			for (BlackListModel bm : blackListModelList) {
				if (bm.getFriendInfoModel().getId() == um.getId()) {
					iter.remove();
				}
			}

		}

		System.err.println("modle list size = " + modelList.size());
		String alert = "有您关注的友好出现在您附近！";
		for (UserInfoModel model : modelList) {

			FriendRelationModel relationModel = entityQueryFactory
					.createQuery(FriendRelationModel.class)
					.eq("userInfoModelId", userId.getId(), false)
					.eq("friendInfoModel", model, true).get();
			if (relationModel != null) {
				PushSettingModel pushSetting = entityQueryFactory
						.createQuery(PushSettingModel.class)
						.eq("userId", model.getId(), true).get();
				if (pushSetting == null) {
					pushSetting = new PushSettingModel();
					pushSetting.setUserId(model.getId());
					pushSetting.setIsAcceptNotification(true);
					pushSetting.setIsAcceptPMNotification(true);
					pushSetting.setIsDisplayNotificationDetail(true);
					entityPersist.saveOrUpdate(pushSetting);
				}
				if (pushSetting.getIsAcceptNotification() == true) {
					PushNotificationPayload payload = new PushNotificationPayload();
					payload.setCharacterEncoding("UTF-8");
					try {
						payload.addAlert(alert);
						payload.addBadge(1);
						payload.addSound("default");

						PushQueue queue = PushQueueSingleton.getInstance();
						if(queue != null){
							queue.add(payload, model.getDeviceToken());
						}else{
							logger.info("queue is null");
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvalidDeviceTokenFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}

		return new ServerResponseBean(200, null);
	}

}
