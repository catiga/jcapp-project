package com.jeancoder.project.ready.service

import java.util.List

import com.jeancoder.project.ready.dto.ProjectGeneralConfig
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.helper.GlobalHolder
import com.jeancoder.jdbc.template.CarryJcDaoTemplate

class ProjectGeneralConfigService extends CarryJcDaoTemplate<ProjectGeneralConfig> {

	static final ProjectGeneralConfigService _instance_ = new ProjectGeneralConfigService();
	
	public static ProjectGeneralConfigService INSTANCE() {
		return _instance_;
	}
	
	public List<ProjectGeneralConfig> find_all_config(SysProjectInfo project) {
		String sql = "select * from ProjectGeneralConfig where flag!=? and proj_id=?";
		return this.find(sql, -1, project.id);
	}
	
	public List<ProjectGeneralConfig> find_project_pts(SysProjectInfo project, def tyc) {
		String sql = "select * from ProjectGeneralConfig where flag!=? and proj_id=? and sc_type=?";
		return this.find(sql, -1, project.id, tyc);
	}
	
	public ProjectGeneralConfig get_config(def id) {
		return this.get('select * from ProjectGeneralConfig where flag!=? and id=?', -1, id);
	}
	
	public ProjectGeneralConfig get_(def pid = GlobalHolder.proj.id, def sc_type, def sc_code) {
		String sql = 'select * from ProjectGeneralConfig where flag!=? and sc_type=? and sc_code=? and proj_id=?';
		ProjectGeneralConfig config = this.get(sql, -1, sc_type, sc_code, pid);
		return config;
	}
	
	def save_update_config(ProjectGeneralConfig config) {
		if(config.id!=null) {
			this.update(config);
		} else {
			BigInteger id = this.save(config);
			return id;
		}
	}
}
