package com.jeancoder.project.internal.incall

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.http.JCThreadLocal
import com.jeancoder.project.ready.dto.AccountSession
import com.jeancoder.project.ready.dto.AuthRole
import com.jeancoder.project.ready.dto.AuthUser
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.internal.AuthRoleService
import com.jeancoder.project.ready.internal.AuthUserService
import com.jeancoder.project.ready.internal.SessionService
import com.jeancoder.project.ready.obj.AuthToken
import com.jeancoder.project.ready.service.ProjectServiceCarry

/**
 * @param token String
 * @param pid   BigInteger
 */

ProjectServiceCarry project_service = ProjectServiceCarry.INSTANCE();

def pid = JC.internal.param('pid');
def token = JC.internal.param('token');

SysProjectInfo project = project_service.INSTANCE().get_proj(pid);


SessionService session_service = SessionService.INSTANCE();

AccountSession session = session_service.flush_session(project, token, 1);
if(session==null) {
	return null;
}
BigInteger user_id = session.basic_id;
AuthUserService user_service = AuthUserService.INSTANCE();
AuthUser user = user_service.get_by_id(user_id,pid);
AuthToken at = new AuthToken(user, session);
List<AuthRole> user_roles = AuthRoleService.INSTANCE().user_roles(user,pid);
at.setRoles(user_roles);
//at.setFunctions(functions);
return at;