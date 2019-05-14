package com.jeancoder.project.ready.internal

import java.util.List

import com.jeancoder.jdbc.template.CarryJcDaoTemplate
import com.jeancoder.project.ready.dto.AuthRole
import com.jeancoder.project.ready.dto.AuthUser
import com.jeancoder.project.ready.service.RoleFunctionService

class AuthRoleService extends CarryJcDaoTemplate<AuthRole> {
	RoleFunctionService role_func_service = RoleFunctionService.INSTANCE();
	
	static final AuthRoleService _instance_ = new AuthRoleService();
	
	public static AuthRoleService INSTANCE() {
		return _instance_;
	}
	
	public List<AuthRole> user_roles(AuthUser user,def pid) {
		if(user==null) {
			return null;
		}
		String sql = "select * from AuthRole where flag!=? and pid=? and id in (select role_id from UserRole where flag!=? and user_id=?)";
		return this.find(sql, -1, pid, -1, user.id);
	}
}
