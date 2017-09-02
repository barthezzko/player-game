package com.barthezzko.playergame.model;

public interface Listener {

	void onMessage(Msg msg);

	boolean active();

}
