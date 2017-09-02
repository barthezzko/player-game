package com.barthezzko.playergame;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.barthezzko.playergame.gameimpl.SocketGameClient;
import com.barthezzko.playergame.gameimpl.SocketGameServer;

public class DifferentVMGameTests {

	private Logger logger = Logger.getLogger(DifferentVMGameTests.class);
	private Process process;

	@Before
	public void before() throws InterruptedException {
		runServerProcess();
		Thread.sleep(2000);
	}

	@After
	public void after() {
		if (process != null && process.isAlive()) {
			process.destroyForcibly();
		}
	}

	@Test
	public void test() throws InterruptedException {
		logger.info("creating client");
		SocketGameClient.main(null);
		Thread.sleep(2000);
		
	}

	private void runServerProcess() {
		String classpath = Arrays.stream(((URLClassLoader) Thread.currentThread().getContextClassLoader()).getURLs())
				.map(URL::getFile).collect(Collectors.joining(File.pathSeparator));
		try {
			String JAVA_HOME_BIN = System.getProperty("java.home") + "/bin/java";
			process = new ProcessBuilder(JAVA_HOME_BIN, "-classpath", classpath,
					SocketGameServer.class.getCanonicalName()).inheritIO().start();
			logger.info("Started server");
			Executors.newSingleThreadExecutor().submit(() -> {
				logger.info("process stopped with exitCode " + process.exitValue());
			});
		} catch (IOException e) {
			logger.error(e);
		}
	}

}
