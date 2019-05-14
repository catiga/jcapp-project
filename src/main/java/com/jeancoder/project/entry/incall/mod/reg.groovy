package com.jeancoder.project.entry.incall.mod

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.project.ready.appfunc.AppFunction
import com.jeancoder.project.ready.helper.FuncHolder

def app_code = JC.request.param('app_code');
def accept = JC.request.param('accept');

def result = null;
try {
	accept = URLDecoder.decode(accept, 'UTF-8');
	result = JackSonBeanMapper.jsonToList(accept, AppFunction);
} catch(any) {}

if(app_code!='project'&&result!=null&&!result.empty) {
	FuncHolder.INSTANCE.add(app_code, result);
}


