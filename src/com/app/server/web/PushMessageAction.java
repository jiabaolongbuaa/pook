package com.app.server.web;

import java.io.UnsupportedEncodingException;

import javapns.Push;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.devices.exceptions.InvalidDeviceTokenFormatException;
import javapns.notification.PayloadPerDevice;
import javapns.notification.PushNotificationPayload;
import javapns.notification.transmission.PushQueue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

import com.app.server.model.PushSettingModel;
import com.app.server.model.UserInfoModel;
import com.app.server.util.PushQueueSingleton;

public class PushMessageAction extends AbstractAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
		String friendId = request.getParameter("friendId");
		String sourceId = request.getParameter("sourceId");
		String alert = request.getParameter("alert");
		String message = request.getParameter("message");
		String receipts = request.getParameter("receipts");

		UserInfoModel userInfoModel;
		String deviceToken = null;
		if (friendId != null) {
			PushSettingModel pushSetting = entityQueryFactory
					.createQuery(PushSettingModel.class)
					.eq("userId", Integer.parseInt(friendId), true).get();
			if(pushSetting == null){
				pushSetting = new PushSettingModel();
				pushSetting.setUserId(Integer.parseInt(friendId));
				pushSetting.setIsAcceptNotification(true);
				pushSetting.setIsAcceptPMNotification(true);
				pushSetting.setIsDisplayNotificationDetail(true);
				entityPersist.saveOrUpdate(pushSetting);
			}
			if (pushSetting.getIsAcceptNotification() != true) 
				return new ServerResponseBean(200, null);
			userInfoModel = entityQueryFactory.createQuery(UserInfoModel.class)
					.eq("id", Integer.parseInt(friendId), false).get();
			deviceToken = userInfoModel.getDeviceToken();
		}

		if (alert == null) {
			alert = "您收到一封新私信";
		} 
		else {
			try {
//				message = new String(message.getBytes("ISO-8859-1"), "UTF-8");
				alert = new String(alert.getBytes("UTF-8"), "UTF-8");
				System.err.println(alert);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
			}
		}
		try {
			PushNotificationPayload payload = new PushNotificationPayload();
			payload.setCharacterEncoding("UTF-8");
			payload.addAlert(alert);
			payload.addCustomDictionary("message", message);
			payload.addBadge(1);
			payload.addSound("default");
			if (sourceId != null)
				payload.addCustomDictionary("sourceId", sourceId);
			if (receipts !=null &&receipts.trim()!=""){
				payload.addCustomDictionary("receipts", receipts);
			}

			
			PushQueue queue =PushQueueSingleton.getInstance();
			queue.add(payload,deviceToken);

		} catch (JSONException | InvalidDeviceTokenFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ServerResponseBean(200, null);
	}

}
