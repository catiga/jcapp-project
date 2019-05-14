package com.jeancoder.project.internal.org

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.jdbc.JcPage
import com.jeancoder.project.ready.dto.AuthUser
import com.jeancoder.project.ready.service.AuthUserService

def pid = JC.internal.param('pid');

def pn = JC.internal.param('pn');
if(pn==null||pn<1) {
	pn = 1;
}
def ps = 100;

JcPage<AuthUser> page = new JcPage<AuthUser>();
page.setPn(Integer.valueOf(pn));
page.setPs(ps);

AuthUserService user_service = AuthUserService.INSTANCE();

page = user_service.find_user(pid, page, null,null);

return page.result;