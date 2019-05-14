package com.jeancoder.project.entry.store.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.project.ready.AjaxUtil
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.StoreInfo
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.service.ProjectServiceCarry
import com.jeancoder.project.ready.service.StoreService

def store_id = JC.request.param('store_id');
def p_id = JC.request.param('p_id');

StoreService store_service = StoreService.INSTANCE();
ProjectServiceCarry proj_service = ProjectServiceCarry.INSTANCE();

StoreInfo store = store_service.get_by_id_from_root(store_id);
SysProjectInfo project = proj_service.get_proj(p_id);

if(store==null||project==null) {
	return SimpleAjax.notAvailable('obj_not_found,门店未找到');
}
if(store.proj_id!=null) {
	SysProjectInfo binded_proj = proj_service.get_proj(store.proj_id);
	if(binded_proj!=null) {
		return SimpleAjax.notAvailable('op_repeat,门店不可重复关联');
	}
}

store.proj_id = project.id;

store_service.update(store);

return SimpleAjax.available();