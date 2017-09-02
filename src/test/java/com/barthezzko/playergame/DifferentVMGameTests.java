package com.barthezzko.playergame;

import static com.barthezzko.playergame.impl.GameRun.IRINA;
import static com.barthezzko.playergame.impl.GameRun.MIKE;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.barthezzko.playergame.busimpl.ClientSocketBusImpl;
import com.barthezzko.playergame.gameimpl.SocketGameClient;
import com.barthezzko.playergame.gameimpl.SocketGameServer;
import com.barthezzko.playergame.impl.MessageImpl;
import com.barthezzko.playergame.impl.NamedPlayer;

public class DifferentVMGameTests extends TestBase {

	private Process process;
	private ClientSocketBusImpl bus;

	@Before
	public void before() throws InterruptedException {
		runServerProcess();
		sleep(2000); // waiting till the server process starts, can't use Latch
						// here - that's another process
		bus = new ClientSocketBusImpl();
		bus.register(MIKE, new NamedPlayer(bus, "Mikhail Baytsurov"));
		bus.publish(new MessageImpl.Builder().payload("initial").sender(MIKE).receiver(IRINA).build());
		bus.shutdown();
	}

	@After
	public void after() {
		if (process != null && process.isAlive()) {
			process.destroyForcibly();
		}
	}

	@Test
	public void testClientReceived() throws InterruptedException {
		logger.info("creating client");
		SocketGameClient.main(null);
		sleep(2000); // waiting till the processes are communicating
		assertEquals("initial0011223344556677889", bus.lastMessageFor(MIKE));
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
