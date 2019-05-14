package com.jeancoder.project.entry.func.aj

import java.sql.Timestamp

import com.jeancoder.project.ready.AjaxUtil
import com.jeancoder.project.ready.dto.SysFunction
import com.jeancoder.project.ready.service.FuncServiceCarry
import com.jeancoder.app.sdk.JC

SysFunction func = JC.extract.fromRequest(SysFunction.class);

if(func==null) {
	return AjaxUtil.fail(['param_null'] as String[], null);
}

if(func.id==null) {
	return AjaxUtil.fail(['param_null'] as String[], null);
}

FuncServiceCarry func_service = FuncServiceCarry.INSTANCE();

SysFunction entity = func_service.get_by_id(func.id);
if(!entity) {
	return AjaxUtil.fail('func_not_found', null);
}


Timestamp times = new Timestamp(Calendar.getInstance().getTimeInMillis());

func.setC_time(times);
func_service.update_func(func);

return AjaxUtil.success();
