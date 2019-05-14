package com.jeancoder.project.entry.incall

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.ResultSource
import com.jeancoder.core.http.JCCookie
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.result.Result
import com.jeancoder.project.ready.dto.AccountSession
import com.jeancoder.project.ready.dto.AuthUser
import com.jeancoder.project.ready.service.AuthUserService
import com.jeancoder.project.ready.service.SessionService

JCRequest request = JC.request.get();

String _c_u_k_ = null;
JCCookie[] cookies = request.getCookies()
if(cookies!=null&&cookies.length>0) {
	for(JCCookie c : cookies) {
		if(c.getName().equals("_c_u_k_adm_")) {
			try {
				_c_u_k_ = URLDecoder.decode(c.getValue(), "utf-8");
			}catch(Exception e){
			}
			break;
		}
	}
}
SessionService session_service = SessionService.INSTANCE();

if(_c_u_k_==null||_c_u_k_.trim().equals("")) {
	return null;
}
AccountSession session = session_service.flush_session(_c_u_k_, 1);
if(session==null) {
	return null;
}
BigInteger user_id = session.basic_id;
AuthUserService user_service = AuthUserService.INSTANCE();
AuthUser user = user_service.get_by_id(user_id);

return user;