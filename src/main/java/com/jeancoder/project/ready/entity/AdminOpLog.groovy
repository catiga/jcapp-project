package com.jeancoder.project.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = 'rd_admg_ms')
class AdminOpLog {
	
	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger proj_id;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
	
	BigInteger uk;
	
	String un;
	
	String res;
	
	String ud;
	
	String addr;
	
	String exeres;
	
	String execode;
	
}
