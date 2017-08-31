package com.barthezzko.playergame.designed;

import org.apache.log4j.Logger;

public class DesignedGame {

	private static Logger logger = Logger.getLogger(DesignedGame.class);
	
	public static void main(String[] args) {
		LoopBackRouterImpl router = new LoopBackRouterImpl();
		router.register("mike", new NamedPlayer(router, "Mikhail Baytsurov"));
		router.register("irina", new NamedPlayer(router, "Irina Plaksina"));
		
		logger.info("players registered");
	
		router.publish(MsgImpl.of("initial", "mike", "irina"));
	}
}
