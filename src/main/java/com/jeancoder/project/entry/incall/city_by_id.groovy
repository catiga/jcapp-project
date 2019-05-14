package com.jeancoder.project.entry.incall

import com.jeancoder.project.ready.AjaxUtil
import com.jeancoder.project.ready.dto.SysCity
import com.jeancoder.project.ready.service.SysCityService
import com.jeancoder.app.sdk.JC

SysCityService city_service = SysCityService.INSTANCE();

def id = JC.request.param('id');
SysCity parent = city_service.get_by_id(id);

return parent;