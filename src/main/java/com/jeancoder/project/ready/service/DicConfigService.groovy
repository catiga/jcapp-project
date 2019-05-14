package com.jeancoder.project.ready.service

import java.util.List

import com.jeancoder.project.ready.dto.DicConfig
import com.jeancoder.jdbc.template.CarryJcDaoTemplate

class DicConfigService extends CarryJcDaoTemplate<DicConfig> {

	public static DicConfigService INSTANCE() {
		return new DicConfigService();
	}
	
	public List<DicConfig> find_support_configs(def cc_type) {
		String sql = "select * from DicConfig where flag!=? and cc_type=?";
		return this.find(sql, -1, cc_type);
	}
}
