package com.app.server.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.server.model.UserInfoModel;
import com.mysql.jdbc.StringUtils;

public class UpdateProfileAction extends AbstractAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String longitude = request.getParameter("longitude");
		String latitude = request.getParameter("latitude");
		String genderSrc = request.getParameter("gender");

		String userIdSrc = request.getParameter("userId");
		String userName = request.getParameter("userName");
		String label = request.getParameter("label");
		String birthday = request.getParameter("birthday");
		String canbefollow = request.getParameter("canbefollow");
		String hide = request.getParameter("hide");
		String ageSrc = request.getParameter("age");
		String deviceToken = request.getParameter("deviceToken");
		//

		System.err.println("in update profile");

		Enumeration<String> keys = request.getParameterNames();

		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			System.err.println("key: " + key + "   value: "
					+ request.getParameter(key));
		}
		System.err.println("userId = " + userIdSrc);
		if (StringUtils.isEmptyOrWhitespaceOnly(userIdSrc)) {
			return new ServerResponseBean(1, null);
		}

		int userId = 0;
		try {
			userId = Integer.parseInt(userIdSrc);
		} catch (Exception e) {
			return new ServerResponseBean(117, null);
		}

		int gender = 0;
		if (!StringUtils.isEmptyOrWhitespaceOnly(genderSrc)) {

			try {
				gender = Integer.parseInt(genderSrc);
			} catch (Exception e) {
				return new ServerResponseBean(119, null);
			}

		}

		int age = 0;
		if (!StringUtils.isEmptyOrWhitespaceOnly(ageSrc)) {

			try {
				age = Integer.parseInt(ageSrc);
			} catch (Exception e) {
				return new ServerResponseBean(121, null);
			}
		}
		UserInfoModel userInfoModel = entityQueryFactory
				.createQuery(UserInfoModel.class).eq("id", userId, true).get();
		if (userInfoModel == null)
			return new ServerResponseBean(2, null);

		if (longitude != null)
			userInfoModel.setLongitude(Float.parseFloat(longitude));
		if (latitude != null)
			userInfoModel.setLatitude(Float.parseFloat(latitude));

		if (gender > 0)
			userInfoModel.setGender(gender);
		if (birthday != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date birth = null;
			try {
				birth = sdf.parse(birthday);
			} catch (ParseException e) {
				// TODO Auto-generated catch block

				e.printStackTrace();
				return new ServerResponseBean(0x001, null);
			}
			userInfoModel.setBirthday(birth);
		}
		if (!StringUtils.isEmptyOrWhitespaceOnly(userName)
				&& !userName.equals(userInfoModel.getName()))
			userInfoModel.setName(userName);
		if (label != null)
			userInfoModel.setLabel(label);

		if (age > 0) {
			userInfoModel.setAge(age);
		}

		if (!StringUtils.isEmptyOrWhitespaceOnly(hide)) {
			userInfoModel.setHide(Integer.parseInt(hide));
		}

		if (!StringUtils.isEmptyOrWhitespaceOnly(canbefollow)) {
			userInfoModel.setCanbefollow(Integer.parseInt(canbefollow));
		}

		if (!StringUtils.isEmptyOrWhitespaceOnly(deviceToken)) {
			deviceToken = deviceToken.replaceAll(" ", "");
			userInfoModel.setDeviceToken(deviceToken);
		}

		userInfoModel.setLastUpdateTime(new Date());
		entityPersist.saveOrUpdate(userInfoModel);

		return new ServerResponseBean(200, null);

	}

}
