package com.jeancoder.project.entry.incall

import com.jeancoder.app.sdk.JC
import com.jeancoder.project.ready.AjaxUtil
import com.jeancoder.project.ready.dto.AuthUser
import com.jeancoder.project.ready.service.AuthUserService


def id = JC.request.param('id');
if(!id) {
	return AjaxUtil.success();
}

AuthUserService user_service = AuthUserService.INSTANCE();

AuthUser user = user_service.get_by_id(id);

return user;