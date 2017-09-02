package com.barthezzko.playergame.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

import org.apache.log4j.Logger;

public class ServerSocketAPI implements SocketAPI {

	private PrintWriter writer;
	private BufferedReader reader;
	private Logger logger = Logger.getLogger(ServerSocketAPI.class);
	private ExecutorService executorService = Executors.newSingleThreadExecutor();

	public ServerSocketAPI(int socketPort, Function<String, Boolean> lineProcessor) {
		ServerSocket serverSocket;
		try {
			logger.info("Starting up the socket in [SERVER] mode [port=" + socketPort + "]");
			serverSocket = new ServerSocket(socketPort);
			logger.info("Waiting for client to join...");
			Socket clientSocket = serverSocket.accept();
			writer = new PrintWriter(clientSocket.getOutputStream(), true);
			reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			executorService.submit(() -> {
				Thread.currentThread().setName("server-socket-listener");
				String inputLine;
				try {
					while ((inputLine = reader.readLine()) != null) {
						if (logger.isDebugEnabled()) {
							logger.debug("SERVER:IN: [" + inputLine + "]");
						}
						if (lineProcessor.apply(inputLine)) {
							return;
						}
					}
				} catch (IOException e) {
					logger.error(e, e);
				}
			});
			executorService.shutdown();
		} catch (IOException e) {
			logger.error(e);
		}
	}

	@Override
	public void send(String line) {
		writer.println(line);
		writer.flush();
	}
}