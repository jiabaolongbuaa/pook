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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "topic_table")
public class TopicModel {
	private Integer id;
	private UserInfoModel topicCreator;
	private int type;
	private String content;
	private Date lastUpdateTime;
	private float longitude;
	private float latitude;
	private List<TopicFollowingModel> followingList;
	private List<TopicLikeModel> likeList;
	
	@OneToMany(mappedBy = "topicModel", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	public List<TopicLikeModel> getLikeList() {
		return likeList;
	}

	public void setLikeList(List<TopicLikeModel> likeList) {
		this.likeList = likeList;
	}
	private boolean hideUser;

	@OneToMany(mappedBy = "topicModel", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	public List<TopicFollowingModel> getFollowingList() {
		return followingList;
	}

	public void setFollowingList(List<TopicFollowingModel> followingList) {
		this.followingList = followingList;
	}

	@Column(name = "longitude", nullable = true)
	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitute) {
		this.longitude = longitute;
	}

	@Column(name = "latitude", nullable = true)
	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@OneToOne
	@JoinColumn(name = "userInfoId")
	public UserInfoModel getTopicCreator() {
		return topicCreator;
	}

	public void setTopicCreator(UserInfoModel topicCreator) {
		this.topicCreator = topicCreator;
	}

	@Column(name = "content", nullable = true, length = 255)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "lastUpdateTime", nullable = true)
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	@Column(name = "hideUser", nullable = true)
	public boolean isHideUser() {
		return hideUser;
	}

	public void setHideUser(boolean hideUser) {
		this.hideUser = hideUser;
	}
	
	public int getType() {
		return type;
	}
	@Column(name = "type", nullable = true)
	public void setType(int type) {
		this.type = type;
	}

}
