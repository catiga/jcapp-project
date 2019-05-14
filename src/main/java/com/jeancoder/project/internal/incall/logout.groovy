package com.jeancoder.project.internal.incall

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.service.SessionService

JCLogger LOGGER = LoggerSource.getLogger("login");

SessionService session_service = SessionService.INSTANCE();

def _c_u_k_ = JC.internal.param("token");
def pid = JC.internal.param("pid");

session_service.invalid_session(new BigInteger(pid.toString()), _c_u_k_, 1);

return SimpleAjax.available('', _c_u_k_);