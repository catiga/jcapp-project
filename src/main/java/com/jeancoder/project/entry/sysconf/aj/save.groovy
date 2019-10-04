package com.jeancoder.project.entry.sysconf.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.entity.ConfigEn

def title = JC.request.param('title');
def info = JC.request.param('info');
def code = JC.request.param('code');
def use = JC.request.param('use');
def detail = JC.request.param('detail');

ConfigEn config = new ConfigEn();
config.a_time = new Date();
config.flag = 0;
config.code = code;
config.info = info;
config.title = title;
if(use=='1') {
	config.ss = '10';
} else {
	config.ss = '00';
}
config.config = detail;

JcTemplate.INSTANCE().save(config);

return SimpleAjax.available();
