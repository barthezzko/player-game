package com.barthezzko.playergame.impl;

import org.apache.log4j.Logger;

import com.barthezzko.playergame.designed.MsgImpl;
import com.barthezzko.playergame.designed.NamedPlayer;
import com.barthezzko.playergame.routers.ClientSocketBusImpl;

public class SocketGameClient {

	private static Logger logger = Logger.getLogger(SocketGameClient.class);

	public static void main(String[] args) {
		ClientSocketBusImpl bus = new ClientSocketBusImpl();
		
		bus.register("mike", new NamedPlayer(bus, "Mikhail Baytsurov"));
		bus.publish(new MsgImpl("initial", "irina", "mike"));

		bus.listen();
		logger.info("mike registered, sending message...");
		bus.shutdown();
	}

}
