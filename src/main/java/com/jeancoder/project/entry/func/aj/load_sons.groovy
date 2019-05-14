package com.jeancoder.project.entry.func.aj

import com.jeancoder.project.ready.AjaxUtil
import com.jeancoder.project.ready.dto.SysFunction
import com.jeancoder.project.ready.service.FuncService
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest

JCRequest req = RequestSource.getRequest();
def fp_id = req.getParameter("fp_id");

SysFunction parent = FuncService.get_by_id(Long.valueOf(fp_id));
if(parent==null) {
	return AjaxUtil.fail('mod_not_found', null);
}

List<SysFunction> result = FuncService.get_son_funcs_by_parent(fp_id);

return AjaxUtil.successs([] as String[], result);