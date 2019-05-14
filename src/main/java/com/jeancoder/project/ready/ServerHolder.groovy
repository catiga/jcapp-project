package com.jeancoder.project.ready

import com.jeancoder.core.http.ChannelWrapper

class ServerHolder {

	static Map<String, ChannelWrapper> map = new HashMap<String, ChannelWrapper>();
	
	static void add(String id, ChannelWrapper obj) {
		map.put(id, obj);
	}
	
	static ChannelWrapper get(String id) {
		return map.get(id);
	}
}
