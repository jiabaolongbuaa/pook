package com.app.server.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "topic_following_table")
public class TopicFollowingModel {
	private Integer id;
	private UserInfoModel creator;
	private Date lastUpdateTime;
	private String content;
	private TopicModel topicModel;


	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "topicId")
	public TopicModel getTopicModel() {
		return topicModel;
	}

	public void setTopicModel(TopicModel topicModel) {
		this.topicModel = topicModel;
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
	public UserInfoModel getCreator() {
		return creator;
	}

	public void setCreator(UserInfoModel creator) {
		this.creator = creator;
	}

	@Column(name = "lastUpdateTime", nullable = true, length = 255)
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	@Column(name = "content", nullable = true, length = 255)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
