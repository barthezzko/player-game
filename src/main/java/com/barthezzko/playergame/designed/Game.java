package com.barthezzko.playergame.designed;

import org.apache.log4j.Logger;

import com.barthezzko.playergame.interfaces.Bus;

public class Game {

	private static Logger logger = Logger.getLogger(Game.class);
	private Bus bus;

	public Game(Bus bus) {
		this.bus = bus;
		start();
	}

	private void start() {
		bus.register("mike", new NamedPlayer(bus, "Mikhail Baytsurov"));
		bus.register("irina", new NamedPlayer(bus, "Irina Plaksina"));
		logger.info("players registered, sending message...");
		bus.publish(new MsgImpl("initial", "mike", "irina"));
		bus.shutdown();
	}
}
