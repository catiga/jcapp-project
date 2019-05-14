package com.jeancoder.project.entry.auth.role.aj

import com.jeancoder.project.ready.AjaxUtil
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.AuthRole
import com.jeancoder.project.ready.dto.SysFunction
import com.jeancoder.project.ready.helper.FuncHolder
import com.jeancoder.project.ready.service.AuthRoleService
import com.jeancoder.project.ready.service.FuncServiceCarry
import com.jeancoder.project.ready.service.PmodMMService
import com.jeancoder.app.sdk.JC

/*
def r_id = JC.request.param("r_id");
def f_id = JC.request.param("f_id");
def type = JC.request.param("type");
def mod = JC.request.param('mod');

AuthRoleService role_service = AuthRoleService.INSTANCE();
FuncServiceCarry func_service = FuncServiceCarry.INSTANCE();

AuthRole role = role_service.get_by_id(r_id);
SysFunction func = func_service.get_by_id(f_id);
if(!role) {
	return AjaxUtil.fail('role_not_found', null);
}
if(!func) {
	return AjaxUtil.fail('func_not_found', null);
}
if(type=='1') {
	role_service.add_role_function(role, func);
} else {
	role_service.remove_role_function(role, func);
}
return AjaxUtil.success();
*/

def r_id = JC.request.param("r_id");
def f_id = JC.request.param("f_id");
def type = JC.request.param("type");
def mod = JC.request.param('mod');

def root_mem_fs = FuncHolder.INSTANCE.getall(mod);

def app_func = null;
root_mem_fs.each({if(it.unicode==f_id) app_func = it;});

AuthRole role = AuthRoleService.INSTANCE().get_by_id(r_id);
if(role==null) {
	return SimpleAjax.notAvailable('role_not_found');
}

if(app_func==null) {
	return SimpleAjax.notAvailable('func_not_found');
}

PmodMMService mod_serv = PmodMMService.INSTANCE();
if(type=='1') {
	mod_serv.add_role_function(role, app_func);
} else {
	mod_serv.remove_role_function(role, app_func);
}
return SimpleAjax.available();









