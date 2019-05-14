package com.jeancoder.project.ready.dto

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "store_tag")
class StoreTag {

	@JCID
	BigInteger id;
	
	String code;
	
	String name;
	
	String tips;
	
	@JCForeign
	BigInteger store_id;
	
	@JCForeign
	BigInteger pid;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
}
