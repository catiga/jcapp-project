package com.jeancoder.project.entry.project.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.helper.FuncHolder
import com.jeancoder.project.ready.service.PmodMMService
import com.jeancoder.project.ready.service.ProjectServiceCarry

/*
def proj_id = JC.request.param("proj_id");
def f_id = JC.request.param("f_id");
def type = JC.request.param("type");

ProjectServiceCarry project_service = ProjectServiceCarry.INSTANCE();
FuncServiceCarry func_service = FuncServiceCarry.INSTANCE();

SysProjectInfo project = project_service.get_proj(proj_id);
SysFunction func = func_service.get_by_id(f_id);
if(!project) {
	return AjaxUtil.fail('proj_not_found', null);
}
if(!func) {
	return AjaxUtil.fail('func_not_found', null);
}

ProjectModService mod_serv = ProjectModService.INSTANCE();
if(type=='1') {
	mod_serv.add_project_function(project, func);
} else {
	mod_serv.remove_project_function(project, func);
}
return AjaxUtil.success();
*/


def p_id = JC.request.param("p_id");
def f_id = JC.request.param("f_id");
def type = JC.request.param("type");
def mod = JC.request.param('mod');

def root_mem_fs = FuncHolder.INSTANCE.getall(mod);

def app_func = null;
root_mem_fs.each({if(it.unicode==f_id) app_func = it;});

println app_func;

ProjectServiceCarry project_service = ProjectServiceCarry.INSTANCE();

SysProjectInfo project = project_service.get_proj(p_id);
if(project==null) {
	return SimpleAjax.notAvailable('p_not_found');
}
if(app_func==null) {
	return SimpleAjax.notAvailable('func_not_found');
}

PmodMMService mod_serv = PmodMMService.INSTANCE();
if(type=='1') {
	mod_serv.add_project_function(project, app_func);
} else {
	mod_serv.remove_project_function(project, app_func);
}
return SimpleAjax.available();


