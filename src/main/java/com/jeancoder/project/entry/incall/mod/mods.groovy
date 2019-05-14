package com.jeancoder.project.entry.incall.mod

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.project.ready.appfunc.AppFunction
import com.jeancoder.project.ready.dto.AuthRole
import com.jeancoder.project.ready.dto.AuthUser
import com.jeancoder.project.ready.helper.FuncHolder
import com.jeancoder.project.ready.helper.GlobalHolder
import com.jeancoder.project.ready.service.AuthRoleService
import com.jeancoder.project.ready.service.PmodMMService

def app_code = JC.request.param('app_code');
//首先执行注册
def accept = JC.request.param('accept');

def result = null;
try {
	accept = URLDecoder.decode(accept, 'UTF-8');
	result = JackSonBeanMapper.jsonToList(accept, AppFunction);
} catch(any) {}

if(app_code!='project'&&result!=null&&!result.empty) {
	FuncHolder.INSTANCE.add(app_code, result);
}




AuthUser current_user = GlobalHolder.authToken?.user;

AuthRoleService role_service = AuthRoleService.INSTANCE();
List<AuthRole> user_roles = role_service.user_roles(current_user);

List<String> role_mods = PmodMMService.INSTANCE().find_roles_functions(app_code, user_roles);

List<AppFunction> functions = FuncHolder.INSTANCE.get_appcode_funcs_by_ids(app_code, role_mods);

return functions;



