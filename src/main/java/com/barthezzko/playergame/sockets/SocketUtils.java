package com.barthezzko.playergame.sockets;

import org.apache.log4j.Logger;

import com.barthezzko.playergame.designed.MsgImpl;
import com.barthezzko.playergame.interfaces.Msg;

public class SocketUtils {

	private final static String DELIMITER = ":"; // FIX-like marshalling
	private static Logger logger = Logger.getLogger(SocketUtils.class);

	public static String marshall(Msg msg) {
		return msg.getReceiver() + DELIMITER + msg.getSender() + DELIMITER + msg.getPayload();
	}

	public static Msg unmarshall(String raw) {
		logger.debug("\n---\nRAW: [" + raw + "]\n---");
		String[] tokens = raw.split(DELIMITER);
		if (tokens.length != 3) {
			throw new RuntimeException("Should be exactly 3 tokens");
		}
		return new MsgImpl(tokens[2], tokens[0], tokens[1]);
	}

	public interface SocketAPI {

		void send(String line);

	}
}
