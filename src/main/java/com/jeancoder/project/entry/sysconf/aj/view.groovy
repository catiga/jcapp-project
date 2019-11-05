package com.jeancoder.project.entry.sysconf.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.entity.ConfigEn

def id = JC.request.param('id');

ConfigEn config = JcTemplate.INSTANCE().get(ConfigEn, 'select * from ConfigEn where flag!=? and id=?', -1, id);

if(config==null) {
	return SimpleAjax.notAvailable('obj_not_found,配置未找到');
}

return SimpleAjax.available('', config);
