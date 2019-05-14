package com.jeancoder.project.entry.func.aj

import com.jeancoder.project.ready.AjaxUtil
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.SysFunction
import com.jeancoder.project.ready.service.FuncService
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest

JCRequest req = RequestSource.getRequest();
def fp_id = req.getParameter("f_id");
if(fp_id==null) {
	//return AjaxUtil.fail('param_error', null);
	return SimpleAjax.notAvailable('param_error,请检查参数');
}

SysFunction function = FuncService.get_by_id(Long.valueOf(fp_id));
if(function==null) {
	//return AjaxUtil.fail('mod_not_found', null);
	return SimpleAjax.notAvailable('mod_not_found,功能未找到');
}

//return AjaxUtil.successs([] as String[], function);
return SimpleAjax.available('', function);