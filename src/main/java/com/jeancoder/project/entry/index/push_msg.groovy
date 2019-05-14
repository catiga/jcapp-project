package com.jeancoder.project.entry.index

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.MemSource
import com.jeancoder.core.http.ChannelWrapper
import com.jeancoder.project.ready.ServerHolder

import io.netty.handler.codec.http.websocketx.TextWebSocketFrame

ChannelWrapper wrapper = ServerHolder.getMap().get(ServerHolder.getMap().keySet().iterator().next());
wrapper.push(new TextWebSocketFrame('fanhui'));
return "success";