package com.jeancoder.project.interceptor.mod

import com.jeancoder.annotation.urlmapped
import com.jeancoder.annotation.urlpassed
import com.jeancoder.app.sdk.JC
import com.jeancoder.core.http.JCRequest
import com.jeancoder.project.ready.appfunc.AppFunction
import com.jeancoder.project.ready.dto.SysFunction
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.helper.FuncHolder
import com.jeancoder.project.ready.helper.GlobalHolder
import com.jeancoder.project.ready.util.FuncUtil

@urlmapped("/")
@urlpassed(['/incall', '/outcall'])

def mod_1 = FuncUtil.build_app_func(1, '系统配置', null, 'project', 'fa-stack-exchange');
def mod_1_1 = FuncUtil.build_app_func(101, '项目管理', 1, 'project/list', '', 2);
def mod_1_2 = FuncUtil.build_app_func(102, '功能管理', 1, 'appfunc/list', '', 2);

def mod_2 = FuncUtil.build_app_func(2, '权限系统', null, 'auth', 'fa-support');
def mod_2_1 = FuncUtil.build_app_func(201, '用户管理', 2, 'auth/user/list', '', 2);
def mod_2_2 = FuncUtil.build_app_func(202, '角色管理', 2, 'auth/role/list', '', 2);
def mod_2_3 = FuncUtil.build_app_func(203, '组织结构', 2, 'org/list', '', 2);

def mod_3 = FuncUtil.build_app_func(3, '门店管理', null, 'store/list', 'fa-bank', 1);

def mod_4 = FuncUtil.build_app_func(4, '操作日志', null, 'oplog/index', 'fa-deviantart', 1);


List<AppFunction> result = [];
result.addAll([mod_1, mod_1_1, mod_1_2]);
result.addAll([mod_2, mod_2_1, mod_2_2, mod_2_3]);
result.addAll([mod_3]);
result.add(mod_4);

FuncHolder.INSTANCE.add('project', result);

JCRequest request = JC.request.get();
def uri = request.getRequestURI();
def context = request.getContextPath();

def uri_without_code = uri[context.length()+1..-1];
if(uri_without_code.endsWith("/"))
	uri_without_code = uri_without_code[0..-2];
request.setAttribute("__now_uri_", uri_without_code);

//Map<SysFunction, List<SysFunction>> my_funcs = my_funcs(result);
def my_funcs = FuncHolder.INSTANCE.my_funcs('project');
//println "uri_without_code_____" + uri_without_code;
//println "user_roles_functions__" + JackSonBeanMapper.toJson(my_funcs);
request.setAttribute("user_roles_functions", my_funcs);


return true;






def get_by_id(def id, List<SysFunction> functions) {
	for(SysFunction f : functions) {
		if(f.id==id) {
			return f;
		}
	}
	return null;
}



def Map<SysFunction, List<SysFunction>> my_funcs(List<SysFunction> functions) {
	Map<SysFunction, List<SysFunction>> parent_functions = new LinkedHashMap<SysFunction, List<SysFunction>>();
	SysProjectInfo project = GlobalHolder.getProj();
	if(functions!=null&&!functions.isEmpty()) {
		for(SysFunction f : functions) {
			SysFunction parent_f = null;
			List<SysFunction> result_f = new ArrayList<SysFunction>();
			
			//只取两级的判断
			if(f.getLevel().equals(1)) {
				//表示当前这个为一级模块
				parent_f = f;
				for(SysFunction f_2 : functions) {
					if('0000'.equals(f_2.getFunc_type())){
						continue;
					}
					if(f_2.getParent_id()!=null&&f_2.getParent_id().equals(parent_f.getId())) {
						if(f_2.getLimpro().equals("0")&&!project.root) {
							continue;
						}
						result_f.add(f_2);
					}
				}
			} else if(f.getLevel().equals(2)) {
				//表示当前这个为二级模块
				parent_f = get_by_id(f.getParent_id(), functions);
				if(parent_f==null) {
					continue;
				}
				for(SysFunction f_2 : functions) {
					if('0000'.equals(f_2.getFunc_type())){
						continue;
					}
					if(f_2.getParent_id()!=null&&f_2.getParent_id().equals(parent_f.getId())) {
						if(f_2.getLimpro().equals("0")&&!project.root) {
							continue;
						}
						result_f.add(f_2);
					}
				}
			}
			parent_functions.put(parent_f, result_f);
		}
	}
	return parent_functions;
}