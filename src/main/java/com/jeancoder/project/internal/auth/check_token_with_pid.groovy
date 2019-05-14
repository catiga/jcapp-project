package com.jeancoder.project.internal.auth

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.AccountSession
import com.jeancoder.project.ready.dto.AuthUser
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.internal.SessionService
import com.jeancoder.project.ready.mgr.AdminUser
import com.jeancoder.project.ready.security.DESPlus
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

AccountSession session = null;

if(curr_project.root) {
	//直接查找token
	String sql = "select * from AccountSession where flag!=? and token=? and lograns=?";
	List<AccountSession> user_sessions = JcTemplate.INSTANCE.find(AccountSession, sql, -1, token, 1);
	if(user_sessions==null||user_sessions.empty) {
		return null;
	}
	if(user_sessions.size()>1) {
		LOGGER.error('admin_session_token_repeat_error:token=' + token);
		return null;
	}
	session = user_sessions.get(0);
	SysProjectInfo token_project = ProjectServiceCarry.INSTANCE().get_proj(session.proj_id);
	try {
		//重置pid
		pid = token_project.id;
		
		DESPlus des = new DESPlus(token_project.proj_key);
		String old_token = session.getToken();
		//需要判断token是否过期
		String old_token_decode = des.decrypt(old_token);
		LOGGER.info(old_token_decode);
		String[] arr_old_token_decode = old_token_decode.split("\\|");
		
		long create_time = session.getRushed();				//最终有效期的时间戳
		long now_time = Calendar.getInstance().getTimeInMillis();
		long valid_time = 0l;
		try {
			valid_time = Long.valueOf(arr_old_token_decode[2]);
		} catch(Exception e) {
		}
		session.rushed = now_time + valid_time;
		session.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	} catch(Exception e) {
		LOGGER.error("", e);
		session = null;
	}
} else {
	session = SessionService.INSTANCE().flush_session(curr_project, token, 1);
}

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


