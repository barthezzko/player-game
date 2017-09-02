package com.barthezzko.playergame.busimpl;

import com.barthezzko.playergame.model.Listener;
import com.barthezzko.playergame.model.Message;
import com.barthezzko.playergame.sockets.ClientSocketAPI;
import com.barthezzko.playergame.sockets.SocketAPI;
import com.barthezzko.playergame.sockets.SocketUtils;

public class ClientSocketBusImpl extends BusBase {

	private SocketAPI socketAPI;

	public ClientSocketBusImpl(int port) {
		socketAPI = new ClientSocketAPI(port, (line) -> {
			Message msg = SocketUtils.unmarshall(line);
			addMessage(msg);
			Listener listener = getListener(msg.getReceiver());
			if (listener != null) {
				if (logger.isDebugEnabled()) {
					logger.debug("Found listener for key:" + msg);
				}
				listener.onMessage(msg);
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("No listener for key:" + msg.getReceiver() + ", dropping the message");
				}
			}
		});
	}

	@Override
	public void register(String msgKey, Listener listener) {
		addListener(msgKey, listener);
	}

	@Override
	public void publish(Message msg) { // publish to sockets
		Listener listener = getListener(msg.getReceiver());
		if (listener != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Send to same JVM:" + msg);
			}
			listener.onMessage(msg);
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("Send to another JVM: " + msg);
			}
			socketAPI.send(SocketUtils.marshall(msg));
		}
	}

}