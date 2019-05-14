package com.jeancoder.project.entry.auth.user.aj

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.AuthUser
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.form.UserModify
import com.jeancoder.project.ready.helper.GlobalHolder
import com.jeancoder.project.ready.service.AuthUserService
import com.jeancoder.project.ready.service.ProjectServiceCarry
import com.jeancoder.project.ready.support.security.MD5Util

UserModify um = JC.extract.fromRequest(UserModify.class);

if(um==null) {
	return SimpleAjax.notAvailable('param_error');
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

AuthUserService user_service = AuthUserService.INSTANCE();

boolean update = true;
AuthUser user = null;
if(um.id>0) {
	user = user_service.get_by_id(um.getId());
	if(user==null) {
		return SimpleAjax.notAvailable('user_not_found');
	}
} else {
	update = false;
	user = new AuthUser();
	user.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	user.flag = 0;
	user.pid = belong_proj;
}

user.setC_time(new Timestamp(Calendar.instance.getTimeInMillis()));
user.setUsername(um.getUsername());
user.setMobile(um.getMobile());
user.name = um.login_name;
if(um.password!=null&&um.password.trim()!='') {
	user.password = MD5Util.getMD5(um.password);
}

if(update)
	user_service.update_user(user);
else
	user_service.save(user);

return SimpleAjax.available();





