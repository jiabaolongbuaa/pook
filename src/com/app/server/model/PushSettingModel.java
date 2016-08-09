package com.app.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
@Entity
@Table(name = "push_setting")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class PushSettingModel {
	private Integer id;
	private Integer userId;
	private boolean isAcceptNotification;
	private boolean isAcceptPMNotification;
	private boolean isDisplayNotificationDetail;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "userId", nullable = true, length = 255)
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}


	@Column(name = "acceptnotification")
	public boolean getIsAcceptNotification() {
		return isAcceptNotification;
	}

	public void setIsAcceptNotification(boolean isAcceptNotification) {
		this.isAcceptNotification = isAcceptNotification;
	}
	
	@Column(name = "acceptpmnotification")
	public boolean getIsAcceptPMNotification() {
		return isAcceptPMNotification;
	}

	public void setIsAcceptPMNotification(boolean isAcceptPMNotification) {
		this.isAcceptPMNotification = isAcceptPMNotification;
	}

	
	@Column(name = "displaydetail")
	public boolean getIsDisplayNotificationDetail() {
		return isDisplayNotificationDetail;
	}

	public void setIsDisplayNotificationDetail(boolean isDisplayNotificationDetail) {
		this.isDisplayNotificationDetail = isDisplayNotificationDetail;
	}


}
