package com.jeancoder.project.ready.service

import com.jeancoder.project.ready.dto.SysFunction
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.template.CommonJcDaoTemplate

class FuncService {

	public static JcPage<SysFunction> find_by_parent(JcPage<SysFunction> page, SysFunction parent) {
		CommonJcDaoTemplate<SysFunction> jc = new CommonJcDaoTemplate<SysFunction>();
		List<Object> params = new ArrayList<Object>();
		String sql = "select * from SysFunction where flag!=?";
		params.add(-1);
		if(parent) {
			sql = sql + " and parent_id=?"
			params.add(parent.id);
		} else {
			sql = sql + " and level=1";
		}
		page = jc.find(SysFunction.class, page, sql, params.toArray());
		return page;
	}
	
	public static List<SysFunction> find_level_funcs(def level) {
		String sql = "select * from SysFunction where flag!=? and level=? order by sort asc";
		CommonJcDaoTemplate<SysFunction> jc = new CommonJcDaoTemplate<SysFunction>();
		return jc.find(SysFunction.class, sql, -1, level);
	}
	
	public static SysFunction get_by_id(def id) {
		String sql = "select * from SysFunction where flag!=? and id=?";
		CommonJcDaoTemplate<SysFunction> jc = new CommonJcDaoTemplate<SysFunction>();
		return jc.get(SysFunction.class, sql, -1, id);
	}
	
	public static List<SysFunction> get_son_funcs_by_parent(def parent_id) {
		String sql = "select * from SysFunction where flag!=? and parent_id=? order by sort asc";
		CommonJcDaoTemplate<SysFunction> jc = new CommonJcDaoTemplate<SysFunction>();
		return jc.find(SysFunction.class, sql, -1, parent_id);
	}
}
