package com.barthezzko.playergame.impl;

import org.apache.log4j.Logger;

import com.barthezzko.playergame.model.Bus;

public class GameRun {

	private Logger logger = Logger.getLogger(GameRun.class);
	private Bus bus;
	public final static String IRINA = "irina";
	public final static String MIKE = "mike";

	public GameRun(Bus bus) {
		this.bus = bus;
	}

	public void startStandardScenario() {
		bus.register(MIKE, new NamedPlayer(bus, "Mikhail Baytsurov"));
		bus.register(IRINA, new NamedPlayer(bus, "Irina Plaksina"));
		logger.info("players registered, sending message...");
		bus.publish(new MessageImpl.Builder().payload("initial").sender(IRINA).receiver(MIKE).build());
		bus.shutdown();
	}
}
