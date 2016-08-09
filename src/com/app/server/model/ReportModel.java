package com.app.server.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
@Entity
@Table(name = "report_table")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class ReportModel {
	private Integer id;
	private Integer userInfoModelId;
	private UserInfoModel friendInfoModel;
	private int times;
	private Date commitTime;
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
	@Column(name = "userId", nullable = true, length = 255)
	public Integer getUserInfoModelId() {
		return userInfoModelId;
	}

	public void setUserInfoModelId(Integer userInfoModelId) {
		this.userInfoModelId = userInfoModelId;
	}

	@OneToOne
	@JoinColumn(name = "friendId")
	public UserInfoModel getFriendInfoModel() {
		return friendInfoModel;
	}

	public void setFriendInfoModel(UserInfoModel friendInfoModel) {
		this.friendInfoModel = friendInfoModel;
	}
	
	@Column(name = "commitTime", nullable = true, length = 255)
	public Date getCommitTime() {
		return commitTime;
	}

	public void setCommitTime(Date commitTime) {
		this.commitTime = commitTime;
	}

	@Column(name = "times")
	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

}
