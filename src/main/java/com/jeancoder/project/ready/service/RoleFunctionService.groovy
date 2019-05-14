package com.jeancoder.project.ready.service

import com.jeancoder.project.ready.dto.RoleFunction
import com.jeancoder.jdbc.template.CarryJcDaoTemplate

class RoleFunctionService extends CarryJcDaoTemplate<RoleFunction> {

	public static RoleFunctionService INSTANCE() {
		return new RoleFunctionService();
	}
}
