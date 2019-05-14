package com.jeancoder.project.internal.auth

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.AccountSession
import com.jeancoder.project.ready.dto.AuthUser
import com.jeancoder.project.ready.dto.DomainObject
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.internal.SessionService
import com.jeancoder.project.ready.service.AuthUserService
import com.jeancoder.project.ready.service.DomainService
import com.jeancoder.project.ready.service.ProjectServiceCarry
import com.jeancoder.project.ready.support.security.MD5Util

JCLogger LOGGER = LoggerSource.getLogger("login");

def token = JC.internal.param('token');
def jc_pwd = JC.internal.param('jc_pwd');
def domain = JC.internal.param('domain');

DomainService domain_service = DomainService.INSTANCE();
ProjectServiceCarry project_service = ProjectServiceCarry.INSTANCE();
DomainObject domain_object = domain_service.get_sys_domain(domain);

SysProjectInfo curr_project = null;
if(domain_object==null) {
	//这种时候说明没有同步域名对象表，则直接从项目表里面找对象
	curr_project = project_service.get_proj_by_domain(domain);
} else {
	if(domain_object.getDtype().equals(SysProjectInfo.class.getName())) {
		//表示是项目类域名
		curr_project = project_service.get_proj_by_domain(domain);
	} else {
		curr_project = project_service.get_proj_by_domain(domain);
	}
}


AccountSession session = SessionService.INSTANCE().flush_session(curr_project, token, 1);
if(session==null) {
	return SimpleAjax.notAvailable();
}
AuthUser user = AuthUserService.INSTANCE().get_by_id(curr_project.id, session.basic_id);
if(user==null) {
	return SimpleAjax.notAvailable();
}

if(user.password!=MD5Util.getMD5(jc_pwd)) {
	return SimpleAjax.notAvailable();
}
return SimpleAjax.available();
