package com.barthezzko.playergame.busimpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

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
	private volatile Map<String, Object> lockMap = new ConcurrentHashMap<>(); //I use a separate map of locks 
	private AtomicReference<Message> currentMsg = new AtomicReference<Message>(null);
	
	@Override
	public void publish(Message msg) {
		if (logger.isDebugEnabled()) {
			logger.debug("PUB:" + msg);
		}
		Object lockObject = lockMap.get(msg.getReceiver());
		synchronized (lockObject) {
			addMessage(msg);
			currentMsg.set(msg);
			lockObject.notify();
		}
	}

	@Override
	public void register(String msgKey, Listener listener) {
		addListener(msgKey, listener);
		Object lockObject = new Object();
		lockMap.put(msgKey, lockObject);
		CountDownLatch latch = new CountDownLatch(1); //letting the thread start and wait on its monitor object   
		executorService.submit(() -> {
			Thread.currentThread().setName(msgKey + "-thread");
			while (listener.isActive()) {
				try {
					synchronized (lockObject) {
						latch.countDown();
						lockObject.wait();
						listener.onMessage(currentMsg.get());
					}
				} catch (InterruptedException e) {
					logger.error(e);
				}
			}
		});
		try {
			latch.await();
		} catch (InterruptedException e) {
			logger.error(e);
		}
	}

	@Override
	public void shutdown() {
		executorService.shutdown();
	}
	
	public boolean isWorking(){
		return !executorService.isTerminated();
	}
}
