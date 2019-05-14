package com.jeancoder.project.entry.index

import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.helper.GlobalHolder
import com.jeancoder.core.result.Result

SysProjectInfo now_proj = GlobalHolder.getProj();

Result result = new Result();
result.setView("index/index");
result.addObject("now_proj", now_proj);
return result;