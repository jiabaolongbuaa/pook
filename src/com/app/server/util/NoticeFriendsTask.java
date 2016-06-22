package com.app.server.util;

import java.util.List;

import javapns.Push;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.notification.PushNotificationPayload;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.app.server.da.IEntityPersist;
import com.app.server.da.IEntityQuery;
import com.app.server.da.IEntityQueryFactory;
import com.app.server.model.UserInfoModel;

public class NoticeFriendsTask implements Runnable {
	@Autowired
	@Qualifier("entityQueryFactory")
	protected IEntityQueryFactory entityQueryFactory;
	@Autowired
	@Qualifier("entityPersist")
	protected IEntityPersist entityPersist;

	public NoticeFriendsTask(UserInfoModel userInfoModel) {
		this.userInfoModel = userInfoModel;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
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
		System.err.println("modle list size = "+modelList.size());
		String alert = "有您关注的友好出现在您附近！";
		for (UserInfoModel model : modelList) {
			PushNotificationPayload payload = new PushNotificationPayload();
			payload.setCharacterEncoding("UTF-8");
			try {
				payload.addAlert(alert);
				payload.addBadge(1);
				payload.addSound("default");

				Push.payload(payload, "/root/jiabaolong/cert/littlebird.p12",
						"littlebird", false, model.getDeviceToken());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (CommunicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (KeystoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private UserInfoModel userInfoModel = null;

}
