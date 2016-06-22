package com.app.server.web.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.app.server.model.UserInfoModel;
import com.app.server.util.BirthdayCalculator;
import com.app.server.util.ConstellationUtil;

/**
 * 用户Model
 * 
 * @author howay
 * 
 */
public class UserInfoBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3388785183421089710L;
	private int userId;
	private String userName;
	private String iconPath;
	private int gender;
//	private Date birthday;
	

	private int age;
//	private String constellation;
	private String label;
	private String distance;
	private Date lastUpdateTime;
	private float longitude;
	private float latitude;
	private String deviceToken;
	private int isFriend;
	private int hide;
	private int canBefollow;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
//		result = prime * result
//				+ ((birthday == null) ? 0 : birthday.hashCode());
//		result = prime * result
//				+ ((constellation == null) ? 0 : constellation.hashCode());
		result = prime * result
				+ ((deviceToken == null) ? 0 : deviceToken.hashCode());
		result = prime * result
				+ ((iconPath == null) ? 0 : iconPath.hashCode());
		result = prime * result + isBlocked;
		result = prime * result + isFriend;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result
				+ ((lastUpdateTime == null) ? 0 : lastUpdateTime.hashCode());
		result = prime * result + Float.floatToIntBits(latitude);
		result = prime * result + Float.floatToIntBits(longitude);
		result = prime * result + userId;
		result = prime * result
				+ ((userImageList == null) ? 0 : userImageList.hashCode());
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
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
		UserInfoBean other = (UserInfoBean) obj;
		if (age != other.age)
			return false;
//		if (birthday == null) {
//			if (other.birthday != null)
//				return false;
//		} else if (!birthday.equals(other.birthday))
//			return false;
//		if (constellation == null) {
//			if (other.constellation != null)
//				return false;
//		} else if (!constellation.equals(other.constellation))
//			return false;
		if (deviceToken == null) {
			if (other.deviceToken != null)
				return false;
		} else if (!deviceToken.equals(other.deviceToken))
			return false;
		
		if (iconPath == null) {
			if (other.iconPath != null)
				return false;
		} else if (!iconPath.equals(other.iconPath))
			return false;
		if (isBlocked != other.isBlocked)
			return false;
		if (isFriend != other.isFriend)
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (lastUpdateTime == null) {
			if (other.lastUpdateTime != null)
				return false;
		} else if (!lastUpdateTime.equals(other.lastUpdateTime))
			return false;
		if (Float.floatToIntBits(latitude) != Float
				.floatToIntBits(other.latitude))
			return false;
		if (Float.floatToIntBits(longitude) != Float
				.floatToIntBits(other.longitude))
			return false;
		if (userId != other.userId)
			return false;
		if (userImageList == null) {
			if (other.userImageList != null)
				return false;
		} else if (!userImageList.equals(other.userImageList))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	private int isBlocked;

	private List<UserImageBean> userImageList;

	public UserInfoBean() {

	}

	public UserInfoBean(UserInfoModel model) {
		this.userId = model.getId();
		this.userName = model.getName();
		this.iconPath = model.getImagePath();
		this.gender = model.getGender();
		this.lastUpdateTime = model.getLastUpdateTime();
		this.latitude = model.getLatitude();
		this.longitude = model.getLongitude();
		
	//	this.birthday = model.getBirthday();
		
		
		this.age= model.getAge();//BirthdayCalculator.calculate(this.birthday);
	//	this.constellation=ConstellationUtil.calculateConstellation(this.birthday);
		
		this.label=model.getLabel();
		this.deviceToken=model.getDeviceToken();
		if (model.getUserImageList() != null) {
			List<UserImageBean> valueList = new ArrayList<UserImageBean>();
			for (int i = 0; i < model.getUserImageList().size(); i++) {
				valueList
						.add(new UserImageBean(model.getUserImageList().get(i)));
			}
			this.userImageList = valueList;
		}
		
		this.hide = model.getHide();
		this.canBefollow = model.getCanbefollow();

	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public List<UserImageBean> getUserImageList() {
		return userImageList;
	}

	public void setUserImageList(List<UserImageBean> userImageList) {
		this.userImageList = userImageList;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

//	public Date getBirthday() {
//		return birthday;
//	}
//
//	public void setBirthday(Date birthday) {
//		this.birthday = birthday;
//	}
//
//	public String getConstellation() {
//		return constellation;
//	}
//
//	public void setConstellation(String constellation) {
//		this.constellation = constellation;
//	}
	public int getIsFriend() {
		return isFriend;
	}

	public void setIsFriend(int isFriend) {
		this.isFriend = isFriend;
	}

	public int getIsBlocked() {
		return isBlocked;
	}

	public void setIsBlocked(int isBlocked) {
		this.isBlocked = isBlocked;
	}

	public int getHide() {
		return hide;
	}

	public void setHide(int hide) {
		this.hide = hide;
	}

	public int getCanBefollow() {
		return canBefollow;
	}

	public void setCanBefollow(int canBefollow) {
		this.canBefollow = canBefollow;
	}


}
