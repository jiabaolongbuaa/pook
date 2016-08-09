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
@Table(name = "activecode")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class ActiveCodeModel {
	private Integer id;
	private String phonenum;
	private String code;
	private Date endtime;
	private Date resendtime;
	private int pass;
	


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	@Column(name = "phonenum", nullable = true, length = 255)
	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}

	@Column(name = "code", nullable = true, length = 255)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "endtime")
	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	
	

	@Column(name = "resendtime")
	public Date getResendtime() {
		return resendtime;
	}

	public void setResendtime(Date resendtime) {
		this.resendtime = resendtime;
	}

	@Column(name = "pass")
	public int getPass() {
		return pass;
	}

	public void setPass(int pass) {
		this.pass = pass;
	}

	
	
}
