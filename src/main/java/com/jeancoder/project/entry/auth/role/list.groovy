package com.jeancoder.project.entry.auth.role

import com.jeancoder.project.ready.constant.RoleType
import com.jeancoder.project.ready.dto.AuthRole
import com.jeancoder.project.ready.helper.GlobalHolder
import com.jeancoder.project.ready.service.AuthRoleService
import com.jeancoder.project.ready.service.ProjectServiceCarry
import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcPage

JCRequest req = RequestSource.getRequest();

def pn = req.getParameter("pn");
if(pn==null) {
	pn = 1;
}
def ps = 20;

JcPage<AuthRole> page = new JcPage<AuthRole>();
page.setPn(Integer.valueOf(pn));
page.setPs(ps);

AuthRoleService _service = AuthRoleService.INSTANCE();

def sp = JC.request.param('sp')?.trim();
if(sp=='0') {
	sp = null;
}
if(GlobalHolder.proj.root!=1) {
	sp = null;
}

page = _service.find_role(page, sp);

Result view = new Result();
view.setView("auth/role/list");
view.addObject("page", page);

view.addObject('root', GlobalHolder.proj.root);

//获取系统所有项目
def proj_page = new JcPage<>();
proj_page.pn = 1; proj_page.ps = 50;
proj_page = ProjectServiceCarry.INSTANCE().find_proj(proj_page);

Map<String,String> proj_map = new HashMap<String,String>();
for (def item : proj_page.result) {
	proj_map.put(item.id.toString(),item.proj_name);
}


view.addObject('proj_page', proj_page);
view.addObject('proj_map', proj_map);
view.addObject('roleType', RoleType.getRoleTypeAll());

view.addObject('sp', sp);

return view;