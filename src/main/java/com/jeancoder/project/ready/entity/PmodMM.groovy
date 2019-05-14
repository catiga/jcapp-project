package com.jeancoder.project.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = 'ts_p_mod_mm')
class PmodMM {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger pid;
	
	String appcode;
	
	String func;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
}
