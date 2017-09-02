package com.barthezzko.playergame.model;

import java.util.List;

public interface Bus extends Publisher, Router {

	default void shutdown() {
	}

	String lastMessageFor(String receiver);

	List<String> getMessagesFor(String receiver);
}
