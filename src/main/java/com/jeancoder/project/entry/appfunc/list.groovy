package com.jeancoder.project.entry.appfunc

import java.util.List

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.dto.NamerApplicationDto
import com.jeancoder.app.sdk.source.jeancoder.ApplicationSource
import com.jeancoder.core.result.Result
import com.jeancoder.project.ready.helper.FuncHolder

/**
 * 功能管理内存版
 * @author jackielee
 *
 */
List<NamerApplicationDto> all_apps = ApplicationSource.getApplicationAll();

def appcode = JC.request.param('appcode');
if(appcode==null||appcode.trim()=='')
	appcode = 'project';
NamerApplicationDto app_info = null;
all_apps.each{
	it-> if(it.appCode==appcode) app_info = it;
}
Result view = new Result();
view.setView("appfunc/list");
view.addObject('all_apps', all_apps);

def root_apps = FuncHolder.INSTANCE.apps();

def root_mem_fs = FuncHolder.INSTANCE.my_funcs(appcode);
view.addObject('root_mem_fs', root_mem_fs);
view.addObject('app_info', app_info);

def all_mem_fs = root_mem_fs;

view.addObject('all_mem_fs', all_mem_fs);
return view;




