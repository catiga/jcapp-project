package com.jeancoder.project.entry.incall

import com.jeancoder.jdbc.JcPage
import com.jeancoder.project.ready.AjaxUtil
import com.jeancoder.project.ready.dto.StoreInfo
import com.jeancoder.project.ready.service.StoreService

StoreService store_service = StoreService.INSTANCE();

JcPage<StoreInfo> page = new JcPage<StoreInfo>();
page.pn = 1;
page.ps = 100;

page = store_service.find_stores(page);

return page.result;
