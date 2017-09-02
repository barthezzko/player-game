package com.barthezzko.playergame.busimpl;

import java.util.function.Function;

import com.barthezzko.playergame.model.Listener;
import com.barthezzko.playergame.model.Message;
import com.barthezzko.playergame.sockets.ClientSocketAPI;
import com.barthezzko.playergame.sockets.ServerSocketAPI;
import com.barthezzko.playergame.sockets.SocketAPI;
import com.barthezzko.playergame.sockets.SocketUtils;

public class SocketBusImpl extends BusBase {

	public enum Mode {
		SERVER, CLIENT
	}

	private SocketAPI socketAPI;
	
	public SocketBusImpl(int port, Mode type) {
		if (type == Mode.SERVER){
			socketAPI = new ServerSocketAPI(port, lineProcessor);
		} else {
			socketAPI = new ClientSocketAPI(port, lineProcessor);
		}
	}
	
	private Function<String, Boolean> lineProcessor = (line) -> {
		Message msg = SocketUtils.unmarshall(line);
		Listener listener = getListener(msg.getReceiver());
		addMessage(msg);
		if (listener != null) {
			if (!listener.active()) {
				logger.warn("Listener [" + msg.getReceiver() + "] is not more accepting messages. Exiting...");
				return true; // this stands for "No more messages"
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Received from another JVM:" + msg);
			}
			listener.onMessage(msg);
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("No listener for key :" + msg.getReceiver());
			}
		}
		return false; // should continue
	};

	@Override
	public void publish(Message msg) {
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

	@Override
	public void register(String msgKey, Listener listener) {
		addListener(msgKey, listener);
	}

}
