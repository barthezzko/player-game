package com.barthezzko.playergame.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MessageStorage {

	protected Map<String, List<String>> state = new ConcurrentHashMap<>();

	protected void storeMessage(Msg msg){
		state.putIfAbsent(msg.getReceiver(), new ArrayList<>());
		state.get(msg.getReceiver()).add(msg.getPayload());
	}
	
	public List<String> getMessagesFor(String receiver){
		return state.get(receiver);
	}

	public String lastMessageFor(String receiver){
		if (state.containsKey(receiver)){
			List<String> messages = state.get(receiver);
			return messages.get(messages.size()-1);
		}
		return null;
	}
}
