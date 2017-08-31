package com.barthezzko.playergame.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import org.apache.log4j.Logger;

public class SocketClient {

	private static PrintStream out;
	private static BufferedReader in;
	private static Logger logger = Logger.getLogger(SocketClient.class);
	private static int messageCount;
	private static Socket socket;

	public static void main(String[] args) {
		try {
			SocketAddress socketAddress = new InetSocketAddress(InetAddress.getByName("localhost"), 9999);
			socket = new Socket();
			socket.connect(socketAddress, 10_000);

			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintStream(socket.getOutputStream(), true);

			String line;
			
			logger.info("sending...");
			send("hi");
			logger.info("sent. waiting for messages");
			while ((line = in.readLine()) != null) {
				logger.info("CLIENT:IN:" + line);
				processLine(line);
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	private static void send(String msg) {
		out.println(msg);
		out.flush();
	}

	private static void processLine(String line) {
		messageCount++;
		if (messageCount >= 10) {
			logger.info("Enough : " + messageCount);
			stopClient();
			return;
		}
		send(line + "B");
	}

	private static void stopClient() {
		try {
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			logger.error(e, e);
		}
	}

}
