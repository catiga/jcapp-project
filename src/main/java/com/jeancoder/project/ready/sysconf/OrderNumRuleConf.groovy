package com.jeancoder.project.ready.sysconf

class OrderNumRuleConf implements ConfFace {

	String code = 'ORNU';
	
	String name = '订单号规则';
	
	List<ConfMod> mods = [new ConfMod(key: 'prefix', name: '订单前缀'), new ConfMod(key: 'withstore', name: '附加门店编号', inputtype:'checkbox')];
}
