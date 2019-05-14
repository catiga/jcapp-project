package com.jeancoder.project.internal.incall

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.project.ready.dto.DomainObject
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.service.DomainService
import com.jeancoder.project.ready.service.ProjectServiceCarry

/**
 * 接收参数类型
 * @param domain
 * 
 */
def pid = JC.internal.param('pid')?.toString().trim();

SysProjectInfo curr_project = ProjectServiceCarry.INSTANCE().get_proj(pid);

return  curr_project;

