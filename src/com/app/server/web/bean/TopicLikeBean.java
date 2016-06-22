package com.app.server.web.bean;

import java.io.Serializable;
import java.util.Date;

import com.app.server.model.TopicLikeModel;

/**
 * 跟帖Model
 * 
 * @author howay
 * 
 */
public class TopicLikeBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7463232920200471487L;
	private int userId;
	private Date lastUpdateTime;

	public TopicLikeBean() {

	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((lastUpdateTime == null) ? 0 : lastUpdateTime.hashCode());
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
		TopicLikeBean other = (TopicLikeBean) obj;
		if (lastUpdateTime == null) {
			if (other.lastUpdateTime != null)
				return false;
		} else if (!lastUpdateTime.equals(other.lastUpdateTime))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}



	public TopicLikeBean(TopicLikeModel model) {
		this.userId = model.getCreator().getId();
		this.lastUpdateTime = model.getLastUpdateTime();
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}


	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

}
