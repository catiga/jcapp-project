package com.jeancoder.project.ready.service

import com.jeancoder.project.ready.dto.AuthUser
import com.jeancoder.project.ready.dto.ProjectUser
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.helper.GlobalHolder
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.jdbc.template.CarryJcDaoTemplate

class AuthUserService extends CarryJcDaoTemplate<AuthUser> {
	
	final static AuthUserService __instance__ = new AuthUserService();
	
	ProjectUserService pus = ProjectUserService.INSTANCE();
	
	public static AuthUserService INSTANCE() {
		return __instance__;
   }
   
   def update_user_password(def user_id, def password) {
	   def sql = 'update AuthUser set password=? where id=?';
	   return JcTemplate.INSTANCE().batchExecute(sql, password, user_id);
   }
   
   public JcPage<AuthUser> find_user(def pid = GlobalHolder.proj.id, JcPage<AuthUser> page, def select_project,def name_moile) {
	   String sql = "select a.*, b.proj_name as pname from AuthUser a left join SysProjectInfo b on a.pid=b.id where a.flag!=?";
	   def param = [];
	   param.add(-1);
	   SysProjectInfo current_project = ProjectServiceCarry.INSTANCE().get_proj(pid);
	   if(current_project.root!=1) {
		   sql += ' and a.pid=?';
		   param.add(current_project.id);
	   } else {
		   if(select_project!=null) {
			   sql += ' and a.pid=?';
			   param.add(select_project);
		   }
	   }
	   if(name_moile){
		   sql += ' and (a.username=? or a.mobile=? )';
		   param.add(name_moile)
		   param.add(name_moile)
	   }
	   page = this.find(page, sql, param.toArray());
	   return page;
   }
   
   public AuthUser get_by_id(def pid = GlobalHolder.proj.id, def id) {
	   String sql = "select * from AuthUser where flag!=? and id=?";
	   def param = [];
	   param.add(-1); param.add(id);
	   SysProjectInfo current_project = GlobalHolder.getProj();
	   if(pid) {
		   current_project = ProjectServiceCarry.INSTANCE().get_proj(pid);
	   }
//	   if(current_project==null) {
//		   current_project = ProjectServiceCarry.INSTANCE().get_proj(pid);
//	   }
	   if(current_project.root!=1) {
		   sql += ' and pid=?';
		   param.add(current_project.id);
	   }
	   return this.get(sql, param.toArray());
   }
   
   public void update_user(AuthUser e) {
	   this.update(e);
   }
   
   public AuthUser auth(def pid = GlobalHolder.proj?.id, String username, String passwd) {
	   String sql = "select * from AuthUser where flag!=? and name=? and password=? and pid=?";
 	   List<AuthUser> users = this.find(sql, -1, username, passwd, pid);
	   if(users==null||users.empty) {
		   return null;
	   }
	   return users.get(0);
	}
	
	def find_user(String username,String mobile,JcPage<AuthUser> page){
		def param = [];
		String sql = "select * from AuthUser where flag!=?";
		param.add(-1);
		if(username!=null){
			sql+= " and username=?"; 
			param.add(username)
		}
		if(mobile!=null){
			sql+=" and mobile=?";
			param.add(mobile)
		}
		return this.find(page, sql, param.toArray());
	}
}
