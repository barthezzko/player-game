package com.barthezzko.playergame.routers;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.barthezzko.playergame.model.Bus;
import com.barthezzko.playergame.model.Listener;
import com.barthezzko.playergame.model.MessageStorage;
import com.barthezzko.playergame.model.Msg;
import com.barthezzko.playergame.sockets.ClientSocketAPI;
import com.barthezzko.playergame.sockets.SocketUtils;
import com.barthezzko.playergame.sockets.SocketUtils.SocketAPI;

public class ClientSocketBusImpl extends MessageStorage implements Bus {

	private Logger logger = Logger.getLogger(this.getClass());
	private Map<String, Listener> listenerMap = new HashMap<>();
	private SocketAPI socketAPI;
	 

	public ClientSocketBusImpl() {
		socketAPI = new ClientSocketAPI(9999, (line) -> {
			Msg msg = SocketUtils.unmarshall(line);
			storeMessage(msg);
			Listener listener = listenerMap.get(msg.getReceiver());
			if (listener != null) {
				logger.debug("Found listener for key:" + msg);
				listener.onMessage(msg);
			} else {
				logger.debug("No listener for key:" + msg.getReceiver() + ", dropping the message");
			}
		});
	}

	@Override
	public void register(String msgKey, Listener listener) {
		listenerMap.put(msgKey, listener);
	}

	@Override
	public void publish(Msg msg) { // publish to sockets
		Listener listener = listenerMap.get(msg.getReceiver());
		if (listener != null) {
			logger.debug("Send to same JVM:" + msg);
			listener.onMessage(msg);
		} else {
			logger.debug("Send to another JVM: " + msg);
			socketAPI.send(SocketUtils.marshall(msg));
		}
	}

}