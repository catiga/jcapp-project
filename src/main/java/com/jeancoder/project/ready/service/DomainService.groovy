package com.jeancoder.project.ready.service

import com.jeancoder.project.ready.dto.DomainObject
import com.jeancoder.project.ready.support.exception.DomainExistException
import com.jeancoder.jdbc.template.CarryJcDaoTemplate

class DomainService extends CarryJcDaoTemplate<DomainObject> {

	final static DomainService _INSTANCE_ = new DomainService();
	
	public static DomainService INSTANCE() {
		return _INSTANCE_;
   }
   
   public DomainObject get_sys_domain(def domain) {
	   String sql = 'select * from DomainObject where flag!=? and domain=?';
	   List<DomainObject> domains = this.find(sql, -1, domain);
	   if(domains==null) {
		   return null;
	   } else if(domains.size()==1) {
		   DomainObject doo = domains.get(0);
		   return doo;
	   } else {
		   throw new DomainExistException();
	   }
   }
}
