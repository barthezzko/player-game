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

	/**
	 * Callback that should be executed on the game run end 
	 */
	default void shutdown() {
	}

	/**
	 * Returns last message that the Player identified by receiver has received 
	 * @param receiver
	 * @return
	 */
	String lastMessageFor(String receiver);

	/**
	 * Returns a List of the message in the insertion order that the Player identified by receiver has received
	 * @param receiver
	 * @return
	 */
	List<String> getMessagesFor(String receiver);
	
	/**
	 * Returns the Listener identified by String key
	 * @param key
	 * @return
	 */
	Listener getListener(String key);
}
