package com.barthezzko.playergame.model;

/**
 * Publisher for the Message instances
 * @author barthezzko
 *
 */
@FunctionalInterface
public interface Publisher {
	
	/**
	 * Publishes given Message instance
	 * @param msg
	 */
	void publish(Message msg);
	
}
