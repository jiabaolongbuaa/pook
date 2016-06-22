package com.app.server.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.app.server.model.PushSettingModel;
import com.app.server.web.bean.PushSettingBean;

public class LoadPushSettingAction extends AbstractAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
		String userId = request.getParameter("userId");

		PushSettingModel pushSettingModel = entityQueryFactory
				.createQuery(PushSettingModel.class)
				.eq("userId", Integer.parseInt(userId), false).get();
		if (pushSettingModel == null)
			return new ServerResponseBean(0x0101, null);
		PushSettingBean settingBean = new PushSettingBean(pushSettingModel);

		JSONObject returnObject = JSONObject.fromObject(settingBean);

		return new ServerResponseBean(200, returnObject);
	}

}
