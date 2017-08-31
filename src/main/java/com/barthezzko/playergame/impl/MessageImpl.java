package com.barthezzko.playergame.impl;

import com.barthezzko.playergame.model.Message;
import com.barthezzko.playergame.model.Player;

/**
 * @author barthezzko Immutable thread-safe implementation of Message
 *
 */
public final class MessageImpl implements Message {

	private final String payload;
	private final Player sender;
	private final Player recepient;

	public MessageImpl(String payload, Player sender, Player recepient) {
		this.payload = payload;
		this.sender = sender;
		this.recepient = recepient;
	}

	public String getPayload() {
		return payload;
	}

	@Override
	public String toString() {
		return "MessageImpl [" + sender + " -> " + recepient + ", payload=" + payload + "]";
	}

	@Override
	public Player getRecepient() {
		return recepient;
	}

	@Override
	public Player getSender() {
		return sender;
	}
}
