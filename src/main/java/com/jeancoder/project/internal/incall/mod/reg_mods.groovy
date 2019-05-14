package com.jeancoder.project.internal.incall.mod

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.project.ready.appfunc.AppFunction
import com.jeancoder.project.ready.dto.AuthRole
import com.jeancoder.project.ready.dto.AuthUser
import com.jeancoder.project.ready.helper.FuncHolder
import com.jeancoder.project.ready.internal.AuthUserService
import com.jeancoder.project.ready.internal.AuthRoleService
import com.jeancoder.project.ready.service.PmodMMService

def start = System.currentTimeMillis();
def app_code = JC.internal.param('app_code');
//首先执行注册
def accept = JC.internal.param('accept');

def result = null;
try {
	accept = URLDecoder.decode(accept, 'UTF-8');
	result = JackSonBeanMapper.jsonToList(accept, AppFunction);
} catch(any) {}

if(app_code!='project'&&result!=null&&!result.empty) {
	FuncHolder.INSTANCE.add(app_code, result);
}
return true;

