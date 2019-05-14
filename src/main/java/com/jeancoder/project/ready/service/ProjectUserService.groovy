package com.jeancoder.project.ready.service

import java.sql.Timestamp

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.jdbc.template.CarryJcDaoTemplate
import com.jeancoder.project.ready.dto.AuthUser
import com.jeancoder.project.ready.dto.ProjectUser
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.helper.GlobalHolder

class ProjectUserService extends CarryJcDaoTemplate<ProjectUser> {
	
	JCLogger LOGGER = LoggerSource.getLogger(ProjectUserService.class.getName());

	static final ProjectUserService __instance__ = new ProjectUserService();
	
	static final JcTemplate jc_template = JcTemplate.INSTANCE();
	
	public static ProjectUserService INSTANCE() {
		return __instance__;
   }
   
   public ProjectUser project_users(AuthUser user) {
	   String sql = "select * from ProjectUser where flag!=? and proj_id=? and user_id=?";
	   List<ProjectUser> pu = this.find(sql, -1, GlobalHolder.proj.id, user.id);
	   if(pu==null||pu.empty) {
		   return null;
	   } else if(pu.size()>1) {
		   LOGGER.error("error:project_user_repeat. proj=" + GlobalHolder.proj + " user=" + user.id);
		   return null;
	   } else {
		   return pu.get(0);
	   }
   }
   
   void bind_project_user(SysProjectInfo project, AuthUser user) {
	   String sql = 'select * from ProjectUser where proj_id=? and user_id=? and flag=?';
	   ProjectUser pu = jc_template.get(ProjectUser.class, sql, project.id, user.id, -1);
	   if(pu!=null) {
		   return;
	   }
	   sql = 'update ProjectUser set flag=-1 where proj_id=? and user_id=?';
	   jc_template.batchExecute(sql, project.id, user.id);
	   pu = new ProjectUser();
	   pu.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	   pu.c_time = pu.a_time;
	   pu.flag = 0;
	   pu.proj_id = project.id;
	   pu.user_id = user.id;
	   jc_template.save(pu);
   }
}
