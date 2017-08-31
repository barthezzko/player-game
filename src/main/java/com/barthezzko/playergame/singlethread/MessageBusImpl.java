package com.barthezzko.playergame.singlethread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.barthezzko.playergame.model.Message;
import com.barthezzko.playergame.model.MessageBus;

public class MessageBusImpl implements MessageBus {

	private final List<Message> messagesHistory = new ArrayList<>(); 
	
	@Override
	public void send(Message message) {
		messagesHistory.add(message);
		message.getRecepient().onMessage(message);
	}

	@Override
	public List<Message> getMessages() {
		return Collections.unmodifiableList(messagesHistory);
	}

}
