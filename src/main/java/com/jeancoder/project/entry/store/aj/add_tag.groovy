package com.jeancoder.project.entry.store.aj

import com.jeancoder.app.sdk.JC
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

List<StoreTag> tags = tag_ser.store_tags(store);
if(tags==null) {
	tags = [];
}

return SimpleAjax.available([] as String[], RetObj.build(tags));