package com.jeancoder.project.ready.dto

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "sys_project_wx_config")
class ProjectFrontConfig {

	@JCID
	BigInteger id;
	
	BigInteger project_id;
	
	String app_name;
	
	String app_id;
	
	String app_key;
	
	String app_type;
	
	String app_token;
	
	String app_encode_key;
	
	String agent_id;
	
	Integer flag = 0;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Integer user_gran;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getProject_id() {
		return project_id;
	}

	public void setProject_id(BigInteger project_id) {
		this.project_id = project_id;
	}

	public String getApp_name() {
		return app_name;
	}

	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}

	public String getApp_id() {
		return app_id;
	}

	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	public String getApp_key() {
		return app_key;
	}

	public void setApp_key(String app_key) {
		this.app_key = app_key;
	}

	public String getApp_type() {
		return app_type;
	}

	public void setApp_type(String app_type) {
		this.app_type = app_type;
	}

	public String getApp_token() {
		return app_token;
	}

	public void setApp_token(String app_token) {
		this.app_token = app_token;
	}

	public String getApp_encode_key() {
		return app_encode_key;
	}

	public void setApp_encode_key(String app_encode_key) {
		this.app_encode_key = app_encode_key;
	}

	public String getAgent_id() {
		return agent_id;
	}

	public void setAgent_id(String agent_id) {
		this.agent_id = agent_id;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
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

	public Integer getUser_gran() {
		return user_gran;
	}

	public void setUser_gran(Integer user_gran) {
		this.user_gran = user_gran;
	}
	
}
