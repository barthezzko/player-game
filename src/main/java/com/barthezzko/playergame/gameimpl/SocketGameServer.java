package com.barthezzko.playergame.gameimpl;

import static com.barthezzko.playergame.impl.GameRun.IRINA;

import org.apache.log4j.Logger;

import com.barthezzko.playergame.busimpl.ServerSocketBusImpl;
import com.barthezzko.playergame.impl.NamedPlayer;
import com.barthezzko.playergame.misc.PlayerGameException;

public class SocketGameServer {

	private static Logger logger = Logger.getLogger(SocketGameClient.class);

	public static void main(String[] args) {
		if (args.length != 1) {
			throw new PlayerGameException("Should be exactly 1 parameter (port to start app with), Example: 9999");
		}
		int port = Integer.valueOf(args[0]);
		
		logger.info("Starting server application on port [" + port + "]");
		ServerSocketBusImpl bus = new ServerSocketBusImpl(port);
		
		logger.info("Client bus started. Creating player...");
		bus.register(IRINA, new NamedPlayer(bus, "Irina Plaksina"));
		
		logger.info("Player registered. Waiting for the message from another player...");
		bus.shutdown();
	}
}
