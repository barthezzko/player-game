package com.barthezzko.playergame.busimpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.barthezzko.playergame.model.Listener;
import com.barthezzko.playergame.model.Message;

/**
 * 
 * Multi-threaded implementation of the Bus interface.
 * Could be helpful long-lasting Player's moves
 * 
 * @see {@link SocketBusImpl}
 * 
 * @author barthezzko
 *
 */
public class ThreadsBusImpl extends BusBase {

	private final ExecutorService executorService = Executors.newFixedThreadPool(2);
	private volatile Map<String, Object> lockMap = new ConcurrentHashMap<>();
	private volatile Message currentMsg;

	@Override
	public void publish(Message msg) {
		addMessage(msg);
		if (logger.isDebugEnabled()) {
			logger.debug("PUB:" + msg);
		}
		currentMsg = msg;
		Object lockObject = lockMap.get(msg.getReceiver());
		synchronized (lockObject) {
			lockObject.notify();
		}
	}

	@Override
	public void register(String msgKey, Listener listener) {
		addListener(msgKey, listener);
		Object lockObject = new Object();
		lockMap.put(msgKey, lockObject);
		executorService.submit(() -> {
			Thread.currentThread().setName(msgKey + "-thread");
			while (listener.isActive()) {
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
