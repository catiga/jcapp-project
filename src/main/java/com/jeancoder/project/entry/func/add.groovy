package com.jeancoder.project.entry.func

import com.jeancoder.project.ready.dto.SysFunction
import com.jeancoder.project.ready.service.FuncService
import com.jeancoder.core.result.Result

def lev = "1";
List<SysFunction> result = FuncService.find_level_funcs(lev);
Result view = new Result();
view.setView("func/add");
view.addObject("first_level_funcs", result);

return view;