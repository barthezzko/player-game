package com.barthezzko.playergame.routers;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.barthezzko.playergame.interfaces.Bus;
import com.barthezzko.playergame.interfaces.Listener;
import com.barthezzko.playergame.interfaces.Msg;

public class LoopBackRouterImpl implements Bus {
	
	private Logger logger = Logger.getLogger(this.getClass());
	private Map<String, Listener> listenerMap = new HashMap<>();

	@Override
	public void register(String msgKey, Listener listener) {
		listenerMap.put(msgKey, listener);
	}

	@Override
	public void publish(Msg msg) {
		Listener listener = listenerMap.get(msg.getReceiver());
		if (listener != null) {
			listener.onMessage(msg);
		} else {
			logger.info("Message dropped " + msg);
		}
	}

	@Override
	public void shutdown() {
		
	}
}