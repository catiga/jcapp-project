package com.jeancoder.project.entry.project.config.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.ProjectGeneralConfig
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.service.ProjectGeneralConfigService
import com.jeancoder.project.ready.service.ProjectServiceCarry

//var param = {partner:partner,disname:disname,sc_info:dis_tips,sc_code:sc_code,sc_type:sc_type};

def partner = JC.request.param('partner');
def disname = JC.request.param('disname');
def sc_info = JC.request.param('sc_info');
def sc_code = JC.request.param('sc_code');
def sc_type = JC.request.param('sc_type');
def sel_p = JC.request.param('sel_p');

SysProjectInfo sel_project = ProjectServiceCarry.INSTANCE().get_proj(sel_p);
if(!sel_project) {
	return SimpleAjax.notAvailable('param_error,请选择要配置的项目');
}

ProjectGeneralConfigService service = ProjectGeneralConfigService.INSTANCE();

ProjectGeneralConfig config = service.get_(sel_project.id, sc_type, sc_code);

//def allow_pts = RemoteUtil.connectAsArray(SysSupp.class, 'trade', '/incall/pts', null);
SimpleAjax allow_pts_aj = JC.internal.call(SimpleAjax, 'trade', '/incall/pts', null);
if(!allow_pts_aj.available) {
	return SimpleAjax.notAvailable('trade_config_error');
}

def allow_pts = allow_pts_aj.data;

def ss = allow_pts.find{it->it.tyc==sc_type&&it.code==sc_code}

if(ss==null) {
	return SimpleAjax.notAvailable('not_allow');
}

if(config==null) {
	config = new ProjectGeneralConfig();
	config.flag = 0;
	config.proj_id = sel_project.id;
}
config.disname = disname;
config.partner = partner;
config.sc_info = sc_info;
config.sc_code = ss.code;
config.sc_type = ss.tyc;
config.sc_name = ss.name;
config.sc_code_cat = ss.code_cat;

service.save_update_config(config);

return SimpleAjax.available();



