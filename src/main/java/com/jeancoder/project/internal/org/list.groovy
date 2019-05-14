package com.jeancoder.project.internal.org

import com.jeancoder.app.sdk.JC
import com.jeancoder.project.ready.AjaxUtil
import com.jeancoder.project.ready.dto.SysDept
import com.jeancoder.project.ready.dto.SysOrgination
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

SysOrgination root_org = org_service.get_org(pid);

List<SysDept> root_depts = dept_service.find_by_parent(pid, root_org, parent);


return root_depts;