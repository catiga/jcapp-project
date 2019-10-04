package com.jeancoder.project.ready.sysconf

class SmsConf implements ConfFace {

	String code = 'SMS';
	
	String name = '短信';
	
	List<ConfMod> mods = [new ConfMod(key: 'uri', name: '请求地址'), new ConfMod(key: 'un', name:'用户名'), new ConfMod(key:'up', name:'密钥')];
}
