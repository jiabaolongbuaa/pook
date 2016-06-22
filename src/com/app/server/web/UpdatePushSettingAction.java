package com.app.server.web;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.server.model.PushSettingModel;


public class UpdatePushSettingAction extends AbstractAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
		String isAcceptNotification = request.getParameter("isAcceptNotification");
		String isAcceptPMNotification = request.getParameter("isAcceptPMNotification");
		String isDisplayDetail = request.getParameter("isDisplayDetail");
		String userId = request.getParameter("userId");



		PushSettingModel pushSettingModel = entityQueryFactory
				.createQuery(PushSettingModel.class)
				.eq("userId", Integer.parseInt(userId), false).get();

		
		if(pushSettingModel ==null)
			new ServerResponseBean(0x0101, null);
			
		if(isAcceptNotification!=null){
			pushSettingModel.setIsAcceptNotification(Boolean.parseBoolean(isAcceptNotification));
		}
		if(isAcceptPMNotification !=null){
			pushSettingModel.setIsAcceptPMNotification(Boolean.parseBoolean(isAcceptPMNotification));
		}
		if(isDisplayDetail!=null){
			pushSettingModel.setIsDisplayNotificationDetail(Boolean.parseBoolean(isDisplayDetail));
		}
	
		entityPersist.saveOrUpdate(pushSettingModel);
		

		return new ServerResponseBean(200, null);
	}

}
