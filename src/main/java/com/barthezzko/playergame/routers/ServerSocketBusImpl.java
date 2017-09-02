package com.barthezzko.playergame.routers;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.barthezzko.playergame.interfaces.Bus;
import com.barthezzko.playergame.interfaces.Listener;
import com.barthezzko.playergame.interfaces.Msg;
import com.barthezzko.playergame.sockets.ServerSocketAPI;
import com.barthezzko.playergame.sockets.SocketUtils;
import com.barthezzko.playergame.sockets.SocketUtils.SocketAPI;

public class ServerSocketBusImpl implements Bus {

	private Logger logger = Logger.getLogger(ServerSocketBusImpl.class);
	private final SocketAPI socketAPI;
	private Map<String, Listener> listenerMap = new HashMap<>();

	public ServerSocketBusImpl() {
		socketAPI = new ServerSocketAPI(9999, (line) -> {
			Msg msg = SocketUtils.unmarshall(line);
			Listener listener = listenerMap.get(msg.getReceiver());
			if (listener != null) {
				if (!listener.active()) {
					logger.warn("Listener [" + msg.getReceiver() + "] is not more accepting messages. Exiting...");
					return true;
				}
				logger.debug("Received from another JVM:" + msg);
				listener.onMessage(msg);
			} else {
				logger.debug("No listener for key :" + msg.getReceiver());
			}
			return false; // should continue
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
			String msgString = SocketUtils.marshall(msg);
			socketAPI.send(msgString);
		}
	}
}