package com.barthezzko.playergame.busimpl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.barthezzko.playergame.model.Bus;
import com.barthezzko.playergame.model.Listener;
import com.barthezzko.playergame.model.MessageStorage;
import com.barthezzko.playergame.model.Message;

public class LoopBackBusImpl extends MessageStorage implements Bus {

	private Logger logger = Logger.getLogger(this.getClass());
	private Map<String, Listener> listenerMap = new HashMap<>();

	@Override
	public void register(String msgKey, Listener listener) {
		listenerMap.put(msgKey, listener);
	}

	@Override
	public void publish(Message msg) {
		Listener listener = listenerMap.get(msg.getReceiver());
		storeMessage(msg);
		if (listener != null) {
			listener.onMessage(msg);
		} else {
			logger.info("Message dropped " + msg);
		}
	}
}