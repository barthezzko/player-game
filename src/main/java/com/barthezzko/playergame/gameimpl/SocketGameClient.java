package com.barthezzko.playergame.gameimpl;

import static com.barthezzko.playergame.impl.GameRun.IRINA;
import static com.barthezzko.playergame.impl.GameRun.MIKE;

import org.apache.log4j.Logger;

import com.barthezzko.playergame.busimpl.ClientSocketBusImpl;
import com.barthezzko.playergame.impl.MessageImpl;
import com.barthezzko.playergame.impl.NamedPlayer;
import com.barthezzko.playergame.misc.PlayerGameException;

public class SocketGameClient {

	private static Logger logger = Logger.getLogger(SocketGameClient.class);

	public static void main(String[] args) {
		if (args.length != 1) {
			throw new PlayerGameException("Should be exactly 1 parameter (port to start app with), Example: 9999");
		}
		int port = Integer.valueOf(args[0]);
		logger.info("Starting client application on port [" + port + "]");
		ClientSocketBusImpl bus = new ClientSocketBusImpl(port);
		
		logger.info("Client bus created. Registering player...");
		bus.register(MIKE, new NamedPlayer(bus, "Mikhail Baytsurov"));
		
		logger.info("Player registered, publishing message...");
		bus.publish(new MessageImpl.Builder().payload("initial").sender(MIKE).receiver(IRINA).build());
		
		bus.shutdown();
	}

}
