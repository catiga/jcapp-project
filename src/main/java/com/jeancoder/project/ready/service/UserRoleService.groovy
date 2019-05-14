package com.jeancoder.project.ready.service

import java.sql.Timestamp

import com.jeancoder.project.ready.dto.AuthRole
import com.jeancoder.project.ready.dto.AuthUser
import com.jeancoder.project.ready.dto.UserRole
import com.jeancoder.jdbc.template.CarryJcDaoTemplate

class UserRoleService extends CarryJcDaoTemplate<UserRole> {
	
	public static UserRoleService INSTANCE() {
		return new UserRoleService();
	}

	public void grant_user_role(AuthUser user, AuthRole role) {
		String sql = "select * from UserRole where flag!=? and user_id=? and role_id=?";
		List<UserRole> result = this.find(sql, -1, user.id, role.id);
		if(result==null||result.empty) {
			UserRole ur = new UserRole();
			ur.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
			ur.flag = 0;
			ur.role_id = role.id;
			ur.user_id = user.id;
			this.save(ur);
		}
	}
	
	public void remove_user_role(AuthUser user, AuthRole role) {
		String sql = "select * from UserRole where flag!=? and user_id=? and role_id=?";
		List<UserRole> result = this.find(sql, -1, user.id, role.id);
		if(result!=null&&!result.isEmpty()) {
			for(UserRole u_r : result) {
				u_r.flag = -1;
				this.update(u_r);
			}
		}
	}
}
