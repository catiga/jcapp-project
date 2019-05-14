package com.jeancoder.project.internal.incall

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.SysCity
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.service.ProjectServiceCarry

def pid = JC.internal.param('pid');

SysProjectInfo project = ProjectServiceCarry.INSTANCE().get_proj(pid);

def sql = 'select distinct b.* from StoreInfo a join SysCity b on a.city_no = b.city_no where a.flag!=?';
def params = [];
params.add(-1);
if(!project.root) {
	//不是根项目，则只取当前项目的门店
	sql = sql + ' and a.proj_id=?';
	params.add(pid);
}
sql += ' order by b.city_no asc';
List<SysCity> citys = JcTemplate.INSTANCE().find(SysCity, sql, params.toArray());

return SimpleAjax.available('', citys);

