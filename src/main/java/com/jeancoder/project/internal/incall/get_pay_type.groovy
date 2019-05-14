package com.jeancoder.project.internal.incall

import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.project.ready.ajax.SimpleAjax
import com.jeancoder.project.ready.dto.ProjectGeneralConfig
import com.jeancoder.project.ready.service.ProjectService

JCLogger logger = LoggerSource.getLogger();
JCRequest request = RequestSource.getRequest();
def pid =CommunicationSource.getParameter("pid");
try {
	List<ProjectGeneralConfig> item = ProjectService.get_all_pay_type(new BigInteger(pid));
	return SimpleAjax.available("",item);
} catch (Exception e) {
	logger.info("查询支付方式失败"+e);
	return SimpleAjax.notAvailable("查询支付方式失败");
}
