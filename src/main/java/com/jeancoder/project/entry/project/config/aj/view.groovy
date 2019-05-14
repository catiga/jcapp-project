package com.jeancoder.project.entry.project.config.aj

import com.jeancoder.project.ready.AjaxUtil
import com.jeancoder.project.ready.dto.ProjectFrontConfig
import com.jeancoder.project.ready.service.ProjectFrontConfigService
import com.jeancoder.app.sdk.JC

def c_id = JC.request.param("c_id");
if(c_id==null) {
	return AjaxUtil.fail('param_error', null);
}

ProjectFrontConfigService config_service = ProjectFrontConfigService.INSTANCE();
ProjectFrontConfig config = config_service.get_by_id(c_id);
if(config==null) {
	return AjaxUtil.fail('config_not_found', null);
}

return AjaxUtil.successs('', config);
