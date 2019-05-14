package com.jeancoder.project.internal.incall

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.ProjectFrontConfig
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.service.ProjectFrontConfigService
import com.jeancoder.project.ready.service.ProjectServiceCarry

ProjectFrontConfigService service = ProjectFrontConfigService.INSTANCE();

def app_type = JC.internal.param('app_type');
def domain = JC.internal.param('domain');

SysProjectInfo project = ProjectServiceCarry.INSTANCE().get_proj_by_domain(domain);

def result = null;
if(!project) {
	return SimpleAjax.notAvailable('empty,项目域配置错误');
}

def sql = 'select * from ProjectFrontConfig where flag!=? and project_id=? and app_type=? and  user_gran=?';
result = JcTemplate.INSTANCE().get(ProjectFrontConfig, sql, -1, project.id, app_type,1);

return SimpleAjax.available('', result);