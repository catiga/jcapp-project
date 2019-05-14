package com.jeancoder.project.entry.project

import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcPage
import com.jeancoder.project.ready.dto.DicConfig
import com.jeancoder.project.ready.dto.SysProjectInfo
import com.jeancoder.project.ready.helper.GlobalHolder
import com.jeancoder.project.ready.service.DicConfigService
import com.jeancoder.project.ready.service.ProjectService

JCRequest req = RequestSource.getRequest();

def pn = req.getParameter("pn");
if(pn==null) {
	pn = 1;
}
def ps = 20;

JcPage<SysProjectInfo> page = new JcPage<SysProjectInfo>();
page.setPn(Integer.valueOf(pn));
page.setPs(ps);

page = ProjectService.find_proj(page);


for (def item : page.result) {
	item.logo = GlobalHolder.INSTANCE.img_path() + '/' + item.logo;
}

Result view = new Result();
view.setView("project/list");
view.addObject("page", page);

DicConfigService config_service = DicConfigService.INSTANCE();
List<DicConfig> root_proj_type = config_service.find_support_configs('6000');
view.addObject('p_types', root_proj_type);

return view;