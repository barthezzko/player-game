package com.barthezzko.playergame.sockets;

import org.apache.log4j.Logger;

import com.barthezzko.playergame.impl.MessageImpl;
import com.barthezzko.playergame.misc.PlayerGameException;
import com.barthezzko.playergame.model.Message;

public class SocketUtils {

	private final static String DELIMITER = ":"; // FIX-like marshalling
	private static Logger logger = Logger.getLogger(SocketUtils.class);

	public static String marshall(Message msg) {
		return msg.getReceiver() + DELIMITER + msg.getSender() + DELIMITER + msg.getPayload();
	}

	public static Message unmarshall(String raw) {
		if (logger.isDebugEnabled()) {
			logger.debug("\n---\nRAW: [" + raw + "]\n---");
		}
		String[] tokens = raw.split(DELIMITER);
		if (tokens.length != 3) {
			throw new PlayerGameException("Should be exactly 3 tokens");
		}
		return new MessageImpl.Builder().payload(tokens[2]).receiver(tokens[0]).sender(tokens[1]).build();
	}
}
