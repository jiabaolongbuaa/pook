package com.app.server.web.bean;

import java.io.Serializable;

import com.app.server.model.UserImageModel;



public class UserImageBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8480155033578754660L;
	private Integer id;
	private String imagePath;

	public UserImageBean() {

	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((imagePath == null) ? 0 : imagePath.hashCode());
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
		UserImageBean other = (UserImageBean) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (imagePath == null) {
			if (other.imagePath != null)
				return false;
		} else if (!imagePath.equals(other.imagePath))
			return false;
		return true;
	}



	public UserImageBean(UserImageModel model) {
		this.id = model.getId();
		this.imagePath = model.getImagePath();

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

}