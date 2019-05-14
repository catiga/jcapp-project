package com.jeancoder.project.entry.auth.user.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.AuthUser
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.helper.GlobalHolder
import com.jeancoder.project.ready.service.AuthUserService
import com.jeancoder.project.ready.service.ProjectServiceCarry
import com.jeancoder.project.ready.service.ProjectUserService

def user_id = JC.request.param('user_id');
def p_id = JC.request.param('p_id');

ProjectServiceCarry proj_service = ProjectServiceCarry.INSTANCE();
ProjectUserService pu_service = ProjectUserService.INSTANCE();
AuthUserService user_service = AuthUserService.INSTANCE();

AuthUser user = user_service.get_by_id(user_id);

SysProjectInfo project = proj_service.get_proj(p_id);

SysProjectInfo root_project = proj_service.get_root_proj();

if(GlobalHolder.proj.root!=1) {
	return SimpleAjax.notAvailable('op_not_allowed');
}
if(user.pid!=null&&user.pid!=root_project.id) {
	return SimpleAjax.notAvailable('op_repeat_error');
}

if(user==null||project==null) {
	return SimpleAjax.notAvailable('obj_not_found');
}

//pu_service.bind_project_user(project, user);
user.pid = project.id;
user_service.update_user(user);

return SimpleAjax.available();
