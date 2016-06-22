package com.app.server.web.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.app.server.model.PushSettingModel;
import com.app.server.model.UserInfoModel;
import com.app.server.util.BirthdayCalculator;
import com.app.server.util.ConstellationUtil;

/**
 * 用户Model
 * 
 * @author howay
 * 
 */
public class PushSettingBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4365025145269731860L;
	private int userId;
	/**
	 * 
	 */
	private boolean isAcceptNotification;
	
	private boolean isAcceptPMNotification;
	private boolean isDisplayDetail;
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isAcceptNotification ? 1231 : 1237);
		result = prime * result + (isAcceptPMNotification ? 1231 : 1237);
		result = prime * result + (isDisplayDetail ? 1231 : 1237);
		result = prime * result + userId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PushSettingBean other = (PushSettingBean) obj;
		if (isAcceptNotification != other.isAcceptNotification)
			return false;
		if (isAcceptPMNotification != other.isAcceptPMNotification)
			return false;
		if (isDisplayDetail != other.isDisplayDetail)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	public PushSettingBean() {

	}

	public PushSettingBean(PushSettingModel model) {
		this.userId = model.getId();
		this.isAcceptNotification = model.getIsAcceptNotification();
		this.isAcceptPMNotification = model.getIsAcceptPMNotification();
		this.isDisplayDetail = model.getIsDisplayNotificationDetail();	

	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public boolean isAcceptNotification() {
		return isAcceptNotification;
	}

	public void setAcceptNotification(boolean isAcceptNotification) {
		this.isAcceptNotification = isAcceptNotification;
	}

	public boolean isAcceptPMNotification() {
		return isAcceptPMNotification;
	}

	public void setAcceptPMNotification(boolean isAcceptPMNotification) {
		this.isAcceptPMNotification = isAcceptPMNotification;
	}

	public boolean isDisplayDetail() {
		return isDisplayDetail;
	}

	public void setDisplayDetail(boolean isDisplayDetail) {
		this.isDisplayDetail = isDisplayDetail;
	}

}
