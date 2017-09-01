package com.barthezzko.playergame.routers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.barthezzko.playergame.interfaces.Bus;
import com.barthezzko.playergame.interfaces.Listener;
import com.barthezzko.playergame.interfaces.Msg;
import com.barthezzko.playergame.sockets.SocketUtils;

public class ClientSocketBusImpl implements Bus {

	private Logger logger = Logger.getLogger(this.getClass());
	private Map<String, Listener> listenerMap = new HashMap<>();
	private PrintStream out;
	private BufferedReader in;
	private Socket socket;

	public ClientSocketBusImpl() {
		try {
			SocketAddress socketAddress = new InetSocketAddress(InetAddress.getByName("localhost"), 9999);
			socket = new Socket();
			socket.connect(socketAddress, 10_000);

			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintStream(socket.getOutputStream(), true);
		} catch (IOException e) {
			logger.warn(e);
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	public void listen() {
		String line;
		try {
			while ((line = in.readLine()) != null) {
				logger.info("CLIENT:IN:" + line);
				Msg msg = SocketUtils.unmarshall(line);
				Listener listener = listenerMap.get(msg.getReceiver());
				if (listener != null) {
					logger.info("Found listener for key:" + msg);
					listener.onMessage(msg);
				} else {
					logger.info("No listener for key:" + msg.getReceiver());
				}
			}
		} catch (IOException e) {
			logger.error(e, e);
		}
	}

	private void send(String msg) {
		logger.info("Sending to socket:" + msg);
		out.println(msg);
		out.flush();
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
			out.println(SocketUtils.marshall(msg));
		}
	}

	@Override
	public void shutdown() {

	}
}