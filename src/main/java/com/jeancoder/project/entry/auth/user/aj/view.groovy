package com.jeancoder.project.entry.auth.user.aj

import com.jeancoder.project.ready.AjaxUtil
import com.jeancoder.project.ready.dto.AuthUser
import com.jeancoder.project.ready.service.AuthUserService
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest

JCRequest req = RequestSource.getRequest();
def u_id = req.getParameter("u_id");
if(u_id==null) {
	return AjaxUtil.fail('param_error', null);
}

AuthUserService user_service = AuthUserService.INSTANCE();
AuthUser user = user_service.get_by_id(Long.valueOf(u_id));
if(user==null) {
	return AjaxUtil.fail('user_not_found', null);
}

return AjaxUtil.successs([] as String[], user);