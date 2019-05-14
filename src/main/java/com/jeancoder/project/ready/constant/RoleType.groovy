package com.jeancoder.project.ready.constant

class RoleType {
	private static final Map<String,String>  roleType  = new LinkedHashMap<>();
	static {
		roleType.put("0000", "超级管理员");
		roleType.put("1000", "项目管理员");
		roleType.put("3000", "在线客服");
		roleType.put("4000", "门店员工");
		roleType.put("5000", "通知推送");
		roleType.put("6000", "专家技术类");
		roleType.put("2000", "其他类");
	}
	
	
	public static Map getRoleTypeAll() {
		return roleType;
	}
	
	public static  Map getRoleName(String type) {
		return roleType.get(type);
	}
}
