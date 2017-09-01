package com.barthezzko.playergame.impl;

import org.apache.log4j.Logger;

import com.barthezzko.playergame.designed.NamedPlayer;
import com.barthezzko.playergame.routers.ServerSocketBusImpl;

public class SocketGameServer {

	private static Logger logger = Logger.getLogger(SocketGameClient.class);

	public static void main(String[] args) {
		ServerSocketBusImpl bus = new ServerSocketBusImpl();

		bus.register("irina", new NamedPlayer(bus, "Irina Plaksina"));

		logger.info("irina registered, sending message...");

		logger.info("blocking listen started");
		bus.listen();
		bus.shutdown();
	}
}
