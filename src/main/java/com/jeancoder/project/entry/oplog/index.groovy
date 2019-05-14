package com.jeancoder.project.entry.oplog

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcPage
import com.jeancoder.project.ready.dto.AuthUser
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.entity.AdminOpLog
import com.jeancoder.project.ready.helper.GlobalHolder
import com.jeancoder.project.ready.service.AdminLogService
import com.jeancoder.project.ready.service.AuthUserService

def user_id = JC.request.param('uk')?.trim();
def res = JC.request.param('res')?.trim();

SysProjectInfo now_proj = GlobalHolder.getProj();

def pn = Integer.valueOf(JC.request.param('pn')?:1);
JcPage<AdminOpLog> page = new JcPage<>();
page.pn = pn;
page.ps = 20;

page = AdminLogService.INSTANCE.find(page, user_id, res);

Result result = new Result();
result.setView("oplog/index");
result.addObject("page", page);


JcPage<AuthUser> user_page = new JcPage<AuthUser>();
user_page.setPn(Integer.valueOf(pn));
user_page.setPs(50);

AuthUserService user_service = AuthUserService.INSTANCE();
user_page = user_service.find_user(user_page, null,null);

result.addObject('user_page', user_page);
result.addObject('res', res);
result.addObject('uk', user_id);

return result;


