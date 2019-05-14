package com.jeancoder.project.ready

import com.jeancoder.core.result.Result

class AjaxUtil {
	
	public static Result success() {
		Result result = new Result();
		result.setData(AvailabilityStatus.available());
		return result;
	}
	
	public static Result successs(String code, Object data) {
		Result result = new Result();
		result.setData(AvailabilityStatus.available([code] as String[], data));
		return result;
	}

	public static Result successs(String[] codes, Object data) {
		Result result = new Result();
		result.setData(AvailabilityStatus.available(codes, data));
		return result;
	}
	
	
	public static Result fail() {
		Result result = new Result();
		result.setData(AvailabilityStatus.notAvailable([] as String[]));
		return result;
	}
	
	public static Result fail(String code, Object data) {
		Result result = new Result();
		result.setData(AvailabilityStatus.notAvailable([code] as String[], data));
		return result;
	}
	
	public static Result fail(String[] codes, Object data) {
		Result result = new Result();
		result.setData(AvailabilityStatus.notAvailable(codes, data));
		return result;
	}
	
}
