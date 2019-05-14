package com.jeancoder.project.ready.internal

import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.jdbc.template.CarryJcDaoTemplate
import com.jeancoder.project.ready.dto.AuthUser
import com.jeancoder.project.ready.support.security.MD5Util

class AuthUserService extends CarryJcDaoTemplate<AuthUser>  {

	final static AuthUserService __instance__ = new AuthUserService();
	
	public static AuthUserService INSTANCE() {
		return __instance__;
   }
	public AuthUser get_by_id(def id, def pid) {
		String sql = "select * from AuthUser where flag!=? and id=?";
		def param = [];
		param.add(-1); param.add(id);
		if(!pid.toString().equals("1")) {
			//sql += ' and id in (select user_id from ProjectUser where flag!=? and proj_id=?)';
			sql += ' and pid=?';
			param.add(pid);
		}
		return this.get(sql, param.toArray());
	}
	
	def check_password(def username, def userpwd, def pid) {
		def sql = 'select * from AuthUser where flag!=? and name=? and password=? and pid=?';
		AuthUser au = JcTemplate.INSTANCE().get(AuthUser, sql, -1, username, MD5Util.getMD5(userpwd), pid);
		return au;
	}
}
