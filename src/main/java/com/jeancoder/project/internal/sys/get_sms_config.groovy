package com.jeancoder.project.internal.sys

import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.entity.ConfigEn

//获取短信网关配置
List<ConfigEn> configs = JcTemplate.INSTANCE().find(ConfigEn, 'select * from ConfigEn where flag!=? and ss=? and code=?', -1, '10', 'SMS');

def conf = null;
if(configs && !configs.empty) {
	conf = configs.get(0);
}

return SimpleAjax.available('', conf);
