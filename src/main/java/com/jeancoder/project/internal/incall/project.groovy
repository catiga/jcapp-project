package com.jeancoder.project.internal.incall

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.project.ready.dto.DomainObject
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.service.DomainService
import com.jeancoder.project.ready.service.ProjectServiceCarry

/**
 * 接收参数类型
 * @param domain
 * 
 */
JCRequest request = RequestSource.getRequest();
DomainService domain_service = DomainService.INSTANCE();
ProjectServiceCarry project_service = ProjectServiceCarry.INSTANCE();
def domain = JC.internal.param('domain')?.toString().trim();

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
if(curr_project==null||!curr_project.getProj_status().equals("10")) {
	//项目已经停止的情况下，还是返回null
	curr_project = null;
}
return  curr_project;

