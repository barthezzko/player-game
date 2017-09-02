package com.barthezzko.playergame.model;

/**
 * 
 * Central entity of the transition of information between Players
 * 
 * @author barthezzko
 *
 */
public interface Message {

	/**
	 * Piece of information to transfer from Player to Player
	 * @return
	 */
	String getPayload();

	/**
	 * String representation of the Sender Player
	 * @return
	 */
	String getSender();

	/**
	 * String representation of the Receiver Player
	 * @return
	 */
	String getReceiver();

	/**
	 * Returns a new Message with augmented payload field and swapped rceiver/sender fields
	 * @param augment
	 * @return a new Message 
	 */
	Message reverseAndAppend(String augment);
}
