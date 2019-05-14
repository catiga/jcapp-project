package com.jeancoder.project.entry.index.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCCookie
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.log.JCLogger
import com.jeancoder.project.ready.AjaxUtil
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.AccountSession
import com.jeancoder.project.ready.form.AuthForm
import com.jeancoder.project.ready.service.SessionService
import com.jeancoder.project.ready.support.security.MD5Util

JCLogger LOGGER = LoggerSource.getLogger("login");

def validate_period = 7*24*60*60*1000l; //默认有效期 七天
//AuthForm auth_form = JC.extract.fromRequest(AuthForm.class);
def jc_name = JC.request.param('jc_name');
def jc_pwd = JC.request.param('jc_pwd');

try {
	LOGGER.info('jc_name=' + jc_name + ', jc_pwd=' + jc_pwd);
	LOGGER.info('pwd md5=' + MD5Util.getMD5(jc_pwd));
} catch(ANY) {
	return SimpleAjax.notAvailable('login_fail', null);
}

def remote_addr = JC.request.get().getRemoteHost();

SessionService session_service = SessionService.INSTANCE();
AccountSession session = session_service.login_admin_session(jc_name, MD5Util.getMD5(jc_pwd), 0l, validate_period, "0", remote_addr);
if(session==null) {
	return SimpleAjax.notAvailable('login_fail', null);
}
JCResponse response = ResponseSource.getResponse();
JCCookie cookie = new JCCookie('_c_u_k_adm_', session.token);
cookie.setPath('/');
response.addCookie(cookie);
return SimpleAjax.available();

