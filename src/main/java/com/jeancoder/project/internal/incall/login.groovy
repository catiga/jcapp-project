package com.jeancoder.project.internal.incall

import com.jeancoder.project.ready.AjaxUtil
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.AccountSession
import com.jeancoder.project.ready.dto.AuthRole
import com.jeancoder.project.ready.form.AuthForm
import com.jeancoder.project.ready.service.AuthRoleService
import com.jeancoder.project.ready.service.SessionService
import com.jeancoder.project.ready.support.security.MD5Util

import java.util.List

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCCookie
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.log.JCLogger

JCLogger LOGGER = LoggerSource.getLogger("login");

def validate_period = 7*24*60*60*1000l; //默认有效期 七天

def jc_name = JC.internal.param('jc_name');
def jc_pwd = MD5Util.getMD5(JC.internal.param('jc_pwd'));
def jc_pid = JC.internal.param('jc_pid');
def jc_ip = JC.internal.param('jc_ip');

SessionService session_service = SessionService.INSTANCE();
AccountSession session = session_service.login_admin_session(jc_pid, jc_name, jc_pwd, 0l, validate_period, "0", jc_ip);

if(session==null) {
	return SimpleAjax.notAvailable('pwd_error,用户名或密码错误');
}

//开始获取当前登录用户的角色
List<AuthRole> roles = AuthRoleService.INSTANCE().user_roles_by_uid(session.basic_id);

return SimpleAjax.available('', [session, roles]);
