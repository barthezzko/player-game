package com.barthezzko.playergame.impl;

import static com.barthezzko.playergame.designed.GameRun.IRINA;
import static com.barthezzko.playergame.designed.GameRun.MIKE;

import org.apache.log4j.Logger;

import com.barthezzko.playergame.designed.MessageImpl;
import com.barthezzko.playergame.designed.NamedPlayer;
import com.barthezzko.playergame.routers.ClientSocketBusImpl;

public class SocketGameClient {

	private static Logger logger = Logger.getLogger(SocketGameClient.class);

	public static void main(String[] args) {
		ClientSocketBusImpl bus = new ClientSocketBusImpl();
		
		bus.register(MIKE, new NamedPlayer(bus, "Mikhail Baytsurov"));
		bus.publish(new MessageImpl.Builder().payload("initial").sender(IRINA).receiver(MIKE).build());

		logger.info("mike registered, sending message...");
		bus.shutdown();
	}

}
