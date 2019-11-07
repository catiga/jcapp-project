package com.jeancoder.project.ready.sysconf

interface ConfFace {
	
	final static List<ConfFace> all_configs = [new SmsConf(), new FilePathConf(), new OrderNumRuleConf()];
	
	def getCode();
	
	List<ConfMod> getMods();
}
