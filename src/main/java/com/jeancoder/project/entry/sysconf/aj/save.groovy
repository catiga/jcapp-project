package com.jeancoder.project.entry.sysconf.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.entity.ConfigEn

def id = JC.request.param('id');
def title = JC.request.param('title');
def info = JC.request.param('info');
def code = JC.request.param('code');
def use = JC.request.param('use');
def detail = JC.request.param('detail');

ConfigEn config = null;
if(id!=null&&id!='') {
	config = JcTemplate.INSTANCE().get(ConfigEn, 'select * from ConfigEn where flag!=? and id=?', -1, id);
	if(config==null) {
		return SimpleAjax.notAvailable('obj_not_found,配置未找到');
	}
}
def update = true;
if(config==null) {
	update = false;
	config = new ConfigEn();
	config.a_time = new Date();
	config.flag = 0;
	config.code = code;
}
config.info = info;
config.title = title;
if(use=='1') {
	config.ss = '10';
} else {
	config.ss = '00';
}
config.config = detail;

if(update) {
	JcTemplate.INSTANCE().update(config);
} else {
	JcTemplate.INSTANCE().save(config);
}

return SimpleAjax.available();
