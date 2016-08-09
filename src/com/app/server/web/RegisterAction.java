package com.app.server.web;

import java.util.Date;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import com.app.server.model.ActiveCodeModel;
import com.app.server.model.PushSettingModel;
import com.app.server.model.UserInfoModel;
import com.app.server.util.MD5Util;
import com.app.server.util.OpenFireUtils;
import com.app.server.util.OpenfireRegistrationAgent;
import com.app.server.web.bean.UserInfoBean;
import com.mysql.jdbc.StringUtils;

/**
 * 注册接口
 * 
 * @author Simon
 *
 */
public class RegisterAction extends AbstractNoneStateAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		String phonenum = request.getParameter("phone");
		if (StringUtils.isEmptyOrWhitespaceOnly(phonenum)) {
			return new ServerResponseBean(222, null);
		}
		ActiveCodeModel code = entityQueryFactory
				.createQuery(ActiveCodeModel.class)
				.eq("phonenum", phonenum, true).get();

		if (code == null) {
			return new ServerResponseBean(223, null);
		}

		if (code.getPass() < 1) {
			return new ServerResponseBean(224, null);
		}
		// Boolean isSMSPassed = (Boolean)session.getAttribute("SMSPassed");

		// if (isSMSPassed == null || isSMSPassed == false) {
		// return new ServerResponseBean(0x0003, null);
		// }
		// if (phonenum == null ) {
		// return new ServerResponseBean(0x0004, null);
		// }

		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String userName = request.getParameter("userName");
		String deviceToken = request.getParameter("deviceToken");

		if ( password == null || userName == null) {
			return new ServerResponseBean(0x0001, null);
		}
	//	if (!com.app.server.util.StringUtils.isEmail(email)) {
	//		return new ServerResponseBean(2, null);
	//	}
		UserInfoModel userInfoModel = entityQueryFactory
				.createQuery(UserInfoModel.class).eq("phonenum", phonenum, true)
				.get();
		if (userInfoModel != null) {
			return new ServerResponseBean(3, null);
		}
		// userInfoModel = entityQueryFactory
		// .createQuery(UserInfoModel.class).eq("name", userName, false)
		// .get();
		// if (userInfoModel != null) {
		// return new ServerResponseBean(0x0003, null);
		// }

		String password_md5 = MD5Util.stringToMD5(password);
		System.err.println("5email=" + email);
		userInfoModel = new UserInfoModel();
		userInfoModel.setEmail(email);
		// model.setGender(gender);
		userInfoModel.setName(userName);
		userInfoModel.setPassword(password_md5);
		userInfoModel.setPhonenum(phonenum);
		// model.setAge(Integer.valueOf(age));
		// model.setLabel(label);
		userInfoModel.setLastUpdateTime(new Date());

		userInfoModel.setDeviceToken(deviceToken);

		entityPersist.saveOrUpdate(userInfoModel);
		System.err.println("6email=" + email);

		PushSettingModel pushSetting = new PushSettingModel();
		pushSetting.setUserId(userInfoModel.getId());
		pushSetting.setIsAcceptNotification(true);
		pushSetting.setIsAcceptPMNotification(true);
		pushSetting.setIsDisplayNotificationDetail(true);
		entityPersist.saveOrUpdate(pushSetting);

		String name = null;
		System.err.println("userid=" + userInfoModel.getId());
		System.err.println("7email=" + email);
		System.err.println("============================");

		boolean successful = OpenFireUtils.createUser(String.valueOf(userInfoModel.getId()), phonenum,
				password);

		ServerResponseBean returnObj = null;
		if (successful) {
			System.err.println("8email=" + email);
			UserInfoBean userInfoBean = new UserInfoBean(userInfoModel);
			JSONObject returnObject = JSONObject.fromObject(userInfoBean);
			returnObj = new ServerResponseBean(200, returnObject);
		} else {
			returnObj = new ServerResponseBean(4, null);
		}
		session = request.getSession();
		session.setAttribute("user", userInfoModel);
		dealwithResponse(response,session,userInfoModel);
		// ServerResponseBean returnObj = new ServerResponseBean(200,
		// returnObject);
		return returnObj;
	}
	
	
	private void dealwithResponse(HttpServletResponse response,
			HttpSession session, UserInfoModel userInfoModel) {

		Cookie cookie1 = new Cookie("JSESSIONID", session.getId());
		cookie1.setMaxAge(365 * 24 * 3600);
		cookie1.setPath("/");

		Cookie cookie2 = new Cookie("user", String.valueOf(userInfoModel
				.getId()));
		cookie2.setMaxAge(365 * 24 * 3600);
		cookie2.setPath("/");

		response.addCookie(cookie1);
		response.addCookie(cookie2);
		session.setAttribute("user", userInfoModel);
	}

}
