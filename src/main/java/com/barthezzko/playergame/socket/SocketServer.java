package com.barthezzko.playergame.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

public class SocketServer {

	private static int portNumber = 9999;
	private static Logger logger = Logger.getLogger(SocketServer.class);

	public static void main(String[] args) {
		try (ServerSocket serverSocket = new ServerSocket(portNumber);
				Socket clientSocket = serverSocket.accept();
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				logger.info("SERVER:IN:" + inputLine);
				if ("EXIT".equals(inputLine)) {
					break;
				}
				out.println(inputLine + "A");
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}
}
