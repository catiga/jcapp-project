package com.jeancoder.project.internal.sys

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.entity.ConfigEn

def code = JC.internal.param('code');

//根据更新时间取最近更新的一个
ConfigEn config = JcTemplate.INSTANCE().get(ConfigEn, 'select * from ConfigEn where flag!=? and ss=? and code=? order by c_time desc', -1, '10', code);

def conf = null;
if(config!=null) {
	conf = config.config;
}
if(conf) {
	try {
		conf = JackSonBeanMapper.jsonToList(conf);
	} catch(any) {}
}

return SimpleAjax.available('', conf);