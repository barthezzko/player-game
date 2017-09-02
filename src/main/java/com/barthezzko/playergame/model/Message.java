package com.barthezzko.playergame.model;

public interface Message {

	String getPayload();

	String getSender();

	String getReceiver();

	Message reverseAndAppend(String augment);
}
