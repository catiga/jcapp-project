package com.jeancoder.project.internal.org

import com.jeancoder.app.sdk.JC
import com.jeancoder.project.ready.dto.AuthUser
import com.jeancoder.project.ready.dto.SysDept
import com.jeancoder.project.ready.service.DeptUserService
import com.jeancoder.project.ready.service.SysDeptService
import com.jeancoder.project.ready.service.SysOrginationService

def parent_id = JC.internal.param('id');
def pid = JC.internal.param('pid');

SysOrginationService org_service = SysOrginationService.INSTANCE();
SysDeptService dept_service = SysDeptService.INSTANCE();

SysDept parent = null;
if(parent_id) {
	parent = dept_service.get_by_id(pid, parent_id);
}
if(parent==null) {
	return null;
}

DeptUserService dept_user_service = DeptUserService.INSTANCE();
List<AuthUser> dept_users = dept_user_service.find_dept_users(parent);

return dept_users;