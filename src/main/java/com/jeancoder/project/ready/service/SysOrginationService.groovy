package com.jeancoder.project.ready.service

import java.sql.Timestamp

import com.jeancoder.project.ready.dto.SysOrgination
import com.jeancoder.project.ready.helper.GlobalHolder
import com.jeancoder.jdbc.template.CarryJcDaoTemplate

class SysOrginationService extends CarryJcDaoTemplate<SysOrgination> {
	
	public SysOrgination get_org(def pid = GlobalHolder.proj.id) {
		String sql = 'select * from SysOrgination where flag!=? and pid=?';
		SysOrgination result = this.get(sql, -1, pid);
		if(result==null) {
			synchronized(this) {
				result = this.get(sql, -1, pid);
				if(result==null) {
					init_org();
					result = this.get(sql, -1, pid);
				}
			}
		}
		return result;
	}
	
	private void init_org() {
		SysOrgination org = new SysOrgination();
		org.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
		org.c_time = org.a_time;
		org.code = GlobalHolder.proj.id + System.currentTimeMillis();
		org.name = GlobalHolder.proj.proj_name;
		org.flag = 0;
		org.pid = GlobalHolder.proj.id;
		this.save(org);
	}

	public static SysOrginationService INSTANCE() { 
		return new SysOrginationService();
	}
	
}
