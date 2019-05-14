package com.jeancoder.project.ready.support.request;

import java.math.BigInteger;

import com.jeancoder.project.ready.dto.SysProjectInfo;
import com.jeancoder.jdbc.JCME2SQL;
import com.jeancoder.jdbc.sql.SqlParser;

public class Test {

	public static void main(String[] argc) {
		SysProjectInfo instance = new SysProjectInfo();
		instance.setId(new BigInteger("1"));
		SqlParser par = JCME2SQL.fullUpdate(instance);
		System.out.println(par);
	}
}
