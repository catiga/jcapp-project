package com.jeancoder.project.internal.auth

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.AccountSession
import com.jeancoder.project.ready.dto.AuthUser
import com.jeancoder.project.ready.service.SessionService
import com.jeancoder.project.ready.support.security.MD5Util

def validate_period = 7*24*60*60*1000l; //默认有效期 七天

def jc_name = JC.internal.param('jc_name');
def jc_pass = JC.internal.param('jc_pass');
def pid = JC.internal.param('pid');

SessionService session_service = SessionService.INSTANCE();
AccountSession session = session_service.login_admin_session(pid, jc_name, MD5Util.getMD5(jc_pass), 0l, validate_period, "0", '127.0.0.1');
if(session==null) {
	return SimpleAjax.notAvailable('login_fail,登录验证失败');
}

def sql = 'select * from AuthUser where flag!=? and pid=? and mobile=?';
AuthUser now_user = JcTemplate.INSTANCE().get(AuthUser, sql, -1, pid, session.basic_id);

return SimpleAjax.available('', [session, now_user]);

