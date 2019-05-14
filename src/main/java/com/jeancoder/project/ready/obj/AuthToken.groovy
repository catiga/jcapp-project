package com.jeancoder.project.ready.obj

import java.util.List

import com.jeancoder.project.ready.dto.AccountSession
import com.jeancoder.project.ready.dto.AuthRole
import com.jeancoder.project.ready.dto.AuthUser
import com.jeancoder.project.ready.dto.SysFunction

class AuthToken {

	AuthUser user;
	
	AccountSession session;
	
	List<AuthRole> roles;
	
	def functions;
	
	public AuthToken(AuthUser user, AccountSession session) {
		this.user = user;
		this.session = session;
	}

	public AuthUser getUser() {
		return user;
	}

	public void setUser(AuthUser user) {
		this.user = user;
	}

	public AccountSession getSession() {
		return session;
	}

	public void setSession(AccountSession session) {
		this.session = session;
	}

	public List<AuthRole> getRoles() {
		return roles;
	}

	public void setRoles(List<AuthRole> roles) {
		this.roles = roles;
	}

	public def getFunctions() {
		return functions;
	}

	public void setFunctions(def functions) {
		this.functions = functions;
	}
	
}
