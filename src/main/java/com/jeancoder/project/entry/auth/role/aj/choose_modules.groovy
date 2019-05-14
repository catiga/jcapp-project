package com.jeancoder.project.entry.auth.role.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.dto.NamerApplicationDto
import com.jeancoder.app.sdk.source.jeancoder.ApplicationSource
import com.jeancoder.project.ready.AjaxUtil
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.AuthRole
import com.jeancoder.project.ready.dto.SysFunction
import com.jeancoder.project.ready.service.AuthRoleService
import com.jeancoder.project.ready.service.FuncServiceCarry

AuthRoleService role_service = AuthRoleService.INSTANCE();

def r_id = JC.request.param('r_id');
AuthRole role = role_service.get_by_id(r_id);

println r_id + role;
if(role==null) {
	return SimpleAjax.notAvailable('role_not_found');
}

List<NamerApplicationDto> all_apps = ApplicationSource.getApplicationAll();

def appcode = JC.request.param('appcode');
if(appcode==null||appcode.trim()=='')
	appcode = 'project';
NamerApplicationDto app_info = null;
all_apps.each{
	it-> if(it.appCode==appcode) app_info = it;
}

return SimpleAjax.available('', [appcode, all_apps]);





