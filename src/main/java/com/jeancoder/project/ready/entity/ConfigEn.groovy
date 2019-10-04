package com.jeancoder.project.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = 't_sys_conf')
class ConfigEn {

	@JCID
	BigInteger id;
	
	String code;
	
	String title;
	
	String info;
	
	Date a_time;
	
	Timestamp c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	
	Integer flag = 0;
	
	String ss = '00';
	
	String config;
}
