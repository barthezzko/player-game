package com.barthezzko.playergame.busimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.barthezzko.playergame.model.Bus;
import com.barthezzko.playergame.model.Listener;
import com.barthezzko.playergame.model.Message;

public abstract class BusBase implements Bus {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	private Map<String, List<String>> messagesHistory = new ConcurrentHashMap<>();
	private Map<String, Listener> listenerMap = new HashMap<>();

	protected void addMessage(Message msg) {
		messagesHistory.putIfAbsent(msg.getReceiver(), new ArrayList<>());
		messagesHistory.get(msg.getReceiver()).add(msg.getPayload());
	}

	protected void addListener(String key, Listener listener) {
		listenerMap.put(key, listener);
	}

	public Listener getListener(String name) {
		return listenerMap.get(name);
	}

	public List<String> getMessagesFor(String receiver) {
		return messagesHistory.get(receiver);
	}

	public String lastMessageFor(String receiver) {
		if (messagesHistory.containsKey(receiver)) {
			List<String> messages = messagesHistory.get(receiver);
			return messages.get(messages.size() - 1);
		}
		return null;
	}
}
