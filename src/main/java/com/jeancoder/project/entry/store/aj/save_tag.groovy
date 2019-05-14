package com.jeancoder.project.entry.store.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.project.ready.AjaxUtil
import com.jeancoder.project.ready.ajax.RetObj
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.StoreInfo
import com.jeancoder.project.ready.dto.StoreTag
import com.jeancoder.project.ready.service.StoreService
import com.jeancoder.project.ready.service.TagService

TagService tag_ser = TagService.INSTANCE();
StoreService store_ser = StoreService.INSTANCE();

def store_id = JC.request.param('store_id');
StoreInfo store = store_ser.get_by_id_from_root(store_id);

if(store==null) {
//	return AjaxUtil.fail('obj_not_found', null);
	return SimpleAjax.notAvailable('obj_not_found,门店未找到');
}

def tags = JC.request.param('tags');
def arr_tags = tags.split(" ");
def data = [];
for(s in arr_tags) {
	if(!s.trim().equals("")) {
		data.add(s);
	}
}
if(!data.empty) {
	tag_ser.update_tags(store, data);
}

return SimpleAjax.available();