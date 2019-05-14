package com.jeancoder.project.entry.project.aj

import com.jeancoder.project.ready.AvailabilityStatus
import com.jeancoder.project.ready.dto.SysMerchantInfo
import com.jeancoder.project.ready.service.ProjectService
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcPage

JcPage<SysMerchantInfo> page = new JcPage<SysMerchantInfo>();
page.setPn(1);
page.setPs(20);

page = ProjectService.find_merch(page);

Result result = new Result();
AvailabilityStatus obj = AvailabilityStatus.available([] as String[], page.result);

result.setData(obj);
return result;