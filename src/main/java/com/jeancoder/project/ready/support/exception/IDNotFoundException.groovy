package com.jeancoder.project.ready.support.exception;

class IDNotFoundException extends RuntimeException {

	public IDNotFoundException(String clz_name) {
		super(clz_name + " does not include ID field.");
	}
}
