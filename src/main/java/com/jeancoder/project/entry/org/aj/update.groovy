package com.jeancoder.project.entry.org.aj

import java.sql.Timestamp

import com.jeancoder.project.ready.AjaxUtil
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.SysDept
import com.jeancoder.project.ready.dto.SysOrgination
import com.jeancoder.project.ready.form.DeptForm
import com.jeancoder.project.ready.helper.GlobalHolder
import com.jeancoder.project.ready.service.SysDeptService
import com.jeancoder.project.ready.service.SysOrginationService
import com.jeancoder.app.sdk.JC

SysDeptService dept_service = SysDeptService.INSTANCE();

def dept_form = JC.extract.fromRequest(DeptForm.class);

def op = dept_form.op;
def id = dept_form.id;

if(id==null||id<1) {
	//根
	SysOrginationService org_service = SysOrginationService.INSTANCE();
	SysOrgination root_org = org_service.get_org();
	SysDept son = new SysDept();
	son.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	son.c_time = son.a_time;
	son.code = dept_form.code;
	son.flag = 0;
	son.info = dept_form.info;
	son.level = 1;
	son.name = dept_form.name;
	son.org = root_org.id;
	son.parent = null;
	son.type = 1;
	son.pid = GlobalHolder.proj.id;
	dept_service.save(son);
	return SimpleAjax.available();
}

SysDept dept = dept_service.get_by_id(id);
if(!dept) {
	return SimpleAjax.notAvailable('dept_not_found,部门或岗位未找到');
}
if(dept.parent==0) {
	dept.parent = null;
}
if(op=='1') {
	//更新
	dept.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	dept.code = dept_form.code;
	dept.name = dept_form.name;
	dept.info = dept_form.info;
	dept.type = dept_form.type;
	dept_service.update(dept);
	return SimpleAjax.available();
} else if(op=='2') {
	//创建子部门
	SysDept son = new SysDept();
	son.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	son.c_time = son.a_time;
	son.code = dept_form.code;
	son.flag = 0;
	son.info = dept_form.info;
	son.level = dept.level + 1;
	son.name = dept_form.name;
	son.org = dept.org;
	son.parent = dept.id;
	son.type = dept_form.type;
	son.pid = GlobalHolder.proj.id;
	
	dept_service.save(son);
	dept.hasson = true;
	dept_service.update(dept);
	return SimpleAjax.available();
} else {
	//return AjaxUtil.fail('unsupport_op', null);
	return SimpleAjax.notAvailable('unsupport_op,不支持的数据类型');
}


