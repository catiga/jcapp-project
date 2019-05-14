package com.jeancoder.project.interceptor.incall

import com.jeancoder.annotation.urlmapped
import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.http.JCCookie
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.project.ready.dto.AccountSession
import com.jeancoder.project.ready.dto.AuthRole
import com.jeancoder.project.ready.dto.AuthUser
import com.jeancoder.project.ready.dto.SysFunction
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.helper.GlobalHolder
import com.jeancoder.project.ready.obj.AuthToken
import com.jeancoder.project.ready.service.AuthRoleService
import com.jeancoder.project.ready.service.AuthUserService
import com.jeancoder.project.ready.service.FuncServiceCarry
import com.jeancoder.project.ready.service.SessionService

@urlmapped('/incall')

JCLogger LOGGER = LoggerSource.getLogger("admin/pre");

JCRequest request = JC.request.get();
def uri = request.getRequestURI();

SysProjectInfo project = GlobalHolder.getProj();
if(project==null) {
	LOGGER.info("web interceptor seq error, domain_proj_interceptor should be executed at first......");
	return true;
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

AuthToken at1 = GlobalHolder.getAuthToken();

SessionService session_service = SessionService.INSTANCE();

if(_c_u_k_==null||_c_u_k_.trim().equals("")) {
	return true;
}
AccountSession session = session_service.flush_session(_c_u_k_, 1);
if(session==null) {
	return true;
}
BigInteger user_id = session.basic_id;
AuthUserService user_service = AuthUserService.INSTANCE();
AuthUser user = user_service.get_by_id(user_id);

AuthRoleService role_service = AuthRoleService.INSTANCE();
FuncServiceCarry func_service = FuncServiceCarry.INSTANCE();

List<AuthRole> user_roles = role_service.user_roles(user);
List<SysFunction> functions = func_service.find_roles_functions(user_roles);

AuthToken at = new AuthToken(user, session);
at.roles = user_roles;
at.functions = functions;
GlobalHolder.setAuthToken(at);
request.setAttribute("current_token", at);

return true;


