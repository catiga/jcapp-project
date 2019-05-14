package com.jeancoder.project.ready

import java.io.Serializable

public final class AvailabilityStatus implements Serializable{
	
	private boolean available;
	
	private String[] messages;
	
	private Object data;
	
	public AvailabilityStatus() {}
	
	private AvailabilityStatus(boolean result, String[] msgs, Object data) {
		this.available = result;
		this.messages = msgs;
		this.data = data;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public String[] getMessages() {
		return messages;
	}

	public void setMessages(String[] messages) {
		this.messages = messages;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public static AvailabilityStatus available() {
		return new AvailabilityStatus(true, [] as String[], []);
	}
	
	public static AvailabilityStatus available(String[] messages) {
		return new AvailabilityStatus(true, messages, null);
	}
	
	public static AvailabilityStatus available(String[] messages, Object data) {
		return new AvailabilityStatus(true, messages, data);
	}
	
	public static AvailabilityStatus notAvailable(String[] messages) {
		return new AvailabilityStatus(false, messages, null);
	}
	
	public static AvailabilityStatus notAvailable(String[] messages, Object data) {
		return new AvailabilityStatus(false, messages, data);
	}
	
}