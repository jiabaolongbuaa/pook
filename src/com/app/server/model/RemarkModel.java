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
@Table(name = "remark")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class RemarkModel {
	private Integer id;
	private Integer userInfoModelId;
	private UserInfoModel friendInfoModel;
	private String remark;
	
	
	
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
	
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
