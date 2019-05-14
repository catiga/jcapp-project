package com.jeancoder.project.ready.service

import java.sql.Timestamp

import com.jeancoder.project.ready.dto.DomainObject
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.support.exception.DomainExistException
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.jdbc.template.CarryJcDaoTemplate

class ProjectServiceCarry extends CarryJcDaoTemplate<SysProjectInfo> {
	
	JCLogger LOGGER = LoggerSource.getLogger(ProjectServiceCarry.class.getName());
	
	final static ProjectServiceCarry _instance_ = new ProjectServiceCarry();
	
	public static ProjectServiceCarry INSTANCE() {
		 return _instance_;
	}
	
	def get_root_proj() {
		def sql = 'select * from SysProjectInfo where flag!=? and root=?';
		return JcTemplate.INSTANCE().get(SysProjectInfo, sql, -1, 1);
	}

	public JcPage<SysProjectInfo> find_proj(JcPage<SysProjectInfo> page) {
		page = this.find(page, "select * from SysProjectInfo where flag!=? order by root desc", -1);
		return page;
	}
	
	public SysProjectInfo get_proj(def id) {
		if(!id) {return null;}
		String sql = "select * from SysProjectInfo where id=?";
		return this.get(sql, id);
	}
	
	public void update_domain(SysProjectInfo project, String new_domain) {
		if(project.getDomain()!=null&&project.getDomain().equals(new_domain)) {
			return;
		}
		String sql = "select * from SysProjectInfo where flag!=? and domain=? and id!=?";
		List<SysProjectInfo> result = this.find(sql, -1, new_domain, project.getId());
		if(result) {
			throw new DomainExistException();
		}
		//first update project domain field
		project.setDomain(new_domain);
		this.update(project);
		//to update domain object tables
		sql = "select * from DomainObject where flag!=? and dtype=? and oid=?";
		DomainService dd = DomainService.INSTANCE();
		List<DomainObject> domain_objects = dd.find(sql, -1, SysProjectInfo.class.getName(), project.getId());
		if(!domain_objects) {
			DomainObject do_obj = new DomainObject();
			do_obj.setC_time(new Timestamp(Calendar.instance.getTimeInMillis()));
			do_obj.setDomain(new_domain);
			do_obj.setDtype(SysProjectInfo.class.getName());
			do_obj.setFlag(0);
			do_obj.setOid(project.getId())
			do_obj.setParent_domain(null);
			this.save(do_obj);
		} else {
			DomainObject do_obj = domain_objects.get(0);
			do_obj.setDomain(new_domain);
			this.update(do_obj);
		}
	}

	public SysProjectInfo get_proj_by_domain(String domain) {
		String sql = "select * from SysProjectInfo where flag!=? and domain=?";
		List<SysProjectInfo> projects = this.find(sql, -1, domain);
		
		if(projects!=null&&projects.size()>1) {
			LOGGER.error("sys_domain_error:" + domain + " repeat");
			return null;
		} else if(projects==null||projects.isEmpty()) {
			return null;
		}
		SysProjectInfo project = projects.get(0);
		
		//导入该project下的所有微信配置
//		hql = "select s from SysProjectWxConfig s where s.flag!=? and s.project.id=?";
//		List<SysProjectWxConfig> wxconfigs = sysProjectWxConfigDao.find(hql, -1, project.getId());
//		project.setWxconfigs(wxconfigs);
		
		return project;
	}
	
}
