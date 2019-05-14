package com.jeancoder.project.ready.service

import com.jeancoder.project.ready.dto.ProjectFrontConfig
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.template.CarryJcDaoTemplate

class ProjectFrontConfigService extends CarryJcDaoTemplate<ProjectFrontConfig> {
	
	static final ProjectFrontConfigService INSTANCE__ = new ProjectFrontConfigService();
	
	public static ProjectFrontConfigService INSTANCE() {
		return INSTANCE__;
	}

	public JcPage<ProjectFrontConfig> find_config(JcPage<ProjectFrontConfig> page, SysProjectInfo proj) {
		String sql = "select * from ProjectFrontConfig where flag!=? and project_id=? order by a_time desc";
		return this.find(page, sql, -1, proj.id);
	}
	
	public ProjectFrontConfig get_by_id(def id) {
		def sql = "select * from ProjectFrontConfig where flag!=? and id=?";
		return this.get(sql, -1, id);
	}
	
	public void set_user_grant(ProjectFrontConfig wx_config) {
		if(!(wx_config.app_type=='10' || wx_config.app_type=='20' || wx_config.app_type=='901')) {
			return;
		}
		if(wx_config.getUser_gran()!=null&&wx_config.getUser_gran()==1) {
			return;
		}
		String sql = "select * from ProjectFrontConfig where flag!=? and project_id=? and user_gran=?";
		List<ProjectFrontConfig> result = this.find(sql, -1, wx_config.project_id, 1);
		for(ProjectFrontConfig c : result) {
			c.user_gran = 0;
			this.update(c);
		}
		wx_config.user_gran = 1;
		this.update(wx_config);
	}
}
