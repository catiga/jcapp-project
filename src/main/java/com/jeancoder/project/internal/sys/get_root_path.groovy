package com.jeancoder.project.internal.sys

import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.entity.ConfigEn

List<ConfigEn> configs = JcTemplate.INSTANCE().find(ConfigEn, 'select * from ConfigEn where flag!=? and ss=? and code=?', -1, '10', 'FPATH');

def root_path = '/data/cdn/jc';
if(configs && !configs.empty) {
	ConfigEn c = configs.get(0);
	def c_json = c.config;
	
	if(c_json) {
		try {
			c_json = JackSonBeanMapper.jsonToList(c_json);
			root_path = c_json.get(0).get('value');
		} catch(any) {}
	}
}

return SimpleAjax.available('', root_path);
