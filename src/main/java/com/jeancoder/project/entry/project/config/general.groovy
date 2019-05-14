package com.jeancoder.project.entry.project.config

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.ProjectGeneralConfig
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.obj.SysSupp
import com.jeancoder.project.ready.service.ProjectGeneralConfigService
import com.jeancoder.project.ready.service.ProjectServiceCarry
import com.jeancoder.project.ready.util.RemoteUtil

Result view = new Result();
view.setView("project/config/general");

ProjectServiceCarry project_service = ProjectServiceCarry.INSTANCE();
SysProjectInfo project = project_service.get_proj(JC.request.param("p_id"));

view.addObject("project", project);
List<ProjectGeneralConfig> result = null;
if(project!=null) {
	ProjectGeneralConfigService config_service = ProjectGeneralConfigService.INSTANCE();
	result = config_service.find_all_config(project);
}
view.addObject("supp_configs", result);

def supp_config_types = ['1000':'支付方式', '2000':'配送方式'];
view.addObject('supp_config_types', supp_config_types);

//获取系统交易允许的支付方式列表
def allow_pts = [];
try {
	SimpleAjax ret_allow_pts = JC.internal.call(SimpleAjax, 'trade', '/incall/pts', null);
	if(ret_allow_pts.available) {
		allow_pts = ret_allow_pts.data;
	}
} catch(any) {}

def real_allow_pts = [];
for(x in allow_pts) {
	def pgc = new ProjectGeneralConfig(sc_type:x.tyc, sc_code_cat:x.code_cat, sc_code:x.code, sc_name:x.name);
	for(y in result) {
		if(x.tyc==y.sc_type && x.code==y.sc_code) {
			pgc.id = y.id;
			pgc.acc_fld = y.acc_fld;
			pgc.c_time = y.c_time;
			pgc.flag = y.flag;
			pgc.paro = y.paro;
			pgc.partner = y.partner;
			pgc.pri_k_p = y.pri_k_p;
			pgc.pri_key = y.pri_key;
			pgc.pri_key_format = y.pri_key_format;
			pgc.pri_key_type = y.pri_key_type;
			pgc.proj_id = y.proj_id;
			pgc.pub_k_p = y.pub_k_p;
			pgc.pub_key = y.pub_key;
			pgc.pub_key_format = y.pub_key_format;
			pgc.pub_key_type = y.pub_key_type;
			pgc.rb_key = y.rb_key;
			pgc.rb_key_format = y.rb_key_format;
			pgc.rb_key_type = y.rb_key_type;
			pgc.rb_kp = y.rb_kp;
			pgc.rollback = y.rollback;
			pgc.sc_info = y.sc_info;
			pgc.sc_jt = y.sc_jt;
			pgc.disname = y.disname;
			pgc.rollback = x.rb;
			pgc.mchid = y.mchid;
			
			if(!pgc.self_check()) {
				pgc.flag = 1;
			}
		}
	}
	real_allow_pts.add(pgc);
}


view.addObject('allow_pts', real_allow_pts);

return view;


