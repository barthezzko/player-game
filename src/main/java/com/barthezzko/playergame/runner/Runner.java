package com.barthezzko.playergame.runner;

import org.apache.log4j.Logger;

import com.barthezzko.playergame.gameimpl.LoopBackGame;
import com.barthezzko.playergame.gameimpl.SocketGameClient;
import com.barthezzko.playergame.gameimpl.SocketGameServer;
import com.barthezzko.playergame.gameimpl.ThreadsGame;
import com.barthezzko.playergame.misc.PlayerGameException;

public class Runner {

	private static Logger logger = Logger.getLogger(Runner.class);

	public static void main(String[] args) {
		if (args.length < 1 || args.length > 2) {
			help();
			throw new PlayerGameException("There should be 1 or 2 parameters");
		}
		String impl = args[0];
		logger.info("Running [" + impl + "] version");

		switch (impl) {
		case "loopback":
			LoopBackGame.main(null);
			break;
		case "multithreaded":
			ThreadsGame.main(null);
			break;
		case "socket-server":
			SocketGameServer.main(new String[] { args[1] });
			break;
		case "socket-client":
			SocketGameClient.main(new String[] { args[1] });
			break;
		default:
			throw new PlayerGameException("Runner type [" + impl + "] is not defined");
		}
	}

	private static void help() {

	}

}
