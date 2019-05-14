package com.jeancoder.project.ready.service

import java.util.List

import com.jeancoder.project.ready.dto.SysCity
import com.jeancoder.jdbc.template.CarryJcDaoTemplate

class SysCityService extends CarryJcDaoTemplate<SysCity> {

	final static SysCityService _instance_ = new SysCityService();
	
	public static SysCityService INSTANCE() {
		return _instance_;
	}
	
	public SysCity get_by_id(def id) {
		if(!id) {
			return null;
		}
		def sql = 'select * from SysCity where flag!=? and id=?';
		return this.get(sql, -1, id);
	}
	
	public List<SysCity> find_city(SysCity parent) {
		def params = [];
		String sql = "select * from SysCity where flag!=?";
		params.add(-1);
		if(parent) {
			sql = sql + ' and pid=?';
			params.add(parent.id);
		} else {
			sql = sql + ' and m_level=?'
			params.add(0);
		}
		sql = sql + ' order by city_no asc';
		List<SysCity> _1_result = this.find(sql, params.toArray());
		return _1_result;
	}
}
