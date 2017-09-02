package com.barthezzko.playergame.gameimpl;

import static com.barthezzko.playergame.impl.GameRun.IRINA;

import org.apache.log4j.Logger;

import com.barthezzko.playergame.busimpl.ServerSocketBusImpl;
import com.barthezzko.playergame.impl.NamedPlayer;

public class SocketGameServer {

	private static Logger logger = Logger.getLogger(SocketGameClient.class);

	public static void main(String[] args) {
		ServerSocketBusImpl bus = new ServerSocketBusImpl();
		bus.register(IRINA, new NamedPlayer(bus, "Irina Plaksina"));
		logger.info("irina registered, sending message...");
		bus.shutdown();
	}
}
