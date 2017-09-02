package com.barthezzko.playergame.model;

/**
 * Router stands for proper routing functionality, i.e. determines which
 * messages should be passed to this or that Player It is possible that this
 * instance of Router doesn't contain information for the given Message - such
 * messages should be dropped as irrelevant
 * 
 * @author barthezzko
 *
 */
public interface Router {

	/**
	 * Adds a listener associated with the given receiver String representation
	 * 
	 * @param msgKey
	 * @param listener
	 */
	void register(String msgKey, Listener listener);

}
