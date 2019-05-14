package com.jeancoder.project.ready.service

import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.project.ready.dto.StoreInfo
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.helper.GlobalHolder

class StoreService {

	JcTemplate jc_template = JcTemplate.INSTANCE();
	
	private static final StoreService __instance__ = new StoreService();
	
	public static StoreService INSTANCE() {
		return __instance__;
	}
	
	/**
	 * 直接获取所有门店，包括下属项目的
	 * @param page
	 * @return
	 */
	public JcPage<StoreInfo> find_stores_from_root(JcPage<StoreInfo> page, def store_num, def province_code) {
		def sql = "select * from StoreInfo where flag!=?";
		def param = [];
		param.add(-1);
		if(GlobalHolder.proj.root!=1) {
			sql += ' and proj_id=?';
			param.add(GlobalHolder.proj.id);
		}
		if(store_num) {
			sql += ' and store_no=?'; param.add(store_num);
		}
		if(province_code) {
			sql += ' and province_no=?'; param.add(province_code);
		}
		sql += ' order by c_time desc';
		return jc_template.find(StoreInfo.class, page, sql, param.toArray());
	}
	
	public StoreInfo get_by_id_from_root(def id) {
		if(!id) {
			return null;
		}
		String sql = "select * from StoreInfo where flag!=? and id=?";
		return jc_template.get(StoreInfo.class, sql, -1, id);
	}
	
	public JcPage<StoreInfo> find_stores(JcPage<StoreInfo> page, def pid = GlobalHolder.proj.id) {
		def sql = "select * from StoreInfo where flag!=?";
		def param = [];
		param.add(-1);
		
		SysProjectInfo project = ProjectServiceCarry.INSTANCE().get_proj(pid);
		if(project.root!=1) {
			sql += ' and proj_id=?';
			param.add(project.id);
		}
		sql += ' order by c_time desc';
		return jc_template.find(StoreInfo.class, page, sql, param.toArray());
	}
	
	public StoreInfo get_by_id(def pid = GlobalHolder.proj.id, def id) {
		if(!id) {
			return null;
		}
		String sql = "select * from StoreInfo where flag!=? and id=?";
		def params = []; params.add(-1); params.add(id);
		if(pid!=null) {
			sql += ' and proj_id=?';
			params.add(pid);
		}
		return jc_template.get(StoreInfo.class, sql, params.toArray());
	}
	
	public save(StoreInfo e) {
		jc_template.save(e);
	}
	
	public update(StoreInfo e) {
		jc_template.update(e);
	}
}
