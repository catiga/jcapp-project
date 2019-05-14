package com.jeancoder.project.internal.auth

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.jdbc.JcPage
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.AccountSession
import com.jeancoder.project.ready.dto.AuthUser
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.internal.SessionService
import com.jeancoder.project.ready.mgr.AdminUser
import com.jeancoder.project.ready.service.AuthRoleService
import com.jeancoder.project.ready.service.AuthUserService
import com.jeancoder.project.ready.service.ProjectServiceCarry

JCLogger LOGGER = LoggerSource.getLogger("login");

def pid = JC.internal.param('pid');
def token = JC.internal.param('token');

ProjectServiceCarry project_service = ProjectServiceCarry.INSTANCE();

SysProjectInfo curr_project = project_service.get_proj(pid);
if(curr_project==null) {
	return SimpleAjax.notAvailable('param_error,pid参数错误');
}

AccountSession session = SessionService.INSTANCE().flush_session(curr_project, token, 1);
if(!session) {
	return SimpleAjax.notAvailable('no_login,登录已过期');
}

AuthUser user = AuthUserService.INSTANCE().get_by_id(pid, session.basic_id);
if(user==null||user.flag!=0) {
	return SimpleAjax.notAvailable('status_error,用户状态异常');
}

AdminUser au = new AdminUser();
au.token = token;
au.user = user;
return SimpleAjax.available('', au);


