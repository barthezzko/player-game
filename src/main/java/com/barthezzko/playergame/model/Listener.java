package com.barthezzko.playergame.model;

/**
 * This interface is used for reacting on the Message instances that are
 * provided by a Bus instance
 * 
 * @author barthezzko
 *
 */
public interface Listener {

	/**
	 * Callback function that defines the behavior of the listener received the
	 * Message
	 * 
	 * @param msg
	 */
	void onMessage(Message msg);

	/**
	 * Due to the requirements we use this method to determine whether the
	 * listener no more accepts Message instances
	 * 
	 * @return
	 */
	boolean isActive();

}
