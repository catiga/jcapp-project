package com.jeancoder.project.ready.form

/**
 * id:core_id,
	name:core_name,
	info:core_info,
	type:core_type,
	code:core_code,
	op:core_op
 * @author jackielee
 *
 */
class DeptForm {
	
	BigInteger id;
	
	String name;
	
	String info;
	
	Integer type;
	
	String code;
	
	String op;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}
	
}
