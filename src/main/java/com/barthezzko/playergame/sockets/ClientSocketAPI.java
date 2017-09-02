package com.barthezzko.playergame.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import org.apache.log4j.Logger;

public class ClientSocketAPI implements SocketAPI {

	private static final String LOCALHOST = "localhost";
	private static final int TIMEOUT_MS = 10_000;
	private Socket socket;
	private PrintStream outStream;
	private BufferedReader reader;
	private Logger logger = Logger.getLogger(ClientSocketAPI.class);

	public ClientSocketAPI(int socketPort, Consumer<String> lineConsumer) {
		try {
			SocketAddress socketAddress = new InetSocketAddress(InetAddress.getByName(LOCALHOST), socketPort);
			socket = new Socket();
			socket.connect(socketAddress, TIMEOUT_MS);

			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outStream = new PrintStream(socket.getOutputStream(), true);
			ExecutorService executorService = Executors.newSingleThreadExecutor();
			executorService.submit(() -> {
				Thread.currentThread().setName("client-socket-listener");
				String line;
				try {
					while ((line = reader.readLine()) != null) {
						logger.debug("CLIENT:IN:" + line);
						lineConsumer.accept(line);
					}
				} catch (IOException e) {
					logger.warn(e.getMessage());
				}
			});
			executorService.shutdown();
		} catch (IOException e) {
			logger.warn(e, e);
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	@Override
	public void send(String line) {
		outStream.println(line);
		outStream.flush();
	}

}