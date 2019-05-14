package com.jeancoder.project.entry.common.qiniu

import com.jeancoder.project.ready.AjaxUtil
import com.jeancoder.project.ready.dto.SysCity
import com.jeancoder.project.ready.service.SysCityService
import com.jeancoder.app.sdk.JC

SysCityService city_service = SysCityService.INSTANCE();

def id = JC.request.param('id');
SysCity parent = city_service.get_by_id(id);

List<SysCity> cities = city_service.find_city(parent);
return AjaxUtil.successs('', cities);