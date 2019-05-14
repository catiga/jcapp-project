package com.jeancoder.project.entry.project.aj

import java.sql.Timestamp

import org.apache.commons.fileupload.FileItem
import org.apache.commons.io.FileUtils;

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.QiniuSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.util.MD5Util
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.helper.GlobalHolder
import com.jeancoder.project.ready.service.ProjectServiceCarry


def cdn_root_path = '/data/cdn/jc';
def rel_path = 'project/project';

def id = JC.request.param('id')?.trim();
def name = JC.request.param('name')?.trim();
def info = JC.request.param('info')?.trim();
def brief = JC.request.param('brief')?.trim();
def key = JC.request.param('key')?.trim();
def phone = JC.request.param('phone')?.trim();
def status = JC.request.param('status')?.trim();

def logo_file = null;
JCRequest req = JC.request.get();
List<FileItem> items = req.getFormItems();
if(items) {
	FileItem file = items.get(0);
	String contentType = file.getContentType();
	if (contentType.indexOf("image") < 0 ) {
		return SimpleAjax.notAvailable("只支持上传*.jpg、*.gif、*.png类型的文件");
	}
	def define_name = MD5Util.getStringMD5(file.getName() + nextInt(1000, 9999));
	def filename = id + '_' + define_name;
	
	def file_path = cdn_root_path + '/' + rel_path;	// + '/' + filename;
	File file_obj = new File(file_path);
	if(!file_obj.exists()) {
		file_obj.mkdir();
	}
	file_path = file_path + '/' + filename;
	logo_file = rel_path + '/' + filename;
	FileUtils.writeByteArrayToFile(new File(file_path), file.get());
}

println 'logo_file======' + logo_file;

ProjectServiceCarry service = ProjectServiceCarry.INSTANCE();

def update = true;
SysProjectInfo project = null;
if(id&&id!='0') {
	project = service.get_proj(id);
	if(project==null) 
		return SimpleAjax.notAvailable('obj_not_found');
} else {
	update = false;
	project = new SysProjectInfo();
	project.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	project.flag = 0;
	project.root = 0;
	//设置直接归属项目为当前项目
	project.dbpid = GlobalHolder.proj.id;
	//设置根归属项目为当前项目
	if(GlobalHolder.proj.rbpid)
		project.rbpid = GlobalHolder.proj.rbpid;
	else
		project.rbpid = GlobalHolder.proj.id;
}
project.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
project.proj_name = name;
project.brief = brief;
project.info = info;
if(project.proj_key==null||project.proj_key=='') {
	if(!key) {
		key = MD5Util.getStringMD5(new Random().nextFloat() + '').substring(9, 21);
		project.proj_key = key;
	}
	project.proj_key = key;
}

project.custom_phone = phone;
project.proj_status = status;
if(logo_file) {
	project.logo = logo_file;
}

if(!update) {
	service.save(project);
} else {
	service.update(project);
}

return SimpleAjax.available();





def nextInt(final int min, final int max){
	Random rand= new Random();
	int tmp = Math.abs(rand.nextInt());
	return tmp % (max - min + 1) + min;
}









