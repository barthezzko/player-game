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
		logger.info("STARTING CLIENT");
		ClientSocketBusImpl bus = new ClientSocketBusImpl();
		logger.info("client bus created. registering player");
		bus.register(MIKE, new NamedPlayer(bus, "Mikhail Baytsurov"));
		logger.info("mike registered, sending message...");
		bus.publish(new MessageImpl.Builder().payload("initial").sender(MIKE).receiver(IRINA).build());
		bus.shutdown();
	}

}
