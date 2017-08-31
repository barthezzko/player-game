package com.barthezzko.playergame.designed;

import org.apache.log4j.Logger;

import com.barthezzko.playergame.interfaces.Listener;
import com.barthezzko.playergame.interfaces.Msg;
import com.barthezzko.playergame.interfaces.Publisher;

public class NamedPlayer implements Listener {

	private final String name;
	private final Publisher publisher;
	private int messageCounter;
	private static final int THRESHOLD = 10;
	private Logger logger = Logger.getLogger(NamedPlayer.class);
	
	
	public NamedPlayer(Publisher publisher, String name) {
		this.publisher = publisher;
		this.name = name;
	}

	@Override
	public void onMessage(Msg msg) {
		logger.info("[" + name +"] : " + msg);
		if (++messageCounter < THRESHOLD){
			publisher.publish(MsgImpl.reverse(msg, String.valueOf(messageCounter)));
		} else {
			logger.info("[" + name +"] stopped sending back messages");
		}
		
	}

	public String getName() {
		return name;
	}
}
