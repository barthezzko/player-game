package com.barthezzko.playergame.impl;

import org.apache.log4j.Logger;

import com.barthezzko.playergame.designed.MsgImpl;
import com.barthezzko.playergame.interfaces.Bus;
import com.barthezzko.playergame.routers.ClientSocketBusImpl;

public class SocketGameClient {

	private static Logger logger = Logger.getLogger(SocketGameClient.class);
	
	public static void main(String[] args) {
		Bus bus = new ClientSocketBusImpl("mike");
		
		logger.info("mike registered, sending message...");
		
		bus.publish(new MsgImpl("initial", "irina", "mike"));
		
		bus.shutdown();
	}
	
}
