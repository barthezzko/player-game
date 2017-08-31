package com.barthezzko.playergame.model;

public interface Message {

	public String getPayload();

	public Player getRecepient();

	public Player getSender();

}
