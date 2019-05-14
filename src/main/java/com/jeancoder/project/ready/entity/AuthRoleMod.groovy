package com.jeancoder.project.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = 't_admin_role_mod_mm')
class AuthRoleMod {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger role_id;
	
	String func;
	
	String appcode;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
}
