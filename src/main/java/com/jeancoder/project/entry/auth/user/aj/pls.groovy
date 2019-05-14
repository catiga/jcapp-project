package com.jeancoder.project.entry.auth.user.aj

import com.jeancoder.jdbc.JcPage
import com.jeancoder.project.ready.AjaxUtil
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.service.ProjectService

JcPage<SysProjectInfo> page = new JcPage<SysProjectInfo>();
page.setPn(Integer.valueOf(1));
page.setPs(50);

page = ProjectService.find_proj(page);


return AjaxUtil.successs('', page);
