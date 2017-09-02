package com.barthezzko.playergame.model;

public interface Msg {

	String getPayload();
	
	String getSender();
	
	String getReceiver();
	
	void reverseAndAppend(String augment);
}
