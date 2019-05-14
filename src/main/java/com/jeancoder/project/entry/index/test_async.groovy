package com.jeancoder.project.entry.index

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.project.ready.dto.SysProjectInfo

println 'main thread=' + Thread.currentThread();

def i = 0;


JC.thread.run(3000, {
	println 'i====================' + i;
	println 'i====================' + (test_add(i));
	
	def sql = 'select * from SysProjectInfo where flag!=?';
	println sql;
	
	def result = JcTemplate.INSTANCE().find(SysProjectInfo, sql, -1);
	println result;
	println '准备休息5秒钟';
	Thread.currentThread().sleep(5000);
	
	println '这个不应该被执行';
	return "这里是返回给回调函数的";
}, {
	// e 为回调函数
	e->
	if(e.success) {
		println 'success to back=' + e.code
		println 'succ data=' + e.data;
	} else {
		println 'msg=' + e.msg;
	}
	println this;
});

Result view = new Result();
view.setData('执行完毕');
return view;


def test_add(def i) {
	return ++i;
}