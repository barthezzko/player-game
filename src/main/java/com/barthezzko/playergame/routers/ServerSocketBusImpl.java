package com.barthezzko.playergame.routers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.barthezzko.playergame.designed.NamedPlayer;
import com.barthezzko.playergame.interfaces.Bus;
import com.barthezzko.playergame.interfaces.Listener;
import com.barthezzko.playergame.interfaces.Msg;
import com.barthezzko.playergame.sockets.SocketUtils;

public class ServerSocketBusImpl implements Bus {

	private Logger logger = Logger.getLogger(this.getClass());
	private Map<String, Listener> listenerMap = new HashMap<>();
	private PrintWriter out;

	public ServerSocketBusImpl(String name) {
		listenerMap.put(name, new NamedPlayer(this, name));
		try {
			ServerSocket serverSocket = new ServerSocket(9999);
			Socket clientSocket = serverSocket.accept();
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			String inputLine;
			logger.info("Server initialized");
			while ((inputLine = in.readLine()) != null) {
				logger.info("SERVER:IN:" + inputLine);
				if ("EXIT".equals(inputLine)) {
					break;
				}
				Msg msg = SocketUtils.unmarshall(inputLine);
				Listener listener = listenerMap.get(msg.getReceiver());
				if (listener != null) {
					logger.info("Received from another JVM:" + msg);
					listener.onMessage(msg);
				} else {
					logger.info("No listener for key :" + msg.getReceiver());	
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}

	@Override
	public void register(String msgKey, Listener listener) {
		listenerMap.put(msgKey, listener);
	}

	@Override
	public void publish(Msg msg) { // publish to sockets
		Listener listener = listenerMap.get(msg.getReceiver());
		if (listener != null) {
			logger.info("Send to same JVM:" + msg);
			listener.onMessage(msg);
		} else {
			logger.info("Send to another JVM: " + msg);
			String msgString = SocketUtils.marshall(msg);
			logger.info("Sending to socket:" + msgString);
			out.println(msgString);
		}
	}

	@Override
	public void shutdown() {

	}
}