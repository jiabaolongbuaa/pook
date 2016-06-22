package com.app.server.web.bean;

import java.io.Serializable;

public class ConstantBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 954645713052999822L;
	
	private String openfireMySQLUrl;
	private String openfireMySQLUsername;
	private String openfireMySQLPassword;

	//<!--user icon configuration here -->
	private String userIconFolder;
	private String userIconLink;


	//<!--user image configuration here -->
	private String userImageFolder;
	private String userImageLink;

	//<!--push related configuration here -->
	private String keystore;
	private String keyPassword;
	private String isProduction;
	private String threadNumber;

	//<!-- block related configuration here -->
	private String duration;
	private String times;

	//<!-- SMS related configration here -->
	private String host;
	private String accId;
	private String accName;
	private String accPwd;
	private String content;
	private String dataType;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accId == null) ? 0 : accId.hashCode());
		result = prime * result + ((accName == null) ? 0 : accName.hashCode());
		result = prime * result + ((accPwd == null) ? 0 : accPwd.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result
				+ ((dataType == null) ? 0 : dataType.hashCode());
		result = prime * result
				+ ((duration == null) ? 0 : duration.hashCode());
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result
				+ ((isProduction == null) ? 0 : isProduction.hashCode());
		result = prime * result
				+ ((keyPassword == null) ? 0 : keyPassword.hashCode());
		result = prime * result
				+ ((keystore == null) ? 0 : keystore.hashCode());
		result = prime
				* result
				+ ((openfireMySQLPassword == null) ? 0 : openfireMySQLPassword
						.hashCode());
		result = prime
				* result
				+ ((openfireMySQLUrl == null) ? 0 : openfireMySQLUrl.hashCode());
		result = prime
				* result
				+ ((openfireMySQLUsername == null) ? 0 : openfireMySQLUsername
						.hashCode());
		result = prime * result
				+ ((threadNumber == null) ? 0 : threadNumber.hashCode());
		result = prime * result + ((times == null) ? 0 : times.hashCode());
		result = prime * result
				+ ((userIconFolder == null) ? 0 : userIconFolder.hashCode());
		result = prime * result
				+ ((userIconLink == null) ? 0 : userIconLink.hashCode());
		result = prime * result
				+ ((userImageFolder == null) ? 0 : userImageFolder.hashCode());
		result = prime * result
				+ ((userImageLink == null) ? 0 : userImageLink.hashCode());
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
		ConstantBean other = (ConstantBean) obj;
		if (accId == null) {
			if (other.accId != null)
				return false;
		} else if (!accId.equals(other.accId))
			return false;
		if (accName == null) {
			if (other.accName != null)
				return false;
		} else if (!accName.equals(other.accName))
			return false;
		if (accPwd == null) {
			if (other.accPwd != null)
				return false;
		} else if (!accPwd.equals(other.accPwd))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (dataType == null) {
			if (other.dataType != null)
				return false;
		} else if (!dataType.equals(other.dataType))
			return false;
		if (duration == null) {
			if (other.duration != null)
				return false;
		} else if (!duration.equals(other.duration))
			return false;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (isProduction == null) {
			if (other.isProduction != null)
				return false;
		} else if (!isProduction.equals(other.isProduction))
			return false;
		if (keyPassword == null) {
			if (other.keyPassword != null)
				return false;
		} else if (!keyPassword.equals(other.keyPassword))
			return false;
		if (keystore == null) {
			if (other.keystore != null)
				return false;
		} else if (!keystore.equals(other.keystore))
			return false;
		if (openfireMySQLPassword == null) {
			if (other.openfireMySQLPassword != null)
				return false;
		} else if (!openfireMySQLPassword.equals(other.openfireMySQLPassword))
			return false;
		if (openfireMySQLUrl == null) {
			if (other.openfireMySQLUrl != null)
				return false;
		} else if (!openfireMySQLUrl.equals(other.openfireMySQLUrl))
			return false;
		if (openfireMySQLUsername == null) {
			if (other.openfireMySQLUsername != null)
				return false;
		} else if (!openfireMySQLUsername.equals(other.openfireMySQLUsername))
			return false;
		if (threadNumber == null) {
			if (other.threadNumber != null)
				return false;
		} else if (!threadNumber.equals(other.threadNumber))
			return false;
		if (times == null) {
			if (other.times != null)
				return false;
		} else if (!times.equals(other.times))
			return false;
		if (userIconFolder == null) {
			if (other.userIconFolder != null)
				return false;
		} else if (!userIconFolder.equals(other.userIconFolder))
			return false;
		if (userIconLink == null) {
			if (other.userIconLink != null)
				return false;
		} else if (!userIconLink.equals(other.userIconLink))
			return false;
		if (userImageFolder == null) {
			if (other.userImageFolder != null)
				return false;
		} else if (!userImageFolder.equals(other.userImageFolder))
			return false;
		if (userImageLink == null) {
			if (other.userImageLink != null)
				return false;
		} else if (!userImageLink.equals(other.userImageLink))
			return false;
		return true;
	}
	public String getOpenfireMySQLUrl() {
		return openfireMySQLUrl;
	}
	public void setOpenfireMySQLUrl(String openfireMySQLUrl) {
		this.openfireMySQLUrl = openfireMySQLUrl;
	}
	public String getOpenfireMySQLUsername() {
		return openfireMySQLUsername;
	}
	public void setOpenfireMySQLUsername(String openfireMySQLUsername) {
		this.openfireMySQLUsername = openfireMySQLUsername;
	}
	public String getOpenfireMySQLPassword() {
		return openfireMySQLPassword;
	}
	public void setOpenfireMySQLPassword(String openfireMySQLPassword) {
		this.openfireMySQLPassword = openfireMySQLPassword;
	}
	public String getUserIconFolder() {
		return userIconFolder;
	}
	public void setUserIconFolder(String userIconFolder) {
		this.userIconFolder = userIconFolder;
	}
	public String getUserIconLink() {
		return userIconLink;
	}
	public void setUserIconLink(String userIconLink) {
		this.userIconLink = userIconLink;
	}
	public String getUserImageFolder() {
		return userImageFolder;
	}
	public void setUserImageFolder(String userImageFolder) {
		this.userImageFolder = userImageFolder;
	}
	public String getUserImageLink() {
		return userImageLink;
	}
	public void setUserImageLink(String userImageLink) {
		this.userImageLink = userImageLink;
	}
	public String getKeystore() {
		return keystore;
	}
	public void setKeystore(String keystore) {
		this.keystore = keystore;
	}
	public String getKeyPassword() {
		return keyPassword;
	}
	public void setKeyPassword(String keyPassword) {
		this.keyPassword = keyPassword;
	}
	public String getIsProduction() {
		return isProduction;
	}
	public void setIsProduction(String isProduction) {
		this.isProduction = isProduction;
	}
	public String getThreadNumber() {
		return threadNumber;
	}
	public void setThreadNumber(String threadNumber) {
		this.threadNumber = threadNumber;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getTimes() {
		return times;
	}
	public void setTimes(String times) {
		this.times = times;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getAccId() {
		return accId;
	}
	public void setAccId(String accId) {
		this.accId = accId;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public String getAccPwd() {
		return accPwd;
	}
	public void setAccPwd(String accPwd) {
		this.accPwd = accPwd;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
}
