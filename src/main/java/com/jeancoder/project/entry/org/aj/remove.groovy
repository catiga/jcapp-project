package com.jeancoder.project.entry.org.aj

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.SysDept
import com.jeancoder.project.ready.service.DeptUserService
import com.jeancoder.project.ready.service.SysDeptService

DeptUserService dept_user_service = DeptUserService.INSTANCE();

def id = JC.request.param('id')?.trim();
SysDept dept = SysDeptService.INSTANCE().get_by_id(id);
if(dept==null) {
	return SimpleAjax.notAvailable('obj_not_found,部门未找到');
}
dept.flag = -1;dept.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
SysDeptService.INSTANCE().update(dept);

dept_user_service.remove_by_dept(dept);

return SimpleAjax.available();

