package com.jeancoder.project;

import com.jeancoder.app.sdk.JC

JC.interceptor.add("project/PreInterceptor", "project/PostInterceptor");
JC.interceptor.add("admin/PreInterceptor", null);
JC.interceptor.add("mod/PreInterceptor", null);
JC.interceptor.add("permission/PreInterceptor", null);
JC.interceptor.add('incall/PreInterceptor', null);