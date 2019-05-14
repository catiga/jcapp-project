package com.jeancoder.project.ready.service

import com.jeancoder.project.ready.dto.SysDept
import com.jeancoder.project.ready.dto.SysOrgination
import com.jeancoder.project.ready.helper.GlobalHolder
import com.jeancoder.jdbc.template.CarryJcDaoTemplate

class SysDeptService extends CarryJcDaoTemplate<SysDept> {

	final static private SysDeptService _instance_ = new SysDeptService();
	
	public static SysDeptService INSTANCE() { 
		return _instance_;
	}
	
	public List<SysDept> find_by_parent(def pid = GlobalHolder.proj.id, SysOrgination org, SysDept parent) {
		String sql = "select * from SysDept where flag!=? and org=? and pid=?";
		def params = [];
		params.add(-1);params.add(org.id);params.add(pid);
		if(parent) {
			sql = sql + ' and parent=?';
			params.add(parent.id);
		} else {
			sql = sql + ' and parent is null';
		}
		return this.find(sql, params.toArray());
	}
	
	public SysDept get_by_id(def pid = GlobalHolder.proj.id, def id) {
		if(!id) {
			return null;
		}
		String sql = "select * from SysDept where flag!=? and id=? and pid=?";
		return this.get(sql, -1, id, pid);
	}
	
}
