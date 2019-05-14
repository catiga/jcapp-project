package com.jeancoder.project.entry.project.aj

import com.jeancoder.project.ready.AjaxUtil
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.service.ProjectServiceCarry
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest

JCRequest req = RequestSource.getRequest();
def p_id = req.getParameter("p_id");

if(!p_id) {
	return AjaxUtil.fail('param_empty', null);
}
SysProjectInfo project = ProjectServiceCarry.INSTANCE().get_proj(p_id);
if(project==null) {
	return AjaxUtil.fail('object_not_found', null);
}

return AjaxUtil.successs('', project.getDomain());