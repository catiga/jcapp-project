package com.jeancoder.project.entry.store

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.project.ready.dto.StoreInfo
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.helper.GlobalHolder
import com.jeancoder.project.ready.service.ProjectServiceCarry
import com.jeancoder.project.ready.service.StoreService
import com.jeancoder.project.ready.service.SysCityService

def select_province = JC.request.param('select_province');
def store_num = JC.request.param('store_num');
if(select_province=="") select_province = null;
if(store_num=="") store_num = null;

def pn = 1;
try {
	pn = Integer.valueOf(JC.request.param('pn'));
	if(pn<1) pn = 1;
} catch(any) {}
def ps = 20;

JcPage<StoreInfo> page = new JcPage<StoreInfo>();
page.setPn(Integer.valueOf(pn));
page.setPs(ps);

StoreService store_service = StoreService.INSTANCE();
page = store_service.find_stores_from_root(page, store_num, select_province);;

Result view = new Result();
view.setView("store/list");
view.addObject("page", page);
view.addObject("proj", GlobalHolder.proj);

SysCityService city_service = SysCityService.INSTANCE();
def provinces = city_service.find_city(null);
view.addObject('provinces', provinces);

view.addObject('store_num', store_num);
view.addObject('select_province', select_province);

def all_proj = JcTemplate.INSTANCE().find(SysProjectInfo, 'select * from SysProjectInfo where flag!=?', -1);
view.addObject('all_proj', all_proj);

return view;