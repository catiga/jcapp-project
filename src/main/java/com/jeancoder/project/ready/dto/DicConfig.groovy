package com.jeancoder.project.ready.dto

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "dic_common_config")
class DicConfig {

	@JCID
	BigInteger id;
	
	String cc_name;
	
	String cc_num;
	
	String cc_type;
	
	Timestamp c_time;
	
	Integer rollback;
	
	String jt;
	
	String entry;
	
	Integer flag;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getCc_name() {
		return cc_name;
	}

	public void setCc_name(String cc_name) {
		this.cc_name = cc_name;
	}

	public String getCc_num() {
		return cc_num;
	}

	public void setCc_num(String cc_num) {
		this.cc_num = cc_num;
	}

	public String getCc_type() {
		return cc_type;
	}

	public void setCc_type(String cc_type) {
		this.cc_type = cc_type;
	}

	public Timestamp getC_time() {
		return c_time;
	}

	public void setC_time(Timestamp c_time) {
		this.c_time = c_time;
	}

	public Integer getRollback() {
		return rollback;
	}

	public void setRollback(Integer rollback) {
		this.rollback = rollback;
	}

	public String getJt() {
		return jt;
	}

	public void setJt(String jt) {
		this.jt = jt;
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
}
