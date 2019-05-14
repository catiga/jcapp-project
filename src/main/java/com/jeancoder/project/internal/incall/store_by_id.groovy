package com.jeancoder.project.internal.incall

import com.jeancoder.app.sdk.JC
import com.jeancoder.project.ready.dto.StoreInfo
import com.jeancoder.project.ready.service.StoreService

StoreService store_service = StoreService.INSTANCE();

def id = JC.internal.param('id');
def pid = JC.internal.param('pid');

StoreInfo parent = store_service.get_by_id(pid, id);

return parent;