package com.jeancoder.project.ready.sysconf

class FilePathConf implements ConfFace {

	String code = 'FPATH';
	
	String name = '文件存储根';
	
	List<ConfMod> mods = [new ConfMod(key: 'rootpath', name: '路径地址')];
}
