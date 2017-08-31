package com.barthezzko.playergame.interfaces;

public interface Msg {

	String getPayload();
	
	String getSender();
	
	String getReceiver();
	
	void reverseAndAppend(String augment);
}
