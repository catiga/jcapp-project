package com.jeancoder.project.internal.incall

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcPage
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.service.ProjectService

def pid = JC.internal.param('pid');

JcPage<SysProjectInfo> page = new JcPage<SysProjectInfo>();
page.setPn(Integer.valueOf(1));
page.setPs(50);

page = ProjectService.find_proj_all(page);
return page.getResult();