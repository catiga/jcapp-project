package com.jeancoder.project.internal.auth

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.jdbc.JcPage
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.service.AuthRoleService
import com.jeancoder.project.ready.service.ProjectServiceCarry

JCLogger LOGGER = LoggerSource.getLogger("login");

def pid = JC.internal.param('pid');

ProjectServiceCarry project_service = ProjectServiceCarry.INSTANCE();

SysProjectInfo curr_project = project_service.get_proj(pid);
if(curr_project==null) {
	return SimpleAjax.notAvailable('param_error,pid参数错误');
}
def page = new JcPage<>();
page.pn = 1;
page.ps = 100;

page = AuthRoleService.INSTANCE().find_role(pid, page, null);
return SimpleAjax.available('', page.result);
