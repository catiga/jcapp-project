package com.jeancoder.project.entry.desktop

import com.jeancoder.app.sdk.source.dto.NamerApplicationDto
import com.jeancoder.app.sdk.source.jeancoder.ApplicationSource
import com.jeancoder.core.result.Result
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.helper.GlobalHolder
import com.jeancoder.project.ready.obj.AuthToken

SysProjectInfo now_proj = GlobalHolder.getProj();

List<NamerApplicationDto> sys_apps = ApplicationSource.getApplicationAll()

Result result = new Result();
result.setView("desktop/index");
result.addObject("now_proj", now_proj);

AuthToken token = GlobalHolder.authToken;
result.addObject('token', token);

def i = 0;
for(x in sys_apps) {
	switch(i++) {
		case 0:
		x.describe = 'red';
		break;
		case 1:
		x.describe = 'green';
		break;
		case 2:
		x.describe = 'yellow';
		break;
		case 3:
		x.describe = 'terques';
		break;
		case 4:
		x.describe = 'navy-blue';
		break;
		case 5:
		x.describe = 'deep-red';
		break;
		case 6:
		x.describe = 'blue';
		break;
		case 7:
		x.describe = 'purple';
		break;
		
		defult:
		x.describe = 'red';
		break;
	}
	if(!x.describe) {
		x.describe = 'yellow'
	}
}
result.addObject('sys_apps', sys_apps);

return result;