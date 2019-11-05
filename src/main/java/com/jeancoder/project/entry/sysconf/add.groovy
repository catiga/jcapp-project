package com.jeancoder.project.entry.sysconf

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.project.ready.entity.ConfigEn
import com.jeancoder.project.ready.sysconf.ConfFace
import com.jeancoder.project.ready.sysconf.FilePathConf
import com.jeancoder.project.ready.sysconf.SmsConf

def code = JC.request.param('code');
def id = JC.request.param('id');

def config = null;
if(id) {
	config = JcTemplate.INSTANCE().get(ConfigEn, 'select * from ConfigEn where flag!=? and id=?', -1, id);
}
if(config!=null) {
	code = config.code;
}

Result view = new Result();
view.setView("sysconf/add");
view.addObject('config_object', config);

List<ConfFace> all_configs = [];
all_configs.addAll(ConfFace.all_configs);
if(config!=null) {
	Iterator<ConfFace> it = all_configs!=null?all_configs.iterator():null;
	if(it) {
		while(it.hasNext()) {
			ConfFace cc = it.next();
			if(cc.code!=code) {
				it.remove();
			}
		}
	}
	
}
view.addObject('supp_configs', all_configs);

view.addObject('code', code);

def select_config = null;
for(x in all_configs) {
	if(x.code==code) {
		select_config = x;
	}
}
if(config!=null && config.config && select_config!=null) {
	def map = JackSonBeanMapper.jsonToList(config.config);
	for(x in select_config.mods) {
		for(y in map) {
			if(y['key']==x.key) {
				x.value = y['value']; break;
			}
		}
	}
}

view.addObject('select_config', select_config);

return view;
