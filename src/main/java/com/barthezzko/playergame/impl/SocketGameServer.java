package com.barthezzko.playergame.impl;

import org.apache.log4j.Logger;

import com.barthezzko.playergame.designed.MsgImpl;
import com.barthezzko.playergame.designed.NamedPlayer;
import com.barthezzko.playergame.interfaces.Bus;
import com.barthezzko.playergame.routers.ServerSocketBusImpl;

public class SocketGameServer {

private static Logger logger = Logger.getLogger(SocketGameClient.class);
	
	public static void main(String[] args) {
		Bus bus = new ServerSocketBusImpl("irina");
		//bus.register();
		
		logger.info("irina registered, sending message...");
		bus.publish(new MsgImpl("initialize", "mike", "irina"));
		bus.shutdown();
	}
}
