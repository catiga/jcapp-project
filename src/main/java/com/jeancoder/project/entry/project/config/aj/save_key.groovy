package com.jeancoder.project.entry.project.config.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.ProjectGeneralConfig
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.obj.SysSupp
import com.jeancoder.project.ready.service.ProjectGeneralConfigService
import com.jeancoder.project.ready.service.ProjectServiceCarry

//var param = {partner:partner,disname:disname,sc_info:dis_tips,sc_code:sc_code,sc_type:sc_type};

JCLogger logger = JCLoggerFactory.getLogger("save_key");
def mch_id = JC.request.param('mch_id');
def pri_key = JC.request.param('pri_key');
def pub_key = JC.request.param('pub_key');
def sc_code = JC.request.param('sc_code');
def sc_type = JC.request.param('sc_type');
def sel_p = JC.request.param('sel_p');

SysProjectInfo sel_project = ProjectServiceCarry.INSTANCE().get_proj(sel_p);
if(!sel_project) {
	return SimpleAjax.notAvailable('param_error,请选择要配置的项目');
}

ProjectGeneralConfigService service = ProjectGeneralConfigService.INSTANCE();

ProjectGeneralConfig config = service.get_(sel_project.id, sc_type, sc_code);

SimpleAjax allow_pts = JC.internal.call(SimpleAjax.class, 'trade', '/incall/pts', null);
logger.info("internal call /incall/pts result: {}", JackSonBeanMapper.toJson(allow_pts))

if (!allow_pts.available || !allow_pts.data) {
	return SimpleAjax.notAvailable('unable to find pts from trade');
}
def ss = allow_pts.data.find{it->it.tyc==sc_type&&it.code==sc_code}

if(ss==null) {
	return SimpleAjax.notAvailable('not_allow');
}

if(config==null) {
	config = new ProjectGeneralConfig();
	config.flag = 0;
	config.proj_id = sel_project.id;
	config.sc_code = ss.code;
	config.sc_type = ss.tyc;
}

config.sc_name = ss.name;
if(mch_id) config.mchid = mch_id;
if(pri_key) config.pri_key = pri_key;
if(pub_key) config.pub_key = pub_key;

service.save_update_config(config);

return SimpleAjax.available();



