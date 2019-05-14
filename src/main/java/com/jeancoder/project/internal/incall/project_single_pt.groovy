package com.jeancoder.project.internal.incall

import com.jeancoder.app.sdk.JC
import com.jeancoder.project.ready.dto.ProjectGeneralConfig
import com.jeancoder.project.ready.service.ProjectGeneralConfigService

ProjectGeneralConfigService service = ProjectGeneralConfigService.INSTANCE();

def sc_code = JC.internal.param('sc_code');
def sc_type = JC.internal.param('sc_type');
def pid = JC.internal.param('pid');

ProjectGeneralConfig config = service.get_(pid, sc_type, sc_code);

return config;