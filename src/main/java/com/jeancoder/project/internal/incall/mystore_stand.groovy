package com.jeancoder.project.internal.incall

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcPage
import com.jeancoder.project.ready.AjaxUtil
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.StoreInfo
import com.jeancoder.project.ready.service.StoreService

def pid = JC.internal.param('pid');

StoreService store_service = StoreService.INSTANCE();

JcPage<StoreInfo> page = new JcPage<StoreInfo>();
page.pn = 1;
page.ps = 100;

page = store_service.find_stores(page, pid);

return SimpleAjax.available('', page.result);
