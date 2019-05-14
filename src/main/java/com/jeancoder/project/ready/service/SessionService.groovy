package com.jeancoder.project.ready.service

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.jdbc.template.CarryJcDaoTemplate
import com.jeancoder.project.ready.dto.AccountSession
import com.jeancoder.project.ready.dto.AuthUser
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.helper.GlobalHolder
import com.jeancoder.project.ready.security.DESPlus

class SessionService extends CarryJcDaoTemplate<AccountSession> {

	JCLogger LOGGER = LoggerSource.getLogger(SessionService.class.getName());
	
	final static SessionService _INSTANCE_ = new SessionService();
	
	AuthUserService user_service = AuthUserService.INSTANCE();
	
	public static SessionService INSTANCE() {
		return _INSTANCE_;
	}
	
	def invalid_session(def pid = GlobalHolder.proj?.id, String token, Integer lograns) {
		if(token==null||token.trim().equals('')) {
			return;
		}
		String hql = "select * from AccountSession where flag!=? and proj_id=? and token=? and lograns=?";
		List<AccountSession> user_sessions = this.find(hql, -1, pid, token, lograns);
		if(user_sessions==null||user_sessions.isEmpty()) {
			return;
		}
		for(AccountSession session : user_sessions) {
			session.flag = -1;
			this.update(session);
		}
	}
	
	public AccountSession login_admin_session(def pid = GlobalHolder.proj?.id, String username, String passwd, Long up_id, Long validate_period, String logtype, def remote_addr) {
		AuthUser authed = user_service.auth(pid, username, passwd);
		if(authed==null) {
			return null;
		}
		if(!up_id.equals(0l)) {
			throw new RuntimeException("unsupport_non_browser_login");
		}
		AccountSession session = this.build_session(pid, new Date(), authed.id, authed.getPassword(), validate_period, 1, logtype, remote_addr);
		return session;
	}
	
	public AccountSession build_session(def pid, Date a_time, BigInteger user_id, String user_key, Long validate_period, Integer lograns, String logtype, def remote_addr) throws Exception {
		def sql = "select * from AccountSession where flag!=? and proj_id=? and basic_id=? and lograns=?";
		List<AccountSession> user_sessions = this.find(sql, -1, pid, user_id, lograns);
		String new_token = user_id + "|" + user_key + "|" + validate_period + "|" + (new Random().nextInt());
		SysProjectInfo vis_project = ProjectServiceCarry.INSTANCE().get_proj(pid);
		DESPlus des = new DESPlus(vis_project.proj_key);
		
		AccountSession session = new AccountSession();
		session.a_time = new Timestamp(a_time.getTime());
		session.rushed = (a_time.getTime() + validate_period);	//设置过期时间戳
		session.basic_id = user_id;
		session.c_time = new Timestamp(a_time.getTime());
		session.flag = 0;
		session.proj_id = vis_project.id;
		session.token = des.encrypt(new_token);
		session.lograns = lograns;
		if(logtype!=null) {
			session.logtype = logtype;
		}
		session.ip = remote_addr;
		
		if(user_sessions!=null&&!user_sessions.empty) {
			for(x in user_sessions) {
				x.flag = -1;
				this.update(x);
			}
		}
		this.save(session);
		
		return session;
	}
	
	protected AccountSession flush_session(def pid = GlobalHolder.proj?.id, String token, Integer lograns) throws Exception {
		//println 'jc_request_sess=' + JC.request.get()?.getRequestURL()?.toString();
		if(token==null||token.trim().equals('')) {
			return null;
		}
		
		SysProjectInfo vis_project = ProjectServiceCarry.INSTANCE().get_proj(pid);
		if(vis_project==null) {
			return null;
		}
		String sql = "select * from AccountSession where flag!=? and proj_id=? and token=? and lograns=?";
		List<AccountSession> user_sessions = this.find(sql, -1, pid, token, lograns);
		if(user_sessions==null||user_sessions.empty) {
			return null;
		}
		if(user_sessions.size()>1) {
			LOGGER.error('admin_session_token_repeat_error:token=' + token);
			return null;
		}
		try {
			DESPlus des = new DESPlus(vis_project.proj_key);
			AccountSession session = user_sessions.get(0);
			String old_token = session.getToken();
			//需要判断token是否过期
			String old_token_decode = des.decrypt(old_token);
			String[] arr_old_token_decode = old_token_decode.split("\\|");
			
			long create_time = session.getRushed();				//最终有效期的时间戳
			long now_time = Calendar.getInstance().getTimeInMillis();
			long valid_time = 0l;
			try {
				valid_time = Long.valueOf(arr_old_token_decode[2]);
			} catch(Exception e) {
			}
			if((create_time - now_time)<=valid_time) {
				//说明在有效期内，刷新有效期为当前时间
				session.rushed = now_time + valid_time;
				session.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
				this.update(session);
				return session;
			} else {
				//clear token
				session.flag = -1
				session.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
				this.update(session);
				return null;
			}
		} catch(Exception e) {
			LOGGER.error("", e);
			return null;
		}
	}
}
