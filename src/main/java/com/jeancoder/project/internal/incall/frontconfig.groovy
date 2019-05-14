package com.jeancoder.project.internal.incall

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.project.ready.dto.ProjectFrontConfig
import com.jeancoder.project.ready.service.ProjectFrontConfigService

ProjectFrontConfigService service = ProjectFrontConfigService.INSTANCE();

def app_type = JC.internal.param('app_type');
def pid = JC.internal.param('pid');

def sql = 'select * from ProjectFrontConfig where flag!=? and project_id=? and app_type=? and user_gran=?';

return JcTemplate.INSTANCE().get(ProjectFrontConfig, sql, -1, pid, app_type, 1);