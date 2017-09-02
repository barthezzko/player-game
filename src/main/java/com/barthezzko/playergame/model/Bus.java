package com.barthezzko.playergame.model;

import java.util.List;

/**
 * 
 * The major Bus component in the application
 * In addition to Publisher and Router methods adds utility methods for getting state of the system
 * @author barthezzko
 *
 */
public interface Bus extends Publisher, Router {

	default void shutdown() {
	}

	String lastMessageFor(String receiver);

	List<String> getMessagesFor(String receiver);
	
	Listener getListener(String key);
}
