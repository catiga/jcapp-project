package com.jeancoder.project.entry.store.aj

import java.sql.Timestamp

import org.apache.commons.fileupload.FileItem

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.QiniuSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.util.MD5Util
import com.jeancoder.project.ready.AjaxUtil
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.StoreInfo
import com.jeancoder.project.ready.dto.SysCity
import com.jeancoder.project.ready.form.StoreForm
import com.jeancoder.project.ready.helper.GlobalHolder
import com.jeancoder.project.ready.service.StoreService
import com.jeancoder.project.ready.service.SysCityService

JCRequest req = JC.request.get();
List<FileItem> items = req.getFormItems();

StoreForm form = JC.extract.fromRequest(StoreForm.class);

if(!form) {
	return SimpleAjax.notAvailable('param_error,参数错误');
}

def logo_file = null;
if(items) {
	FileItem file = items.get(0);
	
	def define_name = MD5Util.getStringMD5(file.getName() + nextInt(1000, 9999));
	logo_file = 'sl_'+define_name;
	QiniuSource.getQiniuPower().upload(file.getInputStream(), logo_file);
}

StoreService store_service = StoreService.INSTANCE();

SysCityService city_service = SysCityService.INSTANCE();

SysCity province = city_service.get_by_id(form.province);
SysCity city = city_service.get_by_id(form.city);
SysCity zone = city_service.get_by_id(form.zone);

if(GlobalHolder.proj.root!=1) {
	return SimpleAjax.notAvailable('unsupport_op,编辑门店信息请联系管理员');
}

if(form.id!=null&&form.id>0) {
	StoreInfo store = store_service.get_by_id_from_root(form.id);
	if(store==null) {
		return SimpleAjax.notAvailable('store_not_found,门店未找到');
	}
	
	store.address = form.store_address;
	store.introduction = form.store_info;
	store.phone = form.store_phone;
	store.store_name = form.store_name;
	store.store_no = form.store_no;
	
	store.province = province!=null?province.city_name:null;
	store.province_no = province!=null?province.city_no:null;
	store.city = city!=null?city.city_name:null;
	store.city_no = city!=null?city.city_no:null;
	store.zone = zone!=null?zone.city_name:null;
	store.zone_no = zone!=null?zone.city_no:null;
	if(logo_file!=null) {
		store.store_logo = logo_file;
	}
	
	store.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	if(form.store_longitude) {
		store.longitude = form.store_longitude;
	}
	if(form.store_latitude) {
		store.latitude = form.store_latitude;
	}
	store_service.update(store);
} else {
	//新增门店
	StoreInfo store = new StoreInfo();
	//新增门店关联项目的逻辑有变化，不关联项目，后面可以指定
	//store.proj_id = GlobalHolder.proj.id;
	store.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	store.flag = 0;
	store.address = form.store_address;
	store.introduction = form.store_info;
	store.phone = form.store_phone;
	store.store_name = form.store_name;
	store.store_no = form.store_no;
	
	store.province = province!=null?province.city_name:null;
	store.province_no = province!=null?province.city_no:null;
	store.city = city!=null?city.city_name:null;
	store.city_no = city!=null?city.city_no:null;
	store.zone = zone!=null?zone.city_name:null;
	store.zone_no = zone!=null?zone.city_no:null;
	
	if(logo_file!=null) {
		store.store_logo = logo_file;
	}
	if(form.store_longitude) {
		store.longitude = form.store_longitude;
	}
	if(form.store_latitude) {
		store.latitude = form.store_latitude;
	}
	store_service.save(store);
}
return SimpleAjax.available();


def nextInt(final int min, final int max){
	Random rand= new Random();
	int tmp = Math.abs(rand.nextInt());
	return tmp % (max - min + 1) + min;
}



