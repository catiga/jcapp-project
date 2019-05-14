package com.jeancoder.project.entry.incall

import com.jeancoder.app.sdk.JC
import com.jeancoder.project.ready.dto.StoreInfo
import com.jeancoder.project.ready.service.StoreService

StoreService store_service = StoreService.INSTANCE();

def id = JC.request.param('id');
StoreInfo parent = store_service.get_by_id(id);

return parent;