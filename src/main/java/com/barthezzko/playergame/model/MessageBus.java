package com.barthezzko.playergame.model;

import java.util.List;

public interface MessageBus {

	void send(Message message);
	
	void register(Player recepient);

	List<Message> getMessages();

}
