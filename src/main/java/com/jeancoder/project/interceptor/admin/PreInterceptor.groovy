package com.jeancoder.project.interceptor.admin

import com.jeancoder.annotation.urlpassed
import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.ResultSource
import com.jeancoder.core.http.JCCookie
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.AccountSession
import com.jeancoder.project.ready.dto.AuthUser
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.helper.GlobalHolder
import com.jeancoder.project.ready.obj.AuthToken
import com.jeancoder.project.ready.service.AuthUserService
import com.jeancoder.project.ready.service.SessionService

@urlpassed(['/index', '/common', '/incall'])

JCLogger LOGGER = LoggerSource.getLogger("admin/pre");

JCRequest request = JC.request.get();
def uri = request.getRequestURI();

boolean is_ajax_req = false;
if(request.getHeader("x-requested-with")!=null&&request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
	is_ajax_req = true;
}

SysProjectInfo project = GlobalHolder.getProj();
if(project==null) {
	LOGGER.info("web interceptor seq error, domain_proj_interceptor should be executed at first......");
	
	if(is_ajax_req) {
		ResultSource.setResult(new Result().setData(SimpleAjax.notAvailable('permission_error')));
		return SimpleAjax.notAvailable('permission_error');
	} else {
		ResultSource.setResult(new Result().setRedirectResource("/project/index/index"));
		return new Result().setRedirectResource("/project/index/index");
	}
	
//	return false;
}

String _c_u_k_ = null;
JCCookie[] cookies = request.getCookies()
if(cookies!=null&&cookies.length>0) {
	for(JCCookie c : cookies) {
		if(c.getName().equals("_c_u_k_adm_")) {
			try {
				_c_u_k_ = URLDecoder.decode(c.getValue(), "utf-8");
			}catch(Exception e){
				LOGGER.error("login token urldecode exception", e);
			}
			break;
		}
	}
}

//println '_c_u_k_=============' + _c_u_k_;
SessionService session_service = SessionService.INSTANCE();

if(_c_u_k_==null||_c_u_k_.trim().equals("")) {
	if(is_ajax_req) {
		ResultSource.setResult(new Result().setData(SimpleAjax.notAvailable('permission_error')));
		return new Result().setData(SimpleAjax.notAvailable('permission_error'));
	} else {
		ResultSource.setResult(new Result().setRedirectResource("/project/index/index?code=login"));
		return new Result().setRedirectResource("/project/index/index?code=login");
	}
//	return false;
}
AccountSession session = session_service.flush_session(_c_u_k_, 1);
if(session==null) {
	if(is_ajax_req) {
		ResultSource.setResult(new Result().setData(SimpleAjax.notAvailable('permission_error')));
		return new Result().setData(SimpleAjax.notAvailable('permission_error'));
	} else {
		ResultSource.setResult(new Result().setRedirectResource("/project/index/index?code=expired"));
		return new Result().setRedirectResource("/project/index/index?code=login");
	}
}
BigInteger user_id = session.basic_id;
AuthUserService user_service = AuthUserService.INSTANCE();
AuthUser user = user_service.get_by_id(user_id);
AuthToken at = new AuthToken(user, session);
GlobalHolder.setAuthToken(at);
request.setAttribute("current_token", at);
return true;


