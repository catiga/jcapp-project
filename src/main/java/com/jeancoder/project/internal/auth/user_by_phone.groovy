package com.jeancoder.project.internal.auth

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.AccountSession
import com.jeancoder.project.ready.dto.AuthUser
import com.jeancoder.project.ready.mgr.AdminUser
import com.jeancoder.project.ready.service.SessionService

JCLogger LOGGER = LoggerSource.getLogger("login");

def pid = JC.internal.param('pid');
def phone = JC.internal.param('phone');
if(!pid||!phone) {
	return SimpleAjax.notAvailable();
}

def sql = 'select * from AuthUser where flag!=? and pid=? and mobile=?';
AuthUser now_user = JcTemplate.INSTANCE().get(AuthUser, sql, -1, pid, phone);

if(!now_user) {
	return SimpleAjax.notAvailable();
}

//直接执行登录操作，并返回token
//首先查找是否有对应的token
sql = 'select * from AccountSession where flag!=? and proj_id=? and basic_id=?';
AccountSession session = JcTemplate.INSTANCE().get(AccountSession, sql, -1, now_user.pid, now_user.id);
if(!session) {
	def validate_period = 7*24*60*60*1000l; //默认有效期 七天
	def remote_addr = '127.0.0.1';
	session = SessionService.INSTANCE().login_admin_session(pid, now_user.name, now_user.password, 0l, validate_period, "0", remote_addr);
}
if(!session) {
	return SimpleAjax.notAvailable();
}
AdminUser au = new AdminUser(token:session.token, user:now_user);

return SimpleAjax.available('', au);
