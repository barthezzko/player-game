package com.barthezzko.playergame.gameimpl;

import static com.barthezzko.playergame.impl.GameRun.IRINA;
import static com.barthezzko.playergame.impl.GameRun.MIKE;

import org.apache.log4j.Logger;

import com.barthezzko.playergame.busimpl.ClientSocketBusImpl;
import com.barthezzko.playergame.impl.MessageImpl;
import com.barthezzko.playergame.impl.NamedPlayer;

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
