package com.jeancoder.project.ready.dto

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "ts_project_mod")
class ProjectMod {

	@JCID
	BigInteger id;
	
	BigInteger proj_id;
	
	BigInteger mod_id;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getProj_id() {
		return proj_id;
	}

	public void setProj_id(BigInteger proj_id) {
		this.proj_id = proj_id;
	}

	public BigInteger getMod_id() {
		return mod_id;
	}

	public void setMod_id(BigInteger mod_id) {
		this.mod_id = mod_id;
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
	
}
