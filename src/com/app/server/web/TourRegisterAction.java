package com.app.server.web;

import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import net.sf.json.JSONObject;

import com.app.server.model.ActiveCodeModel;
import com.app.server.model.PushSettingModel;
import com.app.server.model.UserInfoModel;
import com.app.server.util.MD5Util;
import com.app.server.util.OpenFireUtils;
import com.app.server.util.OpenfireRegistrationAgent;
import com.app.server.web.bean.UserInfoBean;

/**
 * 注册接口
 * 
 * @author Simon
 *
 */
public class TourRegisterAction extends AbstractNoneStateAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
	
		String password = request.getParameter("password");
		String longitude = request.getParameter("longitude");
		String latitude = request.getParameter("latitude");

		if (StringUtils.isEmpty(longitude)|| StringUtils.isEmpty(latitude)) {
			return new ServerResponseBean(0x0101, null);
		}

	

		
	

		UserInfoModel userInfoModel = new UserInfoModel();

		// model.setGender(gender);
		userInfoModel.setName(String.valueOf(new Random().nextLong()));
		userInfoModel.setPassword(password);

		// model.setAge(Integer.valueOf(age));
		// model.setLabel(label);
		userInfoModel.setLastUpdateTime(new Date());
		userInfoModel.setLongitude(Float.parseFloat(longitude));
		userInfoModel.setLatitude(Float.parseFloat(latitude));

		entityPersist.saveOrUpdate(userInfoModel);
	



		boolean successful = OpenFireUtils.createUser(String.valueOf(userInfoModel.getId()), "",
				password);

		ServerResponseBean returnObj = null;
		if (successful) {

			UserInfoBean userInfoBean = new UserInfoBean(userInfoModel);
			JSONObject returnObject = JSONObject.fromObject(userInfoBean);
			returnObj = new ServerResponseBean(200, returnObject);
		} else {
			returnObj = new ServerResponseBean(4, null);
		}
		
		return returnObj;
	}
	
	
	

}
