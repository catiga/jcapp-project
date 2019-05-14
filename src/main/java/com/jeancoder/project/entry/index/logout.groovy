package com.jeancoder.project.entry.index

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCCookie
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.result.Result
import com.jeancoder.project.ready.service.SessionService


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

return new Result().setRedirectResource('/project/index/index?'+new Date().getTime())