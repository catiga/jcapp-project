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
def id = JC.request.param('id');
StoreTag tag = tag_ser.get(id);
if(tag!=null) {
	tag.flag = -1;
	tag_ser.update_tag(tag);
}

return AjaxUtil.success();