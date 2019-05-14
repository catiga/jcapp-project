package com.jeancoder.project.entry.org.aj

import com.jeancoder.project.ready.dto.SysDept
import com.jeancoder.project.ready.dto.SysOrgination
import com.jeancoder.project.ready.form.OrgForm
import com.jeancoder.project.ready.service.SysDeptService
import com.jeancoder.project.ready.service.SysOrginationService
import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result

SysOrginationService org_service = SysOrginationService.INSTANCE();
SysDeptService dept_service = SysDeptService.INSTANCE();

SysOrgination root_org = org_service.get_org();

SysDept parent = dept_service.get_by_id(JC.request.param('id'));

List<SysDept> root_depts = dept_service.find_by_parent(root_org, parent);

OrgForm root = new OrgForm();
if(!parent) {
	root.id = root_org.id;
	root.name = root_org.name;
	root.title = root_org.info;
	root.relationship = '001';
	root.type = 0;
} else {
	root.id = parent.id;
	root.name = parent.name;
	root.title = parent.info;
	root.code = parent.code;
	if(parent.type==1) {
		root.className = 'product-dept';
	} else if(parent.type==2) {
		root.className = 'rd-dept';
	} else {
		root.className = 'pipeline1';
	}
	root.relationship = '11';
	if(parent.hasson) {
		root.relationship = root.relationship + '1';
	} else {
		root.relationship = root.relationship + '0';
	}
	root.type = parent.type;
}

List<OrgForm> children = new ArrayList<OrgForm>();
for(SysDept sd : root_depts) {
	OrgForm tmp = new OrgForm();
	tmp.id = sd.id;
	tmp.name = sd.name;
	tmp.title = sd.info;
	tmp.relationship = '11';
	tmp.code = sd.code;
	if(sd.hasson) {
		tmp.relationship = tmp.relationship + '1';
	} else {
		tmp.relationship = tmp.relationship + '0';
	}
	if(sd.type==1) {
		tmp.className = 'product-dept';
	} else if(sd.type==2) {
		tmp.className = 'rd-dept';
	} else {
		tmp.className = 'pipeline1';
	}
	tmp.type = sd.type;
	children.add(tmp);
}
root.children = children;

Result result = new Result();
result.setData(root);

return result;

/*
var datascource = {
  'id': [[${root_org.id}]],
  'name': [[${root_org.name}]],
  'title': [[${root_org.info}]],
  'relationship': '111',
  'children': [
	{ 'id': 'n2','name': 'Tie Hua', 'title': 'senior engineer', 'relationship': '110' },
	{ 'id': 'n3','name': 'Hei Hei', 'title': 'senior engineer', 'relationship': '111' }
  ]
};
*/

