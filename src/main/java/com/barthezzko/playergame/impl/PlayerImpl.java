package com.barthezzko.playergame.impl;

import org.apache.log4j.Logger;

import com.barthezzko.playergame.model.Message;
import com.barthezzko.playergame.model.MessageBus;
import com.barthezzko.playergame.model.Player;

public class PlayerImpl implements Player {

	private Logger logger = Logger.getLogger(PlayerImpl.class);
	private int counter = 0;
	private final int playerID;
	private final MessageBus mBus;
	private static int THRESHOLD = 10;

	public PlayerImpl(MessageBus mBus, int playerID) {
		this.mBus = mBus;
		this.playerID = playerID;
	}

	public void onMessage(Message message) {
		if (++counter >= THRESHOLD) {
			logger.info("Game finished by player [" + playerID + "]: reached threshold of " + THRESHOLD + " messages");
			return;
		}
		logger.info("Player [" + playerID + "] received from [" + message.getSender().getPlayerID() + "] : [" + message.getPayload() + "]");
		mBus.send(new MessageImpl(message.getPayload() + counter, this, message.getSender()));
	}

	@Override
	public String toString() {
		return "PlayerImpl [playerID=" + playerID + "]";
	}

	@Override
	public int getPlayerID() {
		return playerID;
	}
}
