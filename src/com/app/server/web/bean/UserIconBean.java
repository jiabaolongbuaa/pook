package com.app.server.web.bean;

import java.io.Serializable;

import com.app.server.model.UserInfoModel;

/**
 * �û�ͷ��Model
 * 
 * @author Simon
 */
public class UserIconBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6243414619288219596L;
	private int userId;
	private String iconPath;
	
	public UserIconBean() {

	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((iconPath == null) ? 0 : iconPath.hashCode());
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
		UserIconBean other = (UserIconBean) obj;
		if (iconPath == null) {
			if (other.iconPath != null)
				return false;
		} else if (!iconPath.equals(other.iconPath))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}


	public UserIconBean(UserInfoModel model) {
		this.userId = model.getId();
	
		this.iconPath = model.getImagePath();	
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}


	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}


}
