package com.app.server.web;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javapns.Push;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.devices.exceptions.InvalidDeviceTokenFormatException;
import javapns.notification.PushNotificationPayload;
import javapns.notification.transmission.PushQueue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;

import com.app.server.da.IEntityPersist;
import com.app.server.da.IEntityQuery;
import com.app.server.da.IEntityQueryFactory;
import com.app.server.model.BlackListModel;
import com.app.server.model.FriendRelationModel;
import com.app.server.model.PushSettingModel;
import com.app.server.model.UserInfoModel;
import com.app.server.util.CrowdUtil;
import com.app.server.util.NoticeFriendsTask;
import com.app.server.util.PushQueueSingleton;

public class UpdateLocationAction extends AbstractAction {

	@Autowired
	@Qualifier("taskExecutor")
	protected TaskExecutor taskExecutor;

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

		UserInfoModel userInfoModel = entityQueryFactory
				.createQuery(UserInfoModel.class)
				.eq("id", Integer.parseInt(userId), true).get();

		userInfoModel.setLongitude(Float.parseFloat(longitude));
		userInfoModel.setLatitude(Float.parseFloat(latitude));
		userInfoModel.setLastUpdateTime(new Date());
		entityPersist.saveOrUpdate(userInfoModel);
		// NoticeFriendsTask noticeFriendsTask = new
		// NoticeFriendsTask(userInfoModel);
		// taskExecutor.execute(noticeFriendsTask);

		float maxLatitude = CrowdUtil
				.getMaxLatitude(userInfoModel.getLatitude(),
						userInfoModel.getLongitude(), 2000);
		float minLatitude = CrowdUtil
				.getMinLatitude(userInfoModel.getLatitude(),
						userInfoModel.getLongitude(), 2000);
		float maxLongitude = CrowdUtil
				.getMaxLongitude(userInfoModel.getLatitude(),
						userInfoModel.getLongitude(), 2000);
		float minLongitude = CrowdUtil
				.getMinLongitude(userInfoModel.getLatitude(),
						userInfoModel.getLongitude(), 2000);

		IEntityQuery<UserInfoModel> query = entityQueryFactory
				.createQuery(UserInfoModel.class)
				.between("latitude", minLatitude, maxLatitude)
				.between("longitude", minLongitude, maxLongitude);
		query.desc("lastUpdateTime", true);
		query.ne("id", userInfoModel.getId(), true);
		List<UserInfoModel> modelList = query.list();

		List<BlackListModel> blackListModelList = entityQueryFactory
				.createQuery(BlackListModel.class)
				.eq("userInfoModelId", Integer.parseInt(userId), false).list();

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
					.eq("userInfoModelId", Integer.parseInt(userId), false)
					.eq("friendInfoModel", model, false).get();
			if (relationModel != null) {
				PushSettingModel pushSetting = entityQueryFactory
						.createQuery(PushSettingModel.class)
						.eq("userId", model.getId(), true).get();
				if(pushSetting == null){
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
						queue.add(payload, model.getDeviceToken());

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
