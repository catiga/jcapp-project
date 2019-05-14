package com.jeancoder.project.ready.service

import java.sql.Timestamp

import com.jeancoder.project.ready.dto.AuthUser
import com.jeancoder.project.ready.dto.DeptUser
import com.jeancoder.project.ready.dto.SysDept
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.jdbc.template.CarryJcDaoTemplate

class DeptUserService extends CarryJcDaoTemplate<DeptUser> {

	private static final DeptUserService _instance_ = new DeptUserService();
	
	public static DeptUserService INSTANCE() {
		return _instance_;
	}
	
	public List<DeptUser> find_by_dept(SysDept dept) {
		if(!dept) {
			return null;
		}
		String sql = 'select * from DeptUser where flag!=? and dept_id=?';
		return this.find(sql, -1, dept.id);
	}
	
	public List<AuthUser> find_dept_users(SysDept dept) {
		def sql = 'select * from AuthUser where flag!=? and id in (select user_id from DeptUser where flag!=? and dept_id=?)';
		return JcTemplate.INSTANCE().find(AuthUser, sql, -1, -1, dept.id);
	}
	
	public void bind_or_not(SysDept dept, AuthUser user) {
		if(dept==null||user==null) {
			return;
		}
		String sql = "select * from DeptUser where flag!=? and dept_id=? and user_id=?";
		List<DeptUser> dus = this.find(sql, -1, dept.id, user.id);
		if(dus==null||dus.empty) {
			DeptUser du = new DeptUser();
			du.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
			du.dept_id = dept.id;
			du.flag = 0;
			du.user_id = user.id;
			this.save(du);
		} else {
			for(DeptUser du in dus) {	
				du.flag = -1;
				this.update(du);
			}
		}
	}
	
	def remove_by_dept(SysDept dept) {
		def sql = "update DeptUser set flag=-1 where dept_id=? and flag!=?";
		JcTemplate.INSTANCE().batchExecute(sql, dept.id, -1);
	}
}
