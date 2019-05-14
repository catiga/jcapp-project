package com.jeancoder.project.entry.auth.role.aj

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.project.ready.AjaxUtil
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.AuthRole
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.form.RoleModify
import com.jeancoder.project.ready.helper.GlobalHolder
import com.jeancoder.project.ready.service.AuthRoleService
import com.jeancoder.project.ready.service.ProjectServiceCarry

RoleModify um = JC.extract.fromRequest(RoleModify.class);

if(um==null) {
	return SimpleAjax.notAvailable('param_null');
}

def belong_proj = JC.request.param('belong_proj')?.trim();
if(GlobalHolder.proj.root!=1) {
	belong_proj = GlobalHolder.proj.id;
} else {
	if(belong_proj==null) {
		return SimpleAjax.notAvailable('need_proj');
	}
	SysProjectInfo select_proj = ProjectServiceCarry.INSTANCE().get_proj(belong_proj);
	if(select_proj==null) {
		return SimpleAjax.notAvailable('need_proj');
	}
	belong_proj = select_proj.id;
}

AuthRoleService __service = AuthRoleService.INSTANCE();

boolean update = true;
AuthRole role = null;
if(um.id>0) {
	role = __service.get_by_id(um.id);
	if(role==null) {
		return SimpleAjax.notAvailable('role_not_found');
	}
} else {
	update = false;
	role = new AuthRole();
	role.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	role.flag = 0;
	role.pid = belong_proj;
}

role.setC_time(new Timestamp(Calendar.instance.getTimeInMillis()));
role.setRole_name(um.role_name);
role.setRole_type(um.role_type);

if(update)
	__service.update(role);
else
	__service.save(role);

return SimpleAjax.available();


