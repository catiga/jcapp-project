package com.jeancoder.project.entry.incall

import com.jeancoder.app.sdk.JC
import com.jeancoder.project.ready.dto.ProjectGeneralConfig
import com.jeancoder.project.ready.helper.GlobalHolder
import com.jeancoder.project.ready.service.ProjectGeneralConfigService

ProjectGeneralConfigService service = ProjectGeneralConfigService.INSTANCE();

def tyc = JC.request.param('tyc');
List<ProjectGeneralConfig> result = service.find_project_pts(GlobalHolder.proj, tyc);

return result;