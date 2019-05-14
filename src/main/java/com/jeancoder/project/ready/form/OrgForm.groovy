package com.jeancoder.project.ready.form

import java.util.List

class OrgForm {

	String id;
	
	String name;
	
	String code;
	
	String title;
	
	String relationship;
	
	String className;
	
	String type;
	
	List<OrgForm> children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<OrgForm> getChildren() {
		return children;
	}

	public void setChildren(List<OrgForm> children) {
		this.children = children;
	}
	
}
