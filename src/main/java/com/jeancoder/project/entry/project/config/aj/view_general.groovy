package com.jeancoder.project.entry.project.config.aj

import com.jeancoder.project.ready.AjaxUtil
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.ProjectFrontConfig
import com.jeancoder.project.ready.dto.ProjectGeneralConfig
import com.jeancoder.project.ready.service.ProjectFrontConfigService
import com.jeancoder.project.ready.service.ProjectGeneralConfigService
import com.jeancoder.app.sdk.JC

def _id = JC.request.param("_id");
if(_id==null) {
	return SimpleAjax.notAvailable('param_error');
}

ProjectGeneralConfigService service = ProjectGeneralConfigService.INSTANCE();
ProjectGeneralConfig config = service.get_config(_id);
if(config==null) {
	return SimpleAjax.notAvailable('config_not_found');
}

return SimpleAjax.available('', config);
