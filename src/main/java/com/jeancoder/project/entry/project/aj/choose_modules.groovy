package com.jeancoder.project.entry.project.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.dto.NamerApplicationDto
import com.jeancoder.app.sdk.source.jeancoder.ApplicationSource
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.helper.FuncHolder

//ProjectModService mod_service = ProjectModService.INSTANCE();
//
//List<SysFunction> result = FuncService.find_level_funcs(1);
//
//return AjaxUtil.successs('', result);

List<NamerApplicationDto> all_apps = ApplicationSource.getApplicationAll();

def appcode = JC.request.param('appcode');
if(appcode==null||appcode.trim()=='')
	appcode = 'project';
NamerApplicationDto app_info = null;
all_apps.each{
	it-> if(it.appCode==appcode) app_info = it;
}

//def root_mem_fs = FuncHolder.INSTANCE.my_funcs(appcode);

return SimpleAjax.available('', [appcode, all_apps]);
