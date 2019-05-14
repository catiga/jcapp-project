package com.jeancoder.project.internal.auth

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.AccountSession
import com.jeancoder.project.ready.dto.AuthUser
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.service.ProjectServiceCarry
import com.jeancoder.project.ready.service.SessionService
import com.jeancoder.project.ready.support.security.MD5Util

def validate_period = 7*24*60*60*1000l; //默认有效期 七天

def jc_name = JC.internal.param('jc_name');
def jc_pass = JC.internal.param('jc_pass');
def pid = JC.internal.param('pid');
def do_ip = JC.internal.param('ip');
if(do_ip==null) {
	do_ip = '0.0.0.0';
}

def new_pass = MD5Util.getMD5(jc_pass);

AuthUser geted_user = null;
//首先检查用户名
SysProjectInfo p = ProjectServiceCarry.INSTANCE().get_proj(pid);
if(p.root) {
	def sql = 'select * from AuthUser where ( name=? or mobile=? ) and password=? and flag!=?';
	List<AuthUser> result = JcTemplate.INSTANCE().find(AuthUser, sql, jc_name, jc_name, new_pass, -1);
	if(!result) {
		return SimpleAjax.notAvailable('login_fail,用户名或密码错误');
	} else if(result.size()>1) {
		return SimpleAjax.notAvailable('login_fail,用户名重复，请尝试联系管理员修改');
	}
	//重置pid
	pid = result.get(0).pid;
	geted_user = result.get(0);
}

SessionService session_service = SessionService.INSTANCE();
//AccountSession session = session_service.login_admin_session(pid, jc_name, new_pass, 0l, validate_period, "0", '127.0.0.1');
AccountSession session = session_service.build_session(pid, new Date(), geted_user.id, geted_user.password, validate_period, 1, "0", do_ip);
if(session==null) {
	return SimpleAjax.notAvailable('login_fail,创建会话令牌失败');
}


return SimpleAjax.available('', [session, geted_user]);

