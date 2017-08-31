package com.barthezzko.playergame.designed;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.barthezzko.playergame.interfaces.Listener;
import com.barthezzko.playergame.interfaces.Msg;
import com.barthezzko.playergame.interfaces.Publisher;
import com.barthezzko.playergame.interfaces.Router;

public class LoopBackRouterImpl implements Router, Publisher {
	
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
}