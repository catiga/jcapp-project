package com.jeancoder.project.entry.incall

import com.jeancoder.project.ready.AjaxUtil
import com.jeancoder.project.ready.dto.SysDept
import com.jeancoder.project.ready.dto.SysOrgination
import com.jeancoder.project.ready.service.SysDeptService
import com.jeancoder.project.ready.service.SysOrginationService

SysOrginationService org_service = SysOrginationService.INSTANCE();
SysDeptService dept_service = SysDeptService.INSTANCE();

SysOrgination root_org = org_service.get_org();

List<SysDept> root_depts = dept_service.find_by_parent(root_org, null);

root_org.root_depts = root_depts;

return root_org;