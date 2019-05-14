package com.jeancoder.project.ready.dto

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "t_admin_role_function")
class RoleFunction {

	@JCID
	BigInteger id;
	
	BigInteger role_id;
	
	BigInteger func_id;
	
	Timestamp c_time;
	
	Integer flag = 0;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getRole_id() {
		return role_id;
	}

	public void setRole_id(BigInteger role_id) {
		this.role_id = role_id;
	}

	public BigInteger getFunc_id() {
		return func_id;
	}

	public void setFunc_id(BigInteger func_id) {
		this.func_id = func_id;
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
