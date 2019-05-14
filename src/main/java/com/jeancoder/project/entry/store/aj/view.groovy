package com.jeancoder.project.entry.store.aj

import com.jeancoder.project.ready.AjaxUtil
import com.jeancoder.project.ready.dto.StoreInfo
import com.jeancoder.project.ready.service.StoreService
import com.jeancoder.app.sdk.JC

def id = JC.request.param('id');
StoreService store_service = StoreService.INSTANCE();

StoreInfo store = store_service.get_by_id_from_root(id);
if(!store) {
	return AjaxUtil.fail('store_not_found', null);
}

return AjaxUtil.successs('', store);