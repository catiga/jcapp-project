package com.jeancoder.project.entry.auth.user

import com.jeancoder.project.ready.dto.AuthUser
import com.jeancoder.project.ready.helper.GlobalHolder
import com.jeancoder.project.ready.service.AuthUserService
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

JcPage<AuthUser> page = new JcPage<AuthUser>();
page.setPn(Integer.valueOf(pn));
page.setPs(ps);

AuthUserService user_service = AuthUserService.INSTANCE();

def sp = JC.request.param('sp')?.trim();
if(sp=='0') {
	sp = null;
}
if(GlobalHolder.proj.root!=1) {
	sp = null;
}
def name_moile = JC.request.param('name_moile')?.trim();
if(name_moile) {
	name_moile = URLDecoder.decode(URLDecoder.decode(name_moile, 'utf-8'), 'utf-8');
}

page = user_service.find_user(page, sp,name_moile);

Result view = new Result();
view.setView("auth/user/list");
view.addObject("page", page);

view.addObject('root', GlobalHolder.proj.root);

//获取系统所有项目
def proj_page = new JcPage<>();
proj_page.pn = 1; proj_page.ps = 50;
proj_page = ProjectServiceCarry.INSTANCE().find_proj(proj_page);
view.addObject('proj_page', proj_page);

view.addObject('sp', sp);

view.addObject('name_moile', name_moile);

return view;