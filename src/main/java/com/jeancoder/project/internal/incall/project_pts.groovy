package com.jeancoder.project.internal.incall

import com.jeancoder.app.sdk.JC
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.ProjectGeneralConfig
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.helper.GlobalHolder
import com.jeancoder.project.ready.service.ProjectGeneralConfigService
import com.jeancoder.project.ready.service.ProjectServiceCarry

def tyc = JC.internal.param('tyc');
def pid = JC.internal.param('pid');

ProjectGeneralConfigService service = ProjectGeneralConfigService.INSTANCE();

SysProjectInfo project = ProjectServiceCarry.INSTANCE().get_proj(pid);
List<ProjectGeneralConfig> result = service.find_project_pts(project, tyc);

return SimpleAjax.available('', result);