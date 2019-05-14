package com.jeancoder.project.ready.support.exception;

class IDEmptyException extends RuntimeException {

	public IDEmptyException(String clz_name) {
		super(clz_name + " does not correct set ID value.");
	}
}
