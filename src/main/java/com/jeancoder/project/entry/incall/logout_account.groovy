package com.jeancoder.project.entry.incall

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.http.JCCookie
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.service.SessionService

JCLogger LOGGER = LoggerSource.getLogger("login");

SessionService session_service = SessionService.INSTANCE();

def token = JC.request.param('token')?.trim();

session_service.invalid_session(token, 1);

return SimpleAjax.available();