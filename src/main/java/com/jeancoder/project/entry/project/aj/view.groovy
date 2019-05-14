package com.jeancoder.project.entry.project.aj

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.service.ProjectServiceCarry


def id = JC.request.param('id');

ProjectServiceCarry service = ProjectServiceCarry.INSTANCE();

SysProjectInfo project = service.get_proj(id);

if(project==null) {
	return SimpleAjax.notAvailable('obj_not_found');
}

return SimpleAjax.available('',, project);