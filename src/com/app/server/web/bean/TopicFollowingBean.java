package com.app.server.web.bean;

import java.io.Serializable;
import java.util.Date;

import com.app.server.model.TopicFollowingModel;

/**
 * 跟帖Model
 * 
 * @author howay
 * 
 */
public class TopicFollowingBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2415125449587178959L;
	private int userId;
	private String content;
	private Date lastUpdateTime;

	public TopicFollowingBean() {

	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
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
		TopicFollowingBean other = (TopicFollowingBean) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (lastUpdateTime == null) {
			if (other.lastUpdateTime != null)
				return false;
		} else if (!lastUpdateTime.equals(other.lastUpdateTime))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}


	public TopicFollowingBean(TopicFollowingModel model) {
		this.userId = model.getCreator().getId();
		this.content = model.getContent();
		this.lastUpdateTime = model.getLastUpdateTime();
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

}
