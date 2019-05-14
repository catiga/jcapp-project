package com.jeancoder.project.entry.incall

import com.jeancoder.app.sdk.JC
import com.jeancoder.project.ready.dto.ProjectGeneralConfig
import com.jeancoder.project.ready.service.ProjectGeneralConfigService

ProjectGeneralConfigService service = ProjectGeneralConfigService.INSTANCE();

def sc_code = JC.request.param('sc_code');
def sc_type = JC.request.param('sc_type');

ProjectGeneralConfig config = service.get_(sc_type, sc_code);

return config;