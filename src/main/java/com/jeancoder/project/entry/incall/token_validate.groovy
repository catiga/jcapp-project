package com.jeancoder.project.entry.incall

import com.jeancoder.app.sdk.JC
import com.jeancoder.project.ready.dto.AccountSession
import com.jeancoder.project.ready.dto.AuthUser
import com.jeancoder.project.ready.obj.AuthToken
import com.jeancoder.project.ready.service.AuthUserService
import com.jeancoder.project.ready.service.SessionService

def token = JC.request.param('token');

SessionService session_service = SessionService.INSTANCE();

AccountSession session = session_service.flush_session(token, 1);
if(session==null) {
	return null;
}
BigInteger user_id = session.basic_id;
AuthUserService user_service = AuthUserService.INSTANCE();
AuthUser user = user_service.get_by_id(user_id);
AuthToken at = new AuthToken(user, session);
return at;