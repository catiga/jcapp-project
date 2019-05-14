package com.jeancoder.project.entry.incall

import com.jeancoder.jdbc.JcPage
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.service.ProjectService

JcPage<SysProjectInfo> page = new JcPage<SysProjectInfo>();
page.setPn(Integer.valueOf(1));
page.setPs(50);
page = ProjectService.find_proj_all(page);
return page.getResult();