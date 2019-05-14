package com.jeancoder.project.ready.service

import java.sql.Timestamp

import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.project.ready.dto.AuthUser
import com.jeancoder.project.ready.entity.AdminOpLog
import com.jeancoder.project.ready.helper.GlobalHolder

class AdminLogService {

	final static AdminLogService INSTANCE = new AdminLogService();
	
	static final JcTemplate jc_template = JcTemplate.INSTANCE();
	
	def record(def pid = GlobalHolder.proj.id, JCRequest request, AuthUser user, String ip, def exeresult) {
		AdminOpLog log = new AdminOpLog();
		log.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
		log.addr = ip;
		log.c_time = log.a_time;
		log.flag = 0;
		log.proj_id = pid;
		log.uk = user.id;
		log.un = user.name;
		log.ud = JackSonBeanMapper.mapToJson(request.getParameterMap());
		log.execode = '0';
		//处理res
		def res = request.getRequestURI();
		if(!res.startsWith('/')) {
			res = '/' + res;
		}
		if(!res.endsWith('/')) {
			res = res + '/';
		}
		log.res = res;
		jc_template.save(log);
	}
	
	def find(def pid = GlobalHolder.proj.id, def page, def user_id, def res) {
		def sql = 'select * from AdminOpLog where flag!=? and proj_id=?';
		def params = []; params.add(-1); params.add(pid);
		if(user_id) {
			sql += ' and uk=?';
			params.add(user_id);
		}
		if(res) {
			if(!res.toString().startsWith('/')) {
				res = '/' + res;
			}
			if(!res.toString().endsWith('/')) {
				res = res + '/';
			}
			sql += ' and res=?';
			params.add(res);
		}
		sql += ' order by a_time desc';
		return jc_template.find(AdminOpLog, page, sql, params.toArray());
	}
}
