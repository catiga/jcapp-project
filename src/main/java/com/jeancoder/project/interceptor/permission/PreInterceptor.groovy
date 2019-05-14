package com.jeancoder.project.interceptor.permission;

import com.jeancoder.annotation.urlmapped
import com.jeancoder.annotation.urlpassed
import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.ResultSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.appfunc.AppFunction
import com.jeancoder.project.ready.dto.AuthRole
import com.jeancoder.project.ready.dto.AuthUser
import com.jeancoder.project.ready.dto.SysFunction
import com.jeancoder.project.ready.entity.AuthRoleMod
import com.jeancoder.project.ready.helper.FuncHolder
import com.jeancoder.project.ready.helper.GlobalHolder
import com.jeancoder.project.ready.service.AdminLogService
import com.jeancoder.project.ready.service.AuthRoleService
import com.jeancoder.project.ready.service.FuncServiceCarry
import com.jeancoder.project.ready.service.PmodMMService

@urlmapped('/')
@urlpassed(['/index', '/common', '/incall', '/desktop', '/welcome'])

 

JCLogger LOGGER = LoggerSource.getLogger("permission/pre");

JCRequest request = JC.request.get();
def uri = request.getRequestURI();
def context = request.getContextPath();

def uri_without_code = uri[context.length()+1..-1];
if(uri_without_code.endsWith("/"))
	uri_without_code = uri_without_code[0..-2];
request.setAttribute("__now_uri_", uri_without_code);

boolean is_ajax_req = false;
if(request.getHeader("x-requested-with")!=null&&request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
	is_ajax_req = true;
}

if(GlobalHolder.getAuthToken() ==null) {
	if(is_ajax_req) {
		ResultSource.setResult(new Result().setData(SimpleAjax.notAvailable('permission_error')));
		return new Result().setData(SimpleAjax.notAvailable('permission_error'));
	} else {
		ResultSource.setResult(new Result().setRedirectResource("index/index"));
		return new Result().setRedirectResource("index/index");
	}
//	return false;
}

AuthUser current_user = GlobalHolder.authToken?.user;

AuthRoleService role_service = AuthRoleService.INSTANCE();

List<AuthRole> user_roles = role_service.user_roles(current_user);
List<String> role_mods = PmodMMService.INSTANCE().find_roles_functions('project', user_roles);
List<AppFunction> functions = FuncHolder.INSTANCE.get_appcode_funcs_by_ids('project', role_mods);

GlobalHolder.getAuthToken().setRoles(user_roles);
GlobalHolder.getAuthToken().setFunctions(functions);
request.setAttribute("token", GlobalHolder.getAuthToken());
//if(functions==null||functions.isEmpty()) {
//	//直接返回无权限
//	ResultSource.setResult(new Result().setView("error/no_permission"));
//	return false;
//}


boolean is_in_perm = false;
for(AppFunction f : functions) {
	String f_click_uri = context + '/' + f.getClick_url();
	if(!uri.endsWith("/")) {
		uri = uri + "/";
	}
	if(!f_click_uri.endsWith("/")) {
		f_click_uri = f_click_uri + "/";
	}
	//这里需要改为计算相关度
	//if(compute_path_relative(uri, f_click_uri)) {
	if(compute_path_relative(uri, f)) {
		is_in_perm = true;
		break;
	}
	
	/*
	if(f.whole) {
		//当前链接下整体授权，逻辑需要增加 uri + "/" 下属整体授权
		f_click_uri = f_click_uri.substring(0, f_click_uri.lastIndexOf("/"));
		f_click_uri = f_click_uri.substring(0, f_click_uri.lastIndexOf("/")) + "/";
		if(uri.startsWith(f_click_uri)) {
			is_in_perm = true;
			break;
		}
	} else {
		if(uri.equals(f_click_uri)) {
			is_in_perm = true;
			break;
		}
	}
	*/
}

Map<AppFunction, List<AppFunction>> my_funcs = FuncHolder.INSTANCE.org_funcs('project', functions);
request.setAttribute("user_roles_functions", my_funcs);

def execode = is_in_perm?'0':'perm_error'
AdminLogService.INSTANCE.record(request, current_user, request.getRemoteHost(), execode);

if(!is_in_perm) {
	if(is_ajax_req) {
		ResultSource.setResult(new Result().setData(SimpleAjax.notAvailable('permission_error')));
		return new Result().setData(SimpleAjax.notAvailable('permission_error'));
	} else {
		ResultSource.setResult(new Result().setView("error/no_permission"));
		return new Result().setView("error/no_permission");
	}
//	return false;
}


return true;


def compute_path_relative(def visit, AppFunction aim_function) {
	def result = false;
	def def_rel_v = 2;
	def aim = aim_function.click_url.trim();
	
	if(visit.toString().startsWith("/")) {
		visit = visit.toString().substring(1);
	}
	if(visit.toString().endsWith("/")) {
		visit = visit.toString().substring(0, visit.length() - 1);
	}
	if(aim.toString().startsWith("/")) {
		aim = aim.toString().substring(1);
	}
	if(aim.toString().endsWith("/")) {
		aim = aim.toString().substring(0, aim.length() - 1);
	}
	
	if(visit.lastIndexOf("/")>-1) {
		visit = visit.toString().substring(0, visit.lastIndexOf("/"));
	}
	if(aim.lastIndexOf("/")>-1) {
		aim = aim.toString().substring(0, aim.lastIndexOf("/"));
	}
	aim = aim_function.appcode + '/' + aim;
	
	//println visit + "===" + aim;
	if(visit.startsWith(aim)) {
		result = true;
	}
	
	return result;
}

/*
def compute_path_relative(def visit, def aim) {
	def result = true;
	def def_rel_v = 2;
	try {
		if(visit.toString().startsWith("/")) {
			visit = visit.toString().substring(1);
		}
		if(visit.toString().endsWith("/")) {
			visit = visit.toString().substring(0, visit.length() - 1);
		}
		if(aim.toString().startsWith("/")) {
			aim = aim.toString().substring(1);
		}
		if(aim.toString().endsWith("/")) {
			aim = aim.toString().substring(0, aim.length() - 1);
		}
		def arr_visit = visit.toString().split("\\/");
		def arr_aim = aim.toString().split("\\/");
		if(arr_visit.length<4) {
			if(visit!=aim) {
				result = false;
			}
		} else {
			def_rel_v = arr_visit.length>4?3:arr_visit.length - 1;
			for(int i=0; i<def_rel_v; i++) {
				if(arr_visit[i]!=arr_aim[i]) {
					result = false;
				}
			}
		}
	} catch(any) {
		result = false;
	}
	return result;
}
*/
