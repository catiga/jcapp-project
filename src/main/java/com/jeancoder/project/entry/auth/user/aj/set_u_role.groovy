package com.jeancoder.project.entry.auth.user.aj

import com.jeancoder.project.ready.AjaxUtil
import com.jeancoder.project.ready.dto.AuthRole
import com.jeancoder.project.ready.dto.AuthUser
import com.jeancoder.project.ready.service.AuthRoleService
import com.jeancoder.project.ready.service.AuthUserService
import com.jeancoder.project.ready.service.UserRoleService
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest

JCRequest req = RequestSource.getRequest();
def u_id = req.getParameter("u_id");
def r_id = req.getParameter("r_id");
def type = req.getParameter("type");
if(u_id==null) {
	return AjaxUtil.fail('param_error', null);
}

AuthUserService user_service = AuthUserService.INSTANCE();
AuthUser user = user_service.get_by_id(Long.valueOf(u_id));
if(user==null) {
	return AjaxUtil.fail('user_not_found', null);
}

AuthRoleService role_service = AuthRoleService.INSTANCE();
AuthRole role = role_service.get_by_id(r_id);
if(role==null) {
	return AjaxUtil.fail('role_not_found', null);
}

UserRoleService user_role_service = UserRoleService.INSTANCE();
if(type=='1') {
	user_role_service.grant_user_role(user, role);
} else {
	user_role_service.remove_user_role(user, role);
}

return AjaxUtil.success();


/*
if(u_id<=0l||r_id<=0l) {
	return AvailabilityStatus.notAvailable(JsConstants.input_param_error);
}
try {
	User user = authService.get_user_by_id(u_id);
	Role role = authService.getRoleById(r_id);
	if(user==null||role==null) {
		return AvailabilityStatus.notAvailable(JsConstants.common_object_not_found);
	}
	if(type) {
		//表示要对该用户授权
		authService.grant_user_role(user, role);
	} else {
		//表示要取消该用户授权
		authService.remove_user_role(user, role);
	}
	return AvailabilityStatus.available();
}catch(Exception e){
	log.error("", e);
	return AvailabilityStatus.notAvailable(JsConstants.unknown);
}
*/

