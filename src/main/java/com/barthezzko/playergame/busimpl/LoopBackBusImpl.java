package com.barthezzko.playergame.busimpl;

import com.barthezzko.playergame.model.Listener;
import com.barthezzko.playergame.model.Message;

/**
 * Loopback-based implementation of Bus interface. Single-threaded, uses
 * internal BusBase storage for routing Message instances
 * 
 * <br/>
 * If the Player's move takes a while - that's going to hang up the whole game.
 * It is added just for demonstration purposes
 * 
 * @see {@link ThreadsBusImpl} and {@link SocketBusImpl}
 * 
 * @author barthezzko
 *
 */
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