package com.jeancoder.project.ready.dto

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "sys_domain_object")
class DomainObject {
	
	@JCID
	BigInteger id;
	
	String dtype;
	
	String domain;
	
	String parent_domain;
	
	BigInteger oid;
	
	Timestamp c_time;
	
	Integer flag = 0;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getDtype() {
		return dtype;
	}

	public void setDtype(String dtype) {
		this.dtype = dtype;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getParent_domain() {
		return parent_domain;
	}

	public void setParent_domain(String parent_domain) {
		this.parent_domain = parent_domain;
	}

	public BigInteger getOid() {
		return oid;
	}

	public void setOid(BigInteger oid) {
		this.oid = oid;
	}

	public Timestamp getC_time() {
		return c_time;
	}

	public void setC_time(Timestamp c_time) {
		this.c_time = c_time;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
}
