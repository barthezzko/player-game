package com.barthezzko.playergame.singlethread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import com.barthezzko.playergame.impl.MessageImpl;
import com.barthezzko.playergame.impl.PlayerImpl;
import com.barthezzko.playergame.model.Message;
import com.barthezzko.playergame.model.MessageBus;
import com.barthezzko.playergame.model.PlayGround;
import com.barthezzko.playergame.model.Player;

public class PlayGroundImpl implements PlayGround {

	private static final int PLAYER_NUMBER = 2; // TODO: move to config
	private Random rnd = new Random();
	private List<Player> players = new ArrayList<>();
	private Logger logger = Logger.getLogger(PlayGroundImpl.class);
	private MessageBus mBus = new MessageBusImpl();

	public void initialize(String startMessage) {
		int initiatorPlayer = rnd.nextInt(PLAYER_NUMBER);
		for (int i = 0; i < PLAYER_NUMBER; i++) {
			players.add(new PlayerImpl(mBus, i));
			logger.info("Starting player [" + i + "]");
		}
		logger.info("Player [" + initiatorPlayer + "] starts");
		Player initiator = players.get(initiatorPlayer);
		Player another = players.get(initiatorPlayer == 1 ? 0 : 1);
		mBus.send(new MessageImpl(startMessage, initiator, another));
	}

	@Override
	public List<Message> getMessages() {
		return mBus.getMessages();
	}

}
