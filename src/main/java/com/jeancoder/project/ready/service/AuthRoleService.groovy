package com.jeancoder.project.ready.service

import java.sql.Timestamp

import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.template.CarryJcDaoTemplate
import com.jeancoder.project.ready.dto.AuthRole
import com.jeancoder.project.ready.dto.AuthUser
import com.jeancoder.project.ready.dto.RoleFunction
import com.jeancoder.project.ready.dto.SysFunction
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.helper.GlobalHolder

class AuthRoleService extends CarryJcDaoTemplate<AuthRole> {
	
	RoleFunctionService role_func_service = RoleFunctionService.INSTANCE();
	
	static final AuthRoleService _instance_ = new AuthRoleService();
	
	public static AuthRoleService INSTANCE() {
		return _instance_;
	}
	
	public List<AuthRole> user_roles(AuthUser user) {
		if(user==null) {
			return null;
		}
		return this.user_roles_by_uid(user.id);
	}
	
	public List<AuthRole> user_roles_by_uid(def user_id) {
		String sql = "select * from AuthRole where flag!=? and id in (select role_id from UserRole where flag!=? and user_id=?)";
		return this.find(sql, -1, -1, user_id);
	}

	public JcPage<AuthRole> find_role(def pid = GlobalHolder.proj.id, JcPage<AuthRole> page, def select_project) {
		def sql = "select * from AuthRole where flag!=?";
		def params = []; params.add(-1);
		
		ProjectServiceCarry project_service = ProjectServiceCarry.INSTANCE();
		SysProjectInfo curr_project = project_service.get_proj(pid);
		
		if(curr_project.root!=1) {
			sql += ' and pid=?'; params.add(curr_project.id);
		} else {
			if(select_project!=null) {
				sql += ' and pid=?';
				params.add(select_project);
			}
		}
		
		page = this.find(page, sql, params.toArray());
		return page;
   }
   
   public AuthRole get_by_id(def id) {
	   if(!id) {
		   return null;
	   }
	   String sql = "select * from AuthRole where flag!=? and id=?";
	   return this.get(sql,-1, id);
   }
   
   public void add_role_function(AuthRole role, SysFunction func) {
	   String sql = "select * from RoleFunction where flag!=? and role_id=? and func_id=?";
	   List<RoleFunction> result = role_func_service.find(sql, -1, role.id, func.id);
	   if(result==null||result.empty) {
		   RoleFunction rf = new RoleFunction();
		   rf.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
		   rf.flag = 0;
		   rf.func_id = func.id;
		   rf.role_id = role.id;
		   this.save(rf);
	   }
   }
   
   public void remove_role_function(AuthRole role, SysFunction func) {
	   String sql = "select * from RoleFunction where flag!=? and role_id=? and func_id=?";
	   List<RoleFunction> result = role_func_service.find(sql, -1, role.id, func.id);
	   if(result!=null&&!result.empty) {
		   for(RoleFunction rf : result) {
			   rf.flag = -1;
			   this.update(rf);
		   }
	   }
   }
   
}
