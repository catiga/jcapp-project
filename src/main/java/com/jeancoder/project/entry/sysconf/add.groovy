package com.jeancoder.project.entry.sysconf

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.project.ready.sysconf.ConfFace
import com.jeancoder.project.ready.sysconf.FilePathConf
import com.jeancoder.project.ready.sysconf.SmsConf

def code = JC.request.param('code');

Result view = new Result();
view.setView("sysconf/add");

List<ConfFace> all_configs = ConfFace.all_configs;
view.addObject('supp_configs', all_configs);

view.addObject('code', code);

def select_config = null;
for(x in all_configs) {
	if(x.code==code) {
		select_config = x;
	}
}

view.addObject('select_config', select_config);

return view;
