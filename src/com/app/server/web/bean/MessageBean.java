package com.app.server.web.bean;

import java.io.Serializable;

import com.app.server.model.MessageModel;

public class MessageBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7385464701267408753L;
	private int id;
	private String content;
	private String lastUpdateTime;
	private int senderUserId;
	private String senderUserName;
	private String senderIconPath;
	private int receiverUserId;
	private String receiverUserName;
	private String receiverIconPath;

	public MessageBean() {

	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((lastUpdateTime == null) ? 0 : lastUpdateTime.hashCode());
		result = prime
				* result
				+ ((receiverIconPath == null) ? 0 : receiverIconPath.hashCode());
		result = prime * result + receiverUserId;
		result = prime
				* result
				+ ((receiverUserName == null) ? 0 : receiverUserName.hashCode());
		result = prime * result
				+ ((senderIconPath == null) ? 0 : senderIconPath.hashCode());
		result = prime * result + senderUserId;
		result = prime * result
				+ ((senderUserName == null) ? 0 : senderUserName.hashCode());
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
		MessageBean other = (MessageBean) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (id != other.id)
			return false;
		if (lastUpdateTime == null) {
			if (other.lastUpdateTime != null)
				return false;
		} else if (!lastUpdateTime.equals(other.lastUpdateTime))
			return false;
		if (receiverIconPath == null) {
			if (other.receiverIconPath != null)
				return false;
		} else if (!receiverIconPath.equals(other.receiverIconPath))
			return false;
		if (receiverUserId != other.receiverUserId)
			return false;
		if (receiverUserName == null) {
			if (other.receiverUserName != null)
				return false;
		} else if (!receiverUserName.equals(other.receiverUserName))
			return false;
		if (senderIconPath == null) {
			if (other.senderIconPath != null)
				return false;
		} else if (!senderIconPath.equals(other.senderIconPath))
			return false;
		if (senderUserId != other.senderUserId)
			return false;
		if (senderUserName == null) {
			if (other.senderUserName != null)
				return false;
		} else if (!senderUserName.equals(other.senderUserName))
			return false;
		return true;
	}


	public MessageBean(MessageModel model) {
		this.id = model.getId();
		this.content = model.getContent();
		this.lastUpdateTime = model.getLastUpdateTime();
		this.senderUserId = model.getSender().getId();
		this.senderUserName = model.getSender().getName();
		this.senderIconPath = model.getSender().getImagePath();
		this.receiverUserId = model.getReceiver().getId();
		this.receiverUserName = model.getReceiver().getName();
		this.receiverIconPath = model.getReceiver().getImagePath();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public int getSenderUserId() {
		return senderUserId;
	}

	public void setSenderUserId(int senderUserId) {
		this.senderUserId = senderUserId;
	}

	public String getSenderUserName() {
		return senderUserName;
	}

	public void setSenderUserName(String senderUserName) {
		this.senderUserName = senderUserName;
	}

	public String getSenderIconPath() {
		return senderIconPath;
	}

	public void setSenderIconPath(String senderIconPath) {
		this.senderIconPath = senderIconPath;
	}

	public int getReceiverUserId() {
		return receiverUserId;
	}

	public void setReceiverUserId(int receiverUserId) {
		this.receiverUserId = receiverUserId;
	}

	public String getReceiverUserName() {
		return receiverUserName;
	}

	public void setReceiverUserName(String receiverUserName) {
		this.receiverUserName = receiverUserName;
	}

	public String getReceiverIconPath() {
		return receiverIconPath;
	}

	public void setReceiverIconPath(String receiverIconPath) {
		this.receiverIconPath = receiverIconPath;
	}

}
