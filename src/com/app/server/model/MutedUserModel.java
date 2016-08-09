package com.app.server.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
@Entity
@Table(name = "muted_user_table")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class MutedUserModel {
	private Integer id;
	private Integer userInfoModelId;
	private Date expireTime;
	private int mutedTimes;
	
	
	
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
	
	@Column(name = "expireTime", nullable = true, length = 255)
	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	
	@Column(name = "mutedTimes")
	public int getMutedTimes() {
		return mutedTimes;
	}

	public void setMutedTimes(int mutedTimes) {
		this.mutedTimes = mutedTimes;
	}

}
