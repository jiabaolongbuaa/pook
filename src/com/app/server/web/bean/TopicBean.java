package com.app.server.web.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.app.server.model.TopicFollowingModel;
import com.app.server.model.TopicModel;

/**
 * 话题Model
 * 
 * @author howay
 * 
 */
public class TopicBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2416737558941222115L;
	private int id;
	private int userId;
	private float distance;
	private String content;
	private int type;
	private float longitude;
	private float latitude;
	private int numofLikes;
	private boolean isILiked;

	private List<TopicLikeBean> likeList;
	private Date lastUpdateTime;
	private boolean hideUser;
	private List<TopicFollowingBean> followingList;

	public TopicBean() {

	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + Float.floatToIntBits(distance);
		result = prime * result
				+ ((followingList == null) ? 0 : followingList.hashCode());
		result = prime * result + (hideUser ? 1231 : 1237);
		result = prime * result + id;
		result = prime * result + (isILiked ? 1231 : 1237);
		result = prime * result
				+ ((lastUpdateTime == null) ? 0 : lastUpdateTime.hashCode());
		result = prime * result + Float.floatToIntBits(latitude);
		result = prime * result
				+ ((likeList == null) ? 0 : likeList.hashCode());
		result = prime * result + Float.floatToIntBits(longitude);
		result = prime * result + numofLikes;
		result = prime * result + type;
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
		TopicBean other = (TopicBean) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (Float.floatToIntBits(distance) != Float
				.floatToIntBits(other.distance))
			return false;
		if (followingList == null) {
			if (other.followingList != null)
				return false;
		} else if (!followingList.equals(other.followingList))
			return false;
		if (hideUser != other.hideUser)
			return false;
		if (id != other.id)
			return false;
		if (isILiked != other.isILiked)
			return false;
		if (lastUpdateTime == null) {
			if (other.lastUpdateTime != null)
				return false;
		} else if (!lastUpdateTime.equals(other.lastUpdateTime))
			return false;
		if (Float.floatToIntBits(latitude) != Float
				.floatToIntBits(other.latitude))
			return false;
		if (likeList == null) {
			if (other.likeList != null)
				return false;
		} else if (!likeList.equals(other.likeList))
			return false;
		if (Float.floatToIntBits(longitude) != Float
				.floatToIntBits(other.longitude))
			return false;
		if (numofLikes != other.numofLikes)
			return false;
		if (type != other.type)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}



	public TopicBean(TopicModel model) {
		this.id = model.getId();
		this.userId = model.getTopicCreator().getId();
		this.content = model.getContent();
		this.longitude = model.getLongitude();
		this.latitude = model.getLatitude();
		this.lastUpdateTime = model.getLastUpdateTime();
		this.hideUser = model.isHideUser();
		this.type = model.getType();

		if (model.getLikeList() != null) {
			List<TopicLikeBean> valueList = new ArrayList<TopicLikeBean>();
			for (int i = 0; i < model.getLikeList().size(); i++) {
				valueList.add(new TopicLikeBean(model.getLikeList().get(i)));
			}
			this.likeList = valueList;
			this.numofLikes = valueList.size();
			
			int flag=0;
			for (int i = 0; i < model.getLikeList().size(); i++) {
				if(model.getLikeList().get(i).getCreator().getId()==userId){
					flag=1;
					break;
				}
			}
			if(flag==0){
				this.isILiked=false;
			}else{
				this.isILiked=true;
			}
//			if(model.getLikeList().contains(userId)){
//				this.isILiked=true;
//			}else{
//				this.isILiked=false;
//			}
		}

		if (model.getFollowingList() != null) {
			List<TopicFollowingBean> valueList = new ArrayList<TopicFollowingBean>();
			for (int i = 0; i < model.getFollowingList().size(); i++) {
				valueList.add(new TopicFollowingBean(model.getFollowingList()
						.get(i)));
			}
			this.followingList = valueList;
		}

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public boolean isHideUser() {
		return hideUser;
	}

	public void setHideUser(boolean hideUser) {
		this.hideUser = hideUser;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<TopicFollowingBean> getFollowingList() {
		return followingList;
	}

	public void setFollowingList(List<TopicFollowingBean> followingList) {
		this.followingList = followingList;
	}

	public int getNumofLikes() {
		return numofLikes;
	}

	public void setNumofLikes(int numofLikes) {
		this.numofLikes = numofLikes;
	}

	public List<TopicLikeBean> getLikeList() {
		return likeList;
	}

	public void setLikeList(List<TopicLikeBean> likeList) {
		this.likeList = likeList;
	}


	public boolean isILiked() {
		return isILiked;
	}

	public void setILiked(boolean isILiked) {
		this.isILiked = isILiked;
	}

}
