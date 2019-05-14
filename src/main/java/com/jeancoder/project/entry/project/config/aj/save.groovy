package com.jeancoder.project.entry.project.config.aj

import java.sql.Timestamp

import com.jeancoder.project.ready.AjaxUtil
import com.jeancoder.project.ready.dto.ProjectFrontConfig
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.form.FrontConfig
import com.jeancoder.project.ready.service.ProjectFrontConfigService
import com.jeancoder.project.ready.service.ProjectServiceCarry
import com.jeancoder.app.sdk.JC

ProjectServiceCarry project_service = ProjectServiceCarry.INSTANCE();
ProjectFrontConfigService config_service = ProjectFrontConfigService.INSTANCE();

FrontConfig form_obj = JC.extract.fromRequest(FrontConfig.class);

SysProjectInfo project = project_service.get_proj(JC.request.param("p_id"));
if(project==null) {
	return AjaxUtil.fail('p_not_found', null);
}

if(form_obj.id!=null&&form_obj.id>0) {
	ProjectFrontConfig config = config_service.get_by_id(form_obj.id);
	if(config==null) {
		return AjaxUtil.fail('config_not_found', null);
	}
	config.agent_id = form_obj.agent_id;
	config.app_encode_key = form_obj.app_encode_key;
	config.app_id = form_obj.app_id;
	config.app_key = form_obj.app_key;
	config.app_name = form_obj.app_name;
	config.app_token = form_obj.app_token;
	config.app_type = form_obj.app_type;
	config.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	
	config_service.update(config);
} else {
	ProjectFrontConfig config = new ProjectFrontConfig();
	config.agent_id = form_obj.agent_id;
	config.app_encode_key = form_obj.app_encode_key;
	config.app_id = form_obj.app_id;
	config.app_key = form_obj.app_key;
	config.app_name = form_obj.app_name;
	config.app_token = form_obj.app_token;
	config.app_type = form_obj.app_type;
	
	config.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	config.c_time = config.a_time;
	config.flag = 0;
	config.project_id = project.id;
	
	config_service.save(config);
}

return AjaxUtil.success();


