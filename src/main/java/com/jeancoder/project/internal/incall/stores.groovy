package com.jeancoder.project.internal.incall

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.project.ready.dto.StoreInfo

def city_no = JC.internal.param('city_no');
def pid = JC.internal.param('pid');

def sql = 'select * from StoreInfo where flag!=?';
def params = []; params.add(-1);

if(city_no) {
	sql += ' and city_no=?';
	params.add(city_no);
}
if(pid) {
	sql += ' and proj_id=?';
	params.add(pid);
}
sql += ' order by id asc';

def stores = JcTemplate.INSTANCE().find(StoreInfo, sql, params.toArray());

return stores;

