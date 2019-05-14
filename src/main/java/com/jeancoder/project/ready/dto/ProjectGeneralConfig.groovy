package com.jeancoder.project.ready.dto

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "sys_project_support_config")
class ProjectGeneralConfig {
	
	@JCID
	BigInteger id;

	@JCForeign
	BigInteger proj_id;
	
	String disname;
	
	String sc_code_cat;
	
	String sc_code;
	
	String sc_name;
	
	String sc_info;
	
	String sc_type;
	
	String sc_jt;
	
	Timestamp c_time;
	
	Integer flag;
	
	String partner;
	
	String mchid;
	
	String pri_key;
	
	Integer pri_key_type;
	
	String pri_k_p;
	
	String pub_key;
	
	Integer pub_key_type;
	
	String pub_k_p;
	
	Integer rollback;
	
	String pri_key_format;
	
	String pub_key_format;
	
	String acc_fld;
	
	Integer rb_key_type;
	
	String rb_key_format;
	
	String rb_key;
	
	String rb_kp;
	
	String rb_file;
	
	BigInteger paro;

	boolean self_check() {
		def check_pass = true;
		if(sc_code!=null) {
			if(sc_code.startsWith("20")) {
				if(!partner||!mchid||!pri_key) {
					check_pass = false;
				}
			} else if(sc_code.startsWith("30")) {
				if(!partner||!pri_key||!pub_key) {
					check_pass = false;
				}
			}
		}
		return check_pass;
	}
}
