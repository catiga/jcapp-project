package com.jeancoder.project.internal.incall.data

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.SysCity

def pid = JC.internal.param('pid');
def p = JC.internal.param('p');

def sql = 'select * from SysCity where flag!=?';
def params = []; params.add(-1);

if(p!=null&&p!='') {
	sql += ' and pid=?'; params.add(p);
} else {
	sql += ' and pid is null';
}

sql += ' order by city_no asc';

def result = JcTemplate.INSTANCE().find(SysCity, sql, params.toArray());

return SimpleAjax.available('', result);