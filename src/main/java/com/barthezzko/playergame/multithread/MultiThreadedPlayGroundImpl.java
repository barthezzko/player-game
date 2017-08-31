package com.barthezzko.playergame.multithread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.barthezzko.playergame.impl.MessageImpl;
import com.barthezzko.playergame.impl.PlayerImpl;
import com.barthezzko.playergame.model.Message;
import com.barthezzko.playergame.model.MessageBus;
import com.barthezzko.playergame.model.PlayGround;
import com.barthezzko.playergame.model.Player;

public class MultiThreadedPlayGroundImpl implements PlayGround {

	private static final int PLAYER_NUMBER = 2; // TODO: move to config
	private Random rnd = new Random();
	private ExecutorService executorService = Executors.newFixedThreadPool(PLAYER_NUMBER);  
	private List<Player> players = new ArrayList<>();
	private Logger logger = Logger.getLogger(MultiThreadedPlayGroundImpl.class);
	private MessageBus mBus = new MultiThreadedBusImpl();

	public void initialize(String startMessage) {
		int initiatorPlayer = rnd.nextInt(PLAYER_NUMBER);
		for (int i = 0; i < PLAYER_NUMBER; i++) {
			Player player = new PlayerImpl(mBus, i);
			players.add(player);
			executorService.submit(()->{
				mBus.register((message)->{
					logger.info(message);
				});
			});
			logger.info("Starting player [" + i + "]");
		}
		logger.info("Player [" + initiatorPlayer + "] starts");
		Player initiator = players.get(initiatorPlayer);
		Player another = players.get(initiatorPlayer == 1 ? 0 : 1);
		mBus.send(new MessageImpl(startMessage, initiator, another));
		sleep(10);
	}

	private void sleep(int i) {
		try {
			TimeUnit.SECONDS.sleep(i);
		} catch (InterruptedException e) {
			logger.error(e);
		}
	}

	@Override
	public List<Message> getMessages() {
		return mBus.getMessages();
	}

	class PlayerWrapper extends PlayerImpl implements Runnable{

		public PlayerWrapper(MessageBus mBus, int playerID) {
			super(mBus, playerID);
		}

		@Override
		public void run() {
			/*while(finished){
				mBus.send(message);
			}*/
		}
		
	}
	
	
	class MultiThreadedBusImpl implements MessageBus {

		private final List<Message> messagesHistory = new ArrayList<>();
		
		@Override
		public void send(Message message) {
			messagesHistory.add(message);
			logger.info("Sending message " + message + "...");
			message.getRecepient().onMessage(message);
		}

		@Override
		public List<Message> getMessages() {
			return Collections.unmodifiableList(messagesHistory);
		}

		@Override
		public void register(Player recepient) {
			
		}
		
	}
}
