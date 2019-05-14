package com.jeancoder.project.ready.service

import java.sql.Timestamp

import com.jeancoder.project.ready.dto.ProjectMod
import com.jeancoder.project.ready.dto.SysFunction
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.jdbc.template.CarryJcDaoTemplate

class ProjectModService extends CarryJcDaoTemplate<ProjectMod> {

	public static ProjectModService INSTANCE() {
		return new ProjectModService();
   }
 
   public void add_project_function(SysProjectInfo project, SysFunction function) {
	   String sql = "select * from ProjectMod where flag!=? and proj_id=? and mod_id=?";
	   List<ProjectMod> result = this.find(sql, -1, project.id, function.id);
	   if(result==null||result.empty) {
		   ProjectMod pm = new ProjectMod();
		   pm.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
		   pm.c_time = pm.a_time;
		   pm.flag = 0;
		   pm.proj_id = project.id;
		   pm.mod_id = function.id;
		   this.save(pm);
	   }
   }
   
   public void remove_project_function(SysProjectInfo project, SysFunction func) {
	   String sql = "select * from ProjectMod where flag!=? and proj_id=? and mod_id=?";
	   List<ProjectMod> result = this.find(sql, -1, project.id, func.id);
	   if(result!=null&&!result.empty) {
		   for(ProjectMod pm : result) {
			   pm.flag = -1;
			   this.update(pm);
		   }
	   }
	   //同时删除这个项目对应的已经授权给角色的功能
	   sql = "update RoleFunction set flag=-1 where func_id=? and role_id in (select id from AuthRole where flag!=? and pid=?)";
	   RoleFunctionService role_func_service = RoleFunctionService.INSTANCE();
	   int code = role_func_service.batchExecute(sql, func.id, -1, project.id);
	   System.out.println(code);
   }
}
