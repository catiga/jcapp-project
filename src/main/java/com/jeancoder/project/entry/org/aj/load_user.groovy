package com.jeancoder.project.entry.org.aj

import com.jeancoder.project.ready.AjaxUtil
import com.jeancoder.project.ready.dto.AuthUser
import com.jeancoder.project.ready.dto.DeptUser
import com.jeancoder.project.ready.dto.SysDept
import com.jeancoder.project.ready.helper.GlobalHolder
import com.jeancoder.project.ready.service.AuthUserService
import com.jeancoder.project.ready.service.DeptUserService
import com.jeancoder.project.ready.service.SysDeptService
import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcPage

AuthUserService user_service = AuthUserService.INSTANCE();
JcPage<AuthUser> page = new JcPage<AuthUser>();
page.pn = 1;
page.ps = 100;
page = user_service.find_user(page, GlobalHolder.proj.id,null);

def dept_id = JC.request.param('dept');
SysDeptService dept_service = SysDeptService.INSTANCE();
SysDept dept = dept_service.get_by_id(dept_id);
if(dept!=null) {
	DeptUserService dept_user_service = DeptUserService.INSTANCE();
	List<DeptUser> dept_users = dept_user_service.find_by_dept(dept);
	if(dept_users!=null) {
		for(AuthUser u : page.result) {
			for(DeptUser du : dept_users) {
				if(u.id==du.user_id) {
					u.flag = 1;
					break;
				}
			}
		}
	}
}


return AjaxUtil.successs('', page.result);


