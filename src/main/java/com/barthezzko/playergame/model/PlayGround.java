package com.barthezzko.playergame.model;

import java.util.List;

public interface PlayGround {
	
	void initialize(String startMessage);

	List<Message> getMessages();
	
}
