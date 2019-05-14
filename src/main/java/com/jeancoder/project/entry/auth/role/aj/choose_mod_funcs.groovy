package com.jeancoder.project.entry.auth.role.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.project.ready.AjaxUtil
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.AuthRole
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.helper.FuncHolder
import com.jeancoder.project.ready.service.AuthRoleService
import com.jeancoder.project.ready.service.PmodMMService
import com.jeancoder.project.ready.service.ProjectService
import com.jeancoder.project.ready.service.ProjectServiceCarry

/*
AuthRoleService role_service = AuthRoleService.INSTANCE();
FuncServiceCarry func_service = FuncServiceCarry.INSTANCE();

SysFunction parent = func_service.get_by_id(JC.request.param("m_id"));
if(!parent) {
	return AjaxUtil.fail('mod_not_found', null);
}
AuthRole role = role_service.get_by_id(JC.request.param("r_id"));
if(!role) {
	return AjaxUtil.fail('role_not_found', null);
}

//获取模块下功能
List<SysFunction> all_functions = func_service.find_mod_funcs(parent);
if(all_functions!=null&&!all_functions.isEmpty()) {
	List<SysFunction> role_functions = func_service.find_role_functions(role);
	if(role_functions!=null&&!role_functions.isEmpty()) {
		for(SysFunction f : all_functions) {
			boolean is_in = false;
			for(SysFunction r_f : role_functions) {
				if(f.getId().equals(r_f.getId())) {
					is_in = true;
					break;
				}
			}
			if(is_in) {
				f.setFlag(1);
			}
		}
	}
}
final List<SysFunction> final_all_functions = all_functions;
return AjaxUtil.successs('', final_all_functions);
*/

def r_id = JC.request.param('r_id');
AuthRole role = AuthRoleService.INSTANCE().get_by_id(r_id);

if(role==null) {
	return SimpleAjax.notAvailable('role_not_found', null);
}
ProjectServiceCarry proj_service = ProjectServiceCarry.INSTANCE();
SysProjectInfo project = proj_service.get_proj(role.pid);

def mod_id = JC.request.param('mod_id');

//def root_mem_fs = FuncHolder.INSTANCE.my_funcs(mod_id);

def mod_functions = FuncHolder.INSTANCE.getall(mod_id);
PmodMMService mod_service = PmodMMService.INSTANCE();
def proj_mod_strs = mod_service.find_project_functions(project, mod_id);

def proj_mod = [];
for(x in mod_functions) {
	proj_mod_strs.each({if(it==x.unicode) proj_mod.add(x)});
}
def root_mem_fs = FuncHolder.INSTANCE.org_funcs(mod_id, proj_mod);

def all_proj_funcs = mod_service.find_role_func_code(role, mod_id);

println JackSonBeanMapper.toJson(all_proj_funcs);

def kmap = [];
root_mem_fs.each({
	k, v ->
	kmap.add([k:k, v:v])
});
return SimpleAjax.available('', [kmap, all_proj_funcs]);






