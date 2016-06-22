package com.app.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
@Entity
@Table(name = "friend_relation_table")
public class FriendRelationModel {
	private Integer id;
	private Integer userInfoModelId;
	private UserInfoModel friendInfoModel;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "userInfoId", nullable = true, length = 255)
	public Integer getUserInfoModelId() {
		return userInfoModelId;
	}

	public void setUserInfoModelId(Integer userInfoModelId) {
		this.userInfoModelId = userInfoModelId;
	}

	@OneToOne
	@JoinColumn(name = "friendUserInfoId")
	public UserInfoModel getFriendInfoModel() {
		return friendInfoModel;
	}

	public void setFriendInfoModel(UserInfoModel friendInfoModel) {
		this.friendInfoModel = friendInfoModel;
	}

}
