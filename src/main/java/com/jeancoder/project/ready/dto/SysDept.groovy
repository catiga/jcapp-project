package com.jeancoder.project.ready.dto

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "sys_dept")
class SysDept {

	@JCID
	BigInteger id;
	
	BigInteger org;
	
	String name;
	
	String code;
	
	String info;
	
	BigInteger parent;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
	
	Integer level;
	
	Boolean hasson;
	
	/**
	 * 1:部门
	 * 2:岗位
	 */
	Integer type = 1;

	BigInteger pid;
	
	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getOrg() {
		return org;
	}

	public void setOrg(BigInteger org) {
		this.org = org;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public BigInteger getParent() {
		return parent;
	}

	public void setParent(BigInteger parent) {
		this.parent = parent;
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

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Boolean getHasson() {
		return hasson;
	}

	public void setHasson(Boolean hasson) {
		this.hasson = hasson;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public BigInteger getPid() {
		return pid;
	}

	public void setPid(BigInteger pid) {
		this.pid = pid;
	}
	
}
