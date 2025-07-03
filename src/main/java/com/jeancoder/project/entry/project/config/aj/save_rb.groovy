package com.jeancoder.project.entry.project.config.aj

import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.util.JackSonBeanMapper
import org.apache.commons.fileupload.FileItem
import org.apache.commons.io.FileUtils

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.http.JCRequest
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.ProjectGeneralConfig
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.helper.GlobalHolder
import com.jeancoder.project.ready.obj.SysSupp
import com.jeancoder.project.ready.service.ProjectGeneralConfigService
import com.jeancoder.project.ready.service.ProjectServiceCarry
import com.jeancoder.project.ready.util.RemoteUtil

//var param = {partner:partner,disname:disname,sc_info:dis_tips,sc_code:sc_code,sc_type:sc_type};

JCLogger logger = JCLoggerFactory.getLogger('')
//这里应指向公共挂载硬盘
//def root_path = '/Users/jackielee/Desktop/tmp';

def root_path = '/data';	//共享磁盘地址
def sec_path = '/project/' + GlobalHolder.proj.id;

def full_path = root_path + sec_path;

def rb_kt = JC.request.param('rb_kt');
def rb_kp = JC.request.param('rb_kp');
def rb_ft = JC.request.param('rb_ft');
def rb_key = JC.request.param('rb_key');
def sc_code = JC.request.param('sc_code');
def sc_type = JC.request.param('sc_type');
def sel_p = JC.request.param('sel_p');

SysProjectInfo sel_project = ProjectServiceCarry.INSTANCE().get_proj(sel_p);
if(!sel_project) {
	return SimpleAjax.notAvailable('param_error,请选择要配置的项目');
}

def rb_file = null;
JCRequest req = JC.request.get();
List<FileItem> items = req.getFormItems();
if(items) {
	FileItem file = items.get(0);
	def file_path = full_path + '/' + (System.currentTimeMillis()+'').toString().substring(0,9) + '_' + file.getName();
	FileUtils.writeByteArrayToFile(new File(file_path), file.get());
	rb_file = file_path;
}

ProjectGeneralConfigService service = ProjectGeneralConfigService.INSTANCE();

ProjectGeneralConfig config = service.get_(sel_project.id, sc_type, sc_code);

SimpleAjax allow_pts = JC.internal.call(SimpleAjax.class, 'trade', '/incall/pts', null);
logger.info("allow_pts: {}", JackSonBeanMapper.toJson(allow_pts))
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
try {
	config.rb_key_type = Integer.valueOf(rb_kt);
}catch(any) {}
config.rb_key = rb_key;
config.rb_key_format = rb_ft;
config.rb_kp = rb_kp;
if(rb_file) {
	config.rb_file = rb_file;
}

service.save_update_config(config);

return SimpleAjax.available();



