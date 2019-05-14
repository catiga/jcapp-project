package com.jeancoder.project.entry.incall

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.http.JCCookie
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.service.SessionService

JCLogger LOGGER = LoggerSource.getLogger("login");

SessionService session_service = SessionService.INSTANCE();


def _c_u_k_ = null;
JCRequest req = JC.request.get();
JCCookie[] cookies = req.cookies;
if(cookies!=null&&cookies.length>0) {
	for(JCCookie c : cookies) {
		if(c.getName().equals('_c_u_k_adm_')) {
			try {
				_c_u_k_ = c.getValue();
				break;
			}catch(Exception e){
			}
		}
	}
}

session_service.invalid_session(_c_u_k_, 1);

return SimpleAjax.available('', _c_u_k_);