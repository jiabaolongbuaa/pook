package com.app.server.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "user")
public class UserInfoModel {
	
	public static final int SEX_UNKOWN = 0;
	public static final int SEX_MALE = 1;
	public static final int SEX_FEMALE =2;
	
	private Integer id;
	private String name;
	private int gender;
	private String phonenum;
	private String imagePath;
	private float longitude;
	private float latitude;
	private Date lastUpdateTime;
	private String email;
	private String password;
	private Date birthday;
	private int age;
	private String label;
	private String deviceToken;
	private List<UserImageModel> userImageList;
	
	private int hide;
	private int canbefollow;

	public UserInfoModel() {

	}

	
	public UserInfoModel(Integer id) {
		this.id = id;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "email", nullable = true, length = 255)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "password", nullable = true, length = 255)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name = "phonenum", nullable = true, length = 255)
	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}

	@Column(name = "lastUpdateTime", nullable = true, length = 255)
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	@Column(name = "longitude", nullable = true)
	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	@Column(name = "latitude", nullable = true)
	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	

	@Column(name = "userName", nullable = true, length = 255)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "gender")
	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	@Column(name = "userIcon", nullable = true, length = 255)
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	@OneToMany(mappedBy = "userInfoModel", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	public List<UserImageModel> getUserImageList() {
		return userImageList;
	}

	public void setUserImageList(List<UserImageModel> userImageList) {
		this.userImageList = userImageList;
	}
	@Column(name = "birthday", nullable = true)
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	@Column(name = "label", nullable = true, length = 255)
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	@Column(name = "devicetoken", nullable = true, length = 255)
	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	@Column(name = "hide")
	public int getHide() {
		return hide;
	}


	public void setHide(int hide) {
		this.hide = hide;
	}

	@Column(name = "canbefollow")
	public int getCanbefollow() {
		return canbefollow;
	}


	public void setCanbefollow(int canbefollow) {
		this.canbefollow = canbefollow;
	}

	@Column(name = "age")
	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}
	
	
	
}
