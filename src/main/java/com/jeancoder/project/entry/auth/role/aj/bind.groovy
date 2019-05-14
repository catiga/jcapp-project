package com.jeancoder.project.entry.auth.role.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.project.ready.AjaxUtil
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.AuthRole
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.helper.GlobalHolder
import com.jeancoder.project.ready.service.AuthRoleService
import com.jeancoder.project.ready.service.ProjectServiceCarry
import com.jeancoder.project.ready.service.ProjectUserService

def role_id = JC.request.param('r_id')?.trim();
def p_id = JC.request.param('p_id')?.trim();

ProjectServiceCarry proj_service = ProjectServiceCarry.INSTANCE();
ProjectUserService pu_service = ProjectUserService.INSTANCE();

AuthRoleService __service = AuthRoleService.INSTANCE();
AuthRole role = __service.get_by_id(Long.valueOf(role_id));

SysProjectInfo project = proj_service.get_proj(p_id);

SysProjectInfo root_project = proj_service.get_root_proj();

if(GlobalHolder.proj.root!=1) {
	return SimpleAjax.notAvailable('op_not_allowed');
}
if(role.pid!=null&&role.pid!=root_project.id) {
	return SimpleAjax.notAvailable('op_repeat_error');
}

if(role==null||project==null) {
	//return AjaxUtil.fail('obj_not_found', null);
	return SimpleAjax.notAvailable('obj_not_found');
}

role.pid = project.id;
__service.update(role);

return SimpleAjax.available();
