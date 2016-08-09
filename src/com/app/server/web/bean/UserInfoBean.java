package com.app.server.web.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.app.server.model.UserInfoModel;
import com.app.server.util.BirthdayCalculator;
import com.app.server.util.ConfigSingleton;
import com.app.server.util.ConstellationUtil;

/**
 * 用户Model
 * 
 * @author howay
 * 
 */
public class UserInfoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3388785183421089710L;
	private int userId;
	private String userName;
	private String iconPath;
	private int gender;
	// private Date birthday;

	private int age;
	// private String constellation;
	private String label;
	private String distance;
	private long lastUpdateTime;
	private float longitude;
	private float latitude;
	private String deviceToken;
	private int isFriend;
	private int hide;
	private int canBefollow;
	private String phone;

	private int isBlocked;
	private int aha;
	private String remark;

	private List<UserImageBean> userImageList;

	public UserInfoBean() {

	}

	public UserInfoBean(UserInfoModel model) {
		this.userId = model.getId();
		this.phone = model.getPhonenum();
		this.userName = model.getName();
		this.iconPath = model.getImagePath();

		ConstantBean config =ConfigSingleton.getInstance();
	//String SAVE_FOLDER = config.getUserImageFolder();
		String LINK_HEADER = config.getUserIconLink();
		switch (model.getHide()) {
		case 1:
			this.iconPath = LINK_HEADER+"hide01.jpg";
			break;
		case 2:
			this.iconPath = LINK_HEADER+"hide02.jpg";
			break;
		case 3:
			this.iconPath = LINK_HEADER+"hide03.jpg";
			break;
		default:
			break;
		}

		this.gender = model.getGender();
		this.lastUpdateTime = model.getLastUpdateTime().getTime();
		this.latitude = model.getLatitude();
		this.longitude = model.getLongitude();

		// this.birthday = model.getBirthday();

		this.age = model.getAge();// BirthdayCalculator.calculate(this.birthday);
		// this.constellation=ConstellationUtil.calculateConstellation(this.birthday);

		this.label = model.getLabel();
		this.deviceToken = model.getDeviceToken();
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
		this.aha = model.getAha();
		this.remark = model.getRemark();

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

	public long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(long lastUpdateTime) {
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

	// public Date getBirthday() {
	// return birthday;
	// }
	//
	// public void setBirthday(Date birthday) {
	// this.birthday = birthday;
	// }
	//
	// public String getConstellation() {
	// return constellation;
	// }
	//
	// public void setConstellation(String constellation) {
	// this.constellation = constellation;
	// }
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getAha() {
		return aha;
	}

	public void setAha(int aha) {
		this.aha = aha;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
