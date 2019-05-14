package com.jeancoder.project.ready.dto

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "sys_merchant_info")
class SysMerchantInfo {

	@JCID
	private BigInteger id;
	
	private String name;
	
	private String info;
	
	private Timestamp a_time;
	
	private Timestamp c_time;
	
	private BigInteger merch_id;
	
	private String s_conf;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Timestamp getA_time() {
		return a_time;
	}

	public void setA_time(Timestamp a_time) {
		this.a_time = a_time;
	}

	public Timestamp getC_time() {
		return c_time;
	}

	public void setC_time(Timestamp c_time) {
		this.c_time = c_time;
	}

	public BigInteger getMerch_id() {
		return merch_id;
	}

	public void setMerch_id(BigInteger merch_id) {
		this.merch_id = merch_id;
	}

	public String getS_conf() {
		return s_conf;
	}

	public void setS_conf(String s_conf) {
		this.s_conf = s_conf;
	}
	
}