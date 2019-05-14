package com.jeancoder.project.entry.org

import com.jeancoder.project.ready.dto.SysDept
import com.jeancoder.project.ready.dto.SysOrgination
import com.jeancoder.project.ready.helper.GlobalHolder
import com.jeancoder.project.ready.service.SysDeptService
import com.jeancoder.project.ready.service.SysOrginationService
import com.jeancoder.core.result.Result

SysOrginationService org_service = SysOrginationService.INSTANCE();
SysDeptService dept_service = SysDeptService.INSTANCE();

SysOrgination root_org = org_service.get_org();

List<SysDept> root_depts = dept_service.find_by_parent(root_org, null);

Result view = new Result();
view.setView("org/list");
view.addObject("proj", GlobalHolder.proj);
view.addObject("root_org", root_org);
view.addObject("root_depts", root_depts);

return view;