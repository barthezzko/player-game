package com.barthezzko.playergame.busimpl;

import com.barthezzko.playergame.model.Listener;
import com.barthezzko.playergame.model.Message;

public class LoopBackBusImpl extends BusBase {

	@Override
	public void register(String msgKey, Listener listener) {
		addListener(msgKey, listener);
	}

	@Override
	public void publish(Message msg) {
		Listener listener = getListener(msg.getReceiver());
		addMessage(msg);
		if (listener != null) {
			listener.onMessage(msg);
		} else {
			logger.info("Message dropped " + msg);
		}
	}
}