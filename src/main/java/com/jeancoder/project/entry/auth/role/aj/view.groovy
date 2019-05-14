package com.jeancoder.project.entry.auth.role.aj

import com.jeancoder.project.ready.AjaxUtil
import com.jeancoder.project.ready.dto.AuthRole
import com.jeancoder.project.ready.service.AuthRoleService
import com.jeancoder.app.sdk.JC

def role_id = JC.request.param('id');
if(role_id==null) {
	return AjaxUtil.fail('param_error', null);
}
AuthRoleService __service = AuthRoleService.INSTANCE();
AuthRole role = __service.get_by_id(Long.valueOf(role_id));
if(role==null) {
	return AjaxUtil.fail('role_not_found', null);
}

return AjaxUtil.successs([] as String[], role);