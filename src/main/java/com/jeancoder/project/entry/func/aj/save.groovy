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

FuncServiceCarry func_service = FuncServiceCarry.INSTANCE();

if(func.parent_id) {
	SysFunction parent = func_service.get_by_id(func.parent_id);
	if(!parent) {
		return AjaxUtil.fail('parent_not_found', null);
	}
	func.level = parent.level + 1;
}

Timestamp times = new Timestamp(Calendar.getInstance().getTimeInMillis());

func.setA_time(times);
func.setC_time(times);
func_service.save_func(func);

return AjaxUtil.success();
