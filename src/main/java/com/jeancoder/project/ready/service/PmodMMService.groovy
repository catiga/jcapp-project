package com.jeancoder.project.ready.service

import java.sql.Timestamp

import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.project.ready.appfunc.AppFunction
import com.jeancoder.project.ready.dto.AuthRole
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.entity.AuthRoleMod
import com.jeancoder.project.ready.entity.PmodMM

class PmodMMService {

	private static final PmodMMService _instance_ = new PmodMMService();

	JcTemplate jc_template = JcTemplate.INSTANCE();

	public static PmodMMService INSTANCE() {
		return _instance_;
	}
	
	def add_project_function(SysProjectInfo project, AppFunction mod) {
		String sql = 'select * from PmodMM where flag!=? and pid=? and appcode=? and func=?';
		def result = jc_template.find(PmodMM, sql, -1, project.id, mod.appcode, mod.unicode);
		if(!result) {
			Timestamp ts = new Timestamp(Calendar.getInstance().getTimeInMillis());
			PmodMM e = new PmodMM(pid:project.id, appcode:mod.appcode, func:mod.unicode, a_time:ts, c_time:ts, flag:0);
			jc_template.save(e);
		}
	}

	def remove_project_function(SysProjectInfo project, AppFunction mod) {
		String sql = "select * from PmodMM where flag!=? and pid=? and appcode=? and func=?";
		def result = jc_template.find(PmodMM, sql, -1, project.id, mod.appcode, mod.unicode);
		if(result) {
			result.each({it.flag=-1; jc_template.update(it)});
		}
		//同时删除这个项目对应的已经授权给角色的功能
		sql = 'update AuthRoleMod set flag=-1 where func=? and appcode=? and flag=? and role_id in (select id from AuthRole where flag!=? and pid=?)';
		jc_template.batchExecute(sql, mod.unicode, mod.appcode, 0, -1, project.id);
		
//		sql = "update RoleFunction set flag=-1 where func_id=? and role_id in (select id from AuthRole where flag!=? and pid=?)";
//		RoleFunctionService role_func_service = RoleFunctionService.INSTANCE();
//		int code = role_func_service.batchExecute(sql, func.id, -1, project.id);
//		System.out.println(code);
	}
	
	def find_role_func_code(AuthRole role, String mod) {
		if(role==null) {
			return null;
		}
		String sql = "select func from AuthRoleMod where flag!=? and role_id=? and appcode=?";
		return jc_template.find(String, sql, -1, role.id, mod);
	}

	
	def find_project_functions(SysProjectInfo project, String appcode) {
		String sql = 'select func from PmodMM where flag!=? and pid=? and appcode=?';
		def result = jc_template.find(String, sql, -1, project.id, appcode);
		return result;
	}

	def add_role_function(AuthRole role, AppFunction func) {
		String sql = "select * from AuthRoleMod where flag!=? and role_id=? and func=? and appcode=?";
		List<AuthRoleMod> result = jc_template.find(AuthRoleMod, sql, -1, role.id, func.unicode, func.appcode);
		if(result==null||result.empty) {
			AuthRoleMod rf = new AuthRoleMod();
			rf.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
			rf.a_time = rf.c_time;
			rf.flag = 0;
			rf.func = func.unicode;
			rf.role_id = role.id;
			rf.appcode = func.appcode;
			jc_template.save(rf);
		}
	}
	
	def remove_role_function(AuthRole role, AppFunction func) {
		String sql = "select * from AuthRoleMod where flag!=? and role_id=? and func=? and appcode=?";
		List<AuthRoleMod> result = jc_template.find(AuthRoleMod, sql, -1, role.id, func.unicode, func.appcode);
		if(result!=null&&!result.empty) {
			for(AuthRoleMod rf : result) {
				rf.flag = -1;
				jc_template.update(rf);
			}
		}
	}

	public List<String> find_roles_functions(String appcode, List<AuthRole> roles) {
		if(!roles) {
			return null;
		}
		String sql = "select func from AuthRoleMod where flag!=? and appcode=? and role_id in ("; //?))
		
		def ids = [-1, appcode];
		def ask_plus = '';
		roles.each{
			ids.add(it.id);
			ask_plus+='?,';
		}
		sql = sql + ask_plus[0..-2] + ')';
		return jc_template.find(String, sql, ids.toArray());
	}
}
