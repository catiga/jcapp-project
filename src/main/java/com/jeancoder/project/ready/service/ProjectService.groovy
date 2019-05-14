package com.jeancoder.project.ready.service

import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.jdbc.template.CommonJcDaoTemplate
import com.jeancoder.project.ready.dto.ProjectGeneralConfig
import com.jeancoder.project.ready.dto.SysMerchantInfo
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.helper.GlobalHolder

class ProjectService {

	static final JcTemplate jc_template = JcTemplate.INSTANCE();
	public static JcPage<SysProjectInfo> find_proj(JcPage<SysProjectInfo> page) {
		//CommonJcDaoTemplate<SysProjectInfo> jc = new CommonJcDaoTemplate<SysProjectInfo>();
		def sql = 'select * from SysProjectInfo where flag!=?';
		def params = []; params.add(-1);
		if(GlobalHolder.proj.root!=1) {
			sql += ' and id=?';
			params.add(GlobalHolder.proj.id);
		}
		//page = jc.find(SysProjectInfo.class, page, "select * from SysProjectInfo");

		page = jc_template.find(SysProjectInfo, page, sql, params.toArray());
		return page;
	}
	public static List<ProjectGeneralConfig> get_all_pay_type(def pid){
		def sql = 'select * from ProjectGeneralConfig where flag!=-1 and proj_id =? '
		return jc_template.find(ProjectGeneralConfig, sql, pid);
	}
	public static JcPage<SysProjectInfo> find_proj_all(JcPage<SysProjectInfo> page) {
		def sql = 'select * from SysProjectInfo where flag!=?';
		page = jc_template.find(SysProjectInfo, page, sql, -1);
		return page;
	}


	public static JcPage<SysMerchantInfo> find_merch(JcPage<SysMerchantInfo> page) {
		CommonJcDaoTemplate<SysMerchantInfo> jc = new CommonJcDaoTemplate<SysMerchantInfo>();
		page = jc.find(SysMerchantInfo.class, page, "select * from SysMerchantInfo");
		return page;
	}

}
