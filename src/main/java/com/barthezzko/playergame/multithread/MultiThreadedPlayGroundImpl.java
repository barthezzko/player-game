package com.barthezzko.playergame.multithread;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.barthezzko.playergame.model.Message;
import com.barthezzko.playergame.model.PlayGround;

public class MultiThreadedPlayGroundImpl implements PlayGround {

	private static final int PLAYER_NUMBER = 2; // TODO: move to config
	private ExecutorService executorService = Executors
			.newFixedThreadPool(PLAYER_NUMBER);
	private Logger logger = Logger.getLogger(MultiThreadedPlayGroundImpl.class);
	private volatile StringBuilder msgBuilder = new StringBuilder("start");

	public static void main(String[] args) {
		new MultiThreadedPlayGroundImpl().initialize("ole");
	}

	public void initialize(String startMessage) {
		for (int i = 0; i < PLAYER_NUMBER; i++) {
			executorService.submit(new RunnablePlayer(i));
			logger.info("Starting player [" + i + "]");
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		synchronized (msgBuilder) {
			msgBuilder.notify();
		}
	}

	class RunnablePlayer implements Runnable {

		private int invocationCounter;
		private int playerID;

		public RunnablePlayer(int playerID) {
			this.playerID = playerID;
		}

		@Override
		public void run() {
			logger.info("Starting ["+playerID+"]");
			while (invocationCounter < 11) {
				synchronized (msgBuilder) {
					try {
						msgBuilder.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					logger.info("[" + playerID + "] message:" + msgBuilder.toString());
					msgBuilder.append(invocationCounter);
					invocationCounter++;
					logger.info("notifying");
					msgBuilder.notify();
				}
			}
			logger.info("player["+playerID+"] done");
			synchronized (msgBuilder) {
				msgBuilder.notify();
			}
		}
	}

	@Override
	public List<Message> getMessages() {
		return Collections.emptyList();
	}
}
