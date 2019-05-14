package com.jeancoder.project.entry.incall

import com.jeancoder.project.ready.AjaxUtil
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.AccountSession
import com.jeancoder.project.ready.form.AuthForm
import com.jeancoder.project.ready.service.SessionService
import com.jeancoder.project.ready.support.security.MD5Util
import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCCookie
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.log.JCLogger

JCLogger LOGGER = LoggerSource.getLogger("login");

def remote_addr = JC.request.get().getRemoteHost();

def validate_period = 7*24*60*60*1000l; //默认有效期 七天
AuthForm auth_form = JC.extract.fromRequest(AuthForm.class);

SessionService session_service = SessionService.INSTANCE();
AccountSession session = session_service.login_admin_session(auth_form.jc_name, MD5Util.getMD5(auth_form.jc_pwd), 0l, validate_period, "0", remote_addr);

return session==null?'':session.token;
