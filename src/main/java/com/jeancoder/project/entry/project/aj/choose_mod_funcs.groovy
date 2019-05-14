package com.jeancoder.project.entry.project.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.project.ready.AjaxUtil
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.helper.FuncHolder
import com.jeancoder.project.ready.service.PmodMMService
import com.jeancoder.project.ready.service.ProjectServiceCarry

/*
List<SysFunction> all_functions = FuncService.get_son_funcs_by_parent(JC.request.param('mod_id'));
if(all_functions==null||all_functions.empty) {
	return AjaxUtil.fail('result_empty', null);
}
FuncServiceCarry func_service = FuncServiceCarry.INSTANCE();

ProjectServiceCarry project_service = ProjectServiceCarry.INSTANCE();
SysProjectInfo select_project = project_service.get_proj(JC.request.param("p_id"));
if(!select_project) {
	return AjaxUtil.fail('project_not_found', null);
}

//获取当前模块所有功能
List<SysFunction> role_functions = func_service.find_project_functions(select_project);
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
final List<SysFunction> final_all_functions = all_functions;

return AjaxUtil.successs('', final_all_functions);
*/

ProjectServiceCarry project_service = ProjectServiceCarry.INSTANCE();
SysProjectInfo select_project = project_service.get_proj(JC.request.param("p_id"));
if(!select_project) {
	return AjaxUtil.fail('project_not_found', null);
}

def mod_id = JC.request.param('mod_id');

def root_mem_fs = FuncHolder.INSTANCE.my_funcs(mod_id);

PmodMMService mod_service = PmodMMService.INSTANCE();
def all_proj_funcs = mod_service.find_project_functions(select_project, mod_id);
println JackSonBeanMapper.listToJson(all_proj_funcs);
def kmap = [];
root_mem_fs.each({
	k, v -> 
	kmap.add([k:k, v:v])
});
return SimpleAjax.available('', [kmap, all_proj_funcs]);

