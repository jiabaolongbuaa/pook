package com.app.server.model;

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
@Table(name = "message")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class MessageModel {
	private Integer id;
	private String content;
	private String lastUpdateTime;
	private UserInfoModel sender;
	private UserInfoModel receiver;


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "content", nullable = true, length = 255)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "lastUpdateTime", nullable = true, length = 255)
	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	@OneToOne
	@JoinColumn(name = "senderUserId")
	public UserInfoModel getSender() {
		return sender;
	}

	public void setSender(UserInfoModel sender) {
		this.sender = sender;
	}

	@OneToOne
	@JoinColumn(name = "receiverUserId")
	public UserInfoModel getReceiver() {
		return receiver;
	}

	public void setReceiver(UserInfoModel receiver) {
		this.receiver = receiver;
	}

}
