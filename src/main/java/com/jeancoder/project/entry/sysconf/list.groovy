package com.jeancoder.project.entry.sysconf

import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.project.ready.entity.ConfigEn
import com.jeancoder.project.ready.sysconf.ConfFace
import com.jeancoder.project.ready.sysconf.FilePathConf
import com.jeancoder.project.ready.sysconf.SmsConf

Result view = new Result();
view.setView("sysconf/list");

//系统支持的配置
List<ConfFace> all_configs = ConfFace.all_configs;
view.addObject('supp_configs', all_configs);

def store_configs = JcTemplate.INSTANCE().find(ConfigEn, 'select * from ConfigEn where flag!=?', -1);
view.addObject('store_configs', store_configs);

return view;

