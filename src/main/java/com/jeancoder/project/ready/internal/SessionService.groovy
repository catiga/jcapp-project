package com.jeancoder.project.ready.internal

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.jdbc.template.CarryJcDaoTemplate
import com.jeancoder.project.ready.dto.AccountSession
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.security.DESPlus
import com.jeancoder.project.ready.service.AuthUserService

class SessionService extends CarryJcDaoTemplate<AccountSession> {

	JCLogger LOGGER = LoggerSource.getLogger(SessionService.class.getName());
	
	final static SessionService _INSTANCE_ = new SessionService();
	
	AuthUserService user_service = AuthUserService.INSTANCE();
	
	public static SessionService INSTANCE() {
		return _INSTANCE_;
	}
	
	AccountSession flush_session(SysProjectInfo project, String token, Integer lograns) throws Exception {
		if(token==null||token.trim().equals('')||project==null) {
			return null;
		}
		String sql = "select * from AccountSession where flag!=? and proj_id=? and token=? and lograns=?";
		List<AccountSession> user_sessions = this.find(sql, -1, project.id, token, lograns);
		if(user_sessions==null||user_sessions.empty) {
			return null;
		}
		if(user_sessions.size()>1) {
			LOGGER.error('admin_session_token_repeat_error:token=' + token);
			return null;
		}
		try {
			DESPlus des = new DESPlus(project.proj_key);
			AccountSession session = user_sessions.get(0);
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
//			if((create_time - now_time)<=valid_time) {
				//说明在有效期内，刷新有效期为当前时间
				session.rushed = now_time + valid_time;
				session.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
				this.update(session);
				return session;
//			} else {
//				//clear token
//				session.flag = -1
//				session.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
//				this.update(session);
//				return null;
//			}
		} catch(Exception e) {
			LOGGER.error("", e);
			return null;
		}
	}
}
