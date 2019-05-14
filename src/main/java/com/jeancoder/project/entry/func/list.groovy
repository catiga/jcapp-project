package com.jeancoder.project.entry.func

import com.jeancoder.project.ready.dto.SysFunction
import com.jeancoder.project.ready.service.FuncService
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcPage

JCRequest req = RequestSource.getRequest();

def pn = req.getParameter("pn");
if(pn==null) {
	pn = 1;
}
def ps = 20;

JcPage<SysFunction> page = new JcPage<SysFunction>();
page.setPn(Integer.valueOf(pn));
page.setPs(ps);

def pid = req.getParameter("pid");
SysFunction parent = null;
if(pid) {
	parent = FuncService.get_by_id(pid);
}

page = FuncService.find_by_parent(page, parent);
Result view = new Result();
view.setView("func/list");
view.addObject("page", page);
view.addObject("parent", parent);

return view;