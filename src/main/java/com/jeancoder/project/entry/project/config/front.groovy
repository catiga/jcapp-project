package com.jeancoder.project.entry.project.config

import com.jeancoder.project.ready.dto.ProjectFrontConfig
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.service.ProjectFrontConfigService
import com.jeancoder.project.ready.service.ProjectServiceCarry
import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcPage

def pn = JC.request.param("pn");
if(pn==null) {
	pn = 1;
}
def ps = 20;

Result view = new Result();
view.setView("project/config/front");

ProjectServiceCarry project_service = ProjectServiceCarry.INSTANCE();
SysProjectInfo project = project_service.get_proj(JC.request.param("p_id"));

view.addObject("project", project);
if(project!=null) {
	JcPage<ProjectFrontConfig> page = new JcPage<ProjectFrontConfig>();
	page.setPn(Integer.valueOf(pn));
	page.setPs(ps);
	
	ProjectFrontConfigService conf_service = ProjectFrontConfigService.INSTANCE();
	page = conf_service.find_config(page, project);
	
	view.addObject("page", page);
}

return view;


