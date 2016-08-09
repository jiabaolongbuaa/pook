package com.app.server.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;

import com.app.server.model.UserInfoModel;
import com.app.server.web.bean.UserInfoBean;

public class UpdateProfileAction extends AbstractAction {

	@Override
	public ServerResponseBean processAndReturnJSONString(
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String longitude = request.getParameter("longitude");
		String latitude = request.getParameter("latitude");
		String genderSrc = request.getParameter("gender");

		String userName = request.getParameter("userName");
		String label = request.getParameter("label");
		String birthday = request.getParameter("birthday");
		String canbefollow = request.getParameter("canbefollow");
		String hide = request.getParameter("hide");
		String ageSrc = request.getParameter("age");
		String deviceToken = request.getParameter("deviceToken");
		String ahaStr = request.getParameter("aha");
		String remark = request.getParameter("remark");
		//

		UserInfoModel userId = user.get();

		boolean isUpdated = false;

		int gender = 0;
		if (!StringUtils.isEmpty(genderSrc)) {

			try {
				gender = Integer.parseInt(genderSrc);
			} catch (Exception e) {
				return new ServerResponseBean(119, null);
			}
			if (gender > 0) {
				userId.setGender(gender);
				isUpdated = true;
			}
		}

		int age = 0;
		if (!StringUtils.isEmpty(ageSrc)) {

			try {
				age = Integer.parseInt(ageSrc);
			} catch (Exception e) {
				return new ServerResponseBean(121, null);
			}
			if (age > 0) {
				userId.setAge(age);
				isUpdated = true;
			}
		}

		float log = 0;
		if (!StringUtils.isEmpty(longitude)) {
			try {
				log = Float.parseFloat(longitude);
			} catch (Exception e) {
				return new ServerResponseBean(121, null);
			}
			userId.setLongitude(log);
			isUpdated = true;
		}

		float lat = 0;
		if (!StringUtils.isEmpty(latitude)) {
			try {
				lat = Float.parseFloat(latitude);
			} catch (Exception e) {
				return new ServerResponseBean(121, null);
			}
			userId.setLatitude(lat);
			isUpdated = true;
		}

		if (!StringUtils.isEmpty(birthday)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date birth = null;
			try {
				birth = sdf.parse(birthday);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				return new ServerResponseBean(0x001, null);
			}
			userId.setBirthday(birth);
			isUpdated = true;
		}

		if (!StringUtils.isEmpty(userName)
				&& !userName.equals(userId.getName())) {
			userId.setName(userName);
			isUpdated = true;
		}

		if (!StringUtils.isEmpty(label)) {
			userId.setLabel(label);
			isUpdated = true;
		}

		if (!StringUtils.isEmpty(hide)) {
			userId.setHide(Integer.parseInt(hide));
			isUpdated = true;
		}

		if (!StringUtils.isEmpty(canbefollow)) {
			userId.setCanbefollow(Integer.parseInt(canbefollow));
			isUpdated = true;
		}

		if (!StringUtils.isEmpty(deviceToken)) {
			deviceToken = deviceToken.replaceAll(" ", "");
			userId.setDeviceToken(deviceToken);
			isUpdated = true;
		}
		if (!StringUtils.isEmpty(ahaStr)) {
			try {

				int aha = Integer.parseInt(ahaStr);
				if (aha >= 0) {
					userId.setAha(aha);
					isUpdated = true;
				}
			} catch (Exception e) {

			}

		}
		if (!StringUtils.isEmpty(remark)) {
			if (!remark.equals(userId.getRemark())) {
				userId.setRemark(remark);
				isUpdated = true;
			}
		}
		JSONObject returnObject = null;
		if (isUpdated) {
			userId.setLastUpdateTime(new Date());
			entityPersist.saveOrUpdate(userId);
			UserInfoBean bean = new UserInfoBean(userId);
			returnObject = JSONObject.fromObject(bean);

		}

		return new ServerResponseBean(200, returnObject);

	}

}
