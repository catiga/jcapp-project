package com.jeancoder.project.entry.auth.user.aj

import com.jeancoder.project.ready.AjaxUtil
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.AuthRole
import com.jeancoder.project.ready.dto.AuthUser
import com.jeancoder.project.ready.service.AuthRoleService
import com.jeancoder.project.ready.service.AuthUserService
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.jdbc.JcPage

JCRequest req = RequestSource.getRequest();
def u_id = req.getParameter("u_id");
if(u_id==null) {
	return SimpleAjax.notAvailable('param_error');
}

AuthUserService user_service = AuthUserService.INSTANCE();
AuthUser user = user_service.get_by_id(Long.valueOf(u_id));
if(user==null) {
	return SimpleAjax.notAvailable('user_not_found');
}

AuthRoleService role_service = AuthRoleService.INSTANCE();
List<AuthRole> user_roles = role_service.user_roles(user);

JcPage<AuthRole> page = new JcPage<AuthRole>();
page.pn = 1;
page.ps = 500;
page = role_service.find_role(page, user.pid);
List<AuthRole> all_roles = page.result;
if(all_roles!=null&&!all_roles.isEmpty()) {
	for(AuthRole r : all_roles) {
		boolean is_user_in = false;
		for(AuthRole u_r : user_roles) {
			if(r.getId().equals(u_r.getId())) {
				is_user_in = true;
				break;
			}
		}
		if(is_user_in) {
			//表示当前角色已经赋予该用户
			r.setFlag(1);
		}
	}
}
final List<AuthRole> ret_roles = all_roles;

if(ret_roles==null||ret_roles.empty) {
	return SimpleAjax.notAvailable('roles_empty');
}
return SimpleAjax.available('', ret_roles);



