package com.jeancoder.project.entry.project.aj

import com.jeancoder.project.ready.AjaxUtil
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.form.ProjDomain
import com.jeancoder.project.ready.service.ProjectServiceCarry
import com.jeancoder.project.ready.support.exception.DomainExistException
import com.jeancoder.app.sdk.JC

ProjDomain form = JC.extract.fromRequest(ProjDomain.class);
if(!form||form.getDomain()==null||form.getDomain()=='') {
	return AjaxUtil.fail('param_empty', null);
}

ProjectServiceCarry psc = ProjectServiceCarry.INSTANCE();
SysProjectInfo project = psc.get_proj(form.getId());
if(!project) {
	return AjaxUtil.fail('object_not_found', null);
}

try {
	psc.update_domain(project, form.getDomain());
	return AjaxUtil.success();
} catch(DomainExistException e) {
	return AjaxUtil.fail('object_repeat', null);
} catch(Exception e) {
	return AjaxUtil.fail('op_fail', null);
}