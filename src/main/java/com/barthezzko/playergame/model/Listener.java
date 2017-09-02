package com.barthezzko.playergame.model;

public interface Listener {

	void onMessage(Message msg);

	boolean active();

}
