package com.barthezzko.playergame.busimpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.barthezzko.playergame.model.Bus;
import com.barthezzko.playergame.model.Listener;
import com.barthezzko.playergame.model.MessageStorage;
import com.barthezzko.playergame.model.Message;

public class ThreadsBusImpl extends MessageStorage implements Bus {

	private final ExecutorService executorService = Executors.newFixedThreadPool(10);
	private volatile Map<String, Object> lockMap = new ConcurrentHashMap<>();
	private volatile Message currentMsg;
	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	public void publish(Message msg) {
		storeMessage(msg);
		logger.debug("PUB:" + msg);
		currentMsg = msg;
		Object lockObject = lockMap.get(msg.getReceiver());
		synchronized (lockObject) {
			lockObject.notify();
		}
	}

	@Override
	public void register(String msgKey, Listener listener) {
		Object lockObject = new Object();
		lockMap.put(msgKey, lockObject);
		executorService.submit(() -> {
			Thread.currentThread().setName(msgKey+"-thread");
			while (listener.active()) {
				try {
					synchronized (lockObject) {
						lockObject.wait();
						listener.onMessage(currentMsg);
					}
				} catch (InterruptedException e) {
					logger.error(e);
				}
			}
		});
	}

	@Override
	public void shutdown() {
		executorService.shutdown();
	}

}
