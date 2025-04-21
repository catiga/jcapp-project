package com.jeancoder.project.interceptor.project

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.ResultSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCThreadLocal
import com.jeancoder.core.result.Result
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.DomainObject
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.helper.GlobalHolder
import com.jeancoder.project.ready.service.DomainService
import com.jeancoder.project.ready.service.ProjectServiceCarry

def start = System.currentTimeMillis();
GlobalHolder.remove();
DomainService domain_service = DomainService.INSTANCE();
ProjectServiceCarry project_service = ProjectServiceCarry.INSTANCE();
JCRequest req = JC.request.get();
String domain = req.getServerName();
int port = req.getServerPort();
//req.setAttribute('pub_bucket', 'https://cdn.iplaysky.com/static/');
// req.setAttribute('pub_bucket', 'https://static.pdr365.com/static/');
//req.setAttribute('pub_bucket', 'http://static.jcloudapp.chinaren.xyz/static/')
req.setAttribute('pub_bucket', 'https://static.hash.bid/static/')

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

boolean is_ajax_req = false;
if(req.getHeader("x-requested-with")!=null&&req.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
	is_ajax_req = true;
}

if(curr_project==null||!curr_project.getProj_status().equals("10")) {
	if(!is_ajax_req) {
		ResultSource.setResult(new Result().setView("error/proj_empty"));
		return new Result().setView("error/proj_empty");
	} else {
		ResultSource.setResult(new Result().setData(SimpleAjax.notAvailable('project_domain_error')));
		return new Result().setData(SimpleAjax.notAvailable('project_domain_error'));
	}
//	return false;
}
GlobalHolder.setProj(curr_project);
req.setAttribute("current_project", curr_project);

return true;
