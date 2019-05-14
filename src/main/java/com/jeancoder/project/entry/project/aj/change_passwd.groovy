package com.jeancoder.project.entry.project.aj

import com.jeancoder.project.ready.AvailabilityStatus
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.AuthUser
import com.jeancoder.project.ready.dto.SysMerchantInfo
import com.jeancoder.project.ready.helper.GlobalHolder
import com.jeancoder.project.ready.service.AuthUserService
import com.jeancoder.project.ready.service.ProjectService
import com.jeancoder.project.ready.support.security.MD5Util

import javax.servlet.http.Cookie

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCCookie
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcPage

AuthUser user = GlobalHolder.authToken?.user;

def new_password = JC.request.param('n')?.trim();
def old_password = JC.request.param('o')?.trim();

if(old_password==null||new_password==null||old_password==''||new_password=='') {
	return SimpleAjax.notAvailable('param_error,参数错误');
}

if(user.password!=MD5Util.getMD5(old_password)) {
	return SimpleAjax.notAvailable('check_error,旧密码校验失败');
}

def rows = AuthUserService.INSTANCE().update_user_password(user.id, MD5Util.getMD5(new_password));
if(rows>0) {
	//删除cookie
	//_c_u_k_adm_
	JCResponse response = ResponseSource.getResponse();
	Cookie c = new Cookie('_c_u_k_adm_', '');
	c.path = '/';
	c.maxAge = 0;
	response.addCookie(new JCCookie(c));
	return SimpleAjax.available();
}

return SimpleAjax.notAvailable('obj_not_found,用户未找到');


