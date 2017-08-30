package com.barthezzko.playergame.model;

import java.util.List;

public interface MessageBus {

	void send(Message message, Player sender, Player recepient);

	List<Message> getMessages();

}
