package com.barthezzko.playergame.impl;

import static com.barthezzko.playergame.designed.GameRun.IRINA;

import org.apache.log4j.Logger;

import com.barthezzko.playergame.designed.NamedPlayer;
import com.barthezzko.playergame.routers.ServerSocketBusImpl;

public class SocketGameServer {

	private static Logger logger = Logger.getLogger(SocketGameClient.class);

	public static void main(String[] args) {
		ServerSocketBusImpl bus = new ServerSocketBusImpl();
		bus.register(IRINA, new NamedPlayer(bus, "Irina Plaksina"));
		logger.info("irina registered, sending message...");
		bus.shutdown();
	}
}
