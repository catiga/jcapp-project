package com.jeancoder.project.entry.org.aj

import com.jeancoder.project.ready.AjaxUtil
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.AuthUser
import com.jeancoder.project.ready.dto.SysDept
import com.jeancoder.project.ready.service.AuthUserService
import com.jeancoder.project.ready.service.DeptUserService
import com.jeancoder.project.ready.service.SysDeptService
import com.jeancoder.app.sdk.JC

DeptUserService dept_user_service = DeptUserService.INSTANCE();

def deptid = JC.request.param('deptid');
def uid = JC.request.param('uid');

AuthUser user = AuthUserService.INSTANCE().get_by_id(uid);
SysDept dept = SysDeptService.INSTANCE().get_by_id(deptid);

if(user==null) {
	return SimpleAjax.notAvailable('obj_not_found,用户不存在');
}
if(dept==null) {
	return SimpleAjax.notAvailable('obj_not_found,部门不存在');
}

dept_user_service.bind_or_not(dept, user);
return SimpleAjax.available();

