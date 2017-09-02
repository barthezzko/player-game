package com.barthezzko.playergame;

import static com.barthezzko.playergame.impl.GameRun.IRINA;
import static com.barthezzko.playergame.impl.GameRun.MIKE;
import static org.junit.Assert.assertEquals;

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

import com.barthezzko.playergame.busimpl.SocketBusImpl;
import com.barthezzko.playergame.gameimpl.SocketGameServer;
import com.barthezzko.playergame.impl.MessageImpl;
import com.barthezzko.playergame.impl.NamedPlayer;
import com.barthezzko.playergame.model.Bus;

public class DifferentVMGameTest extends TestBase {

	private Process process;
	private Bus bus;
	private static final int SOCKET_PORT = 9090; // let's set to other, not the
													// one mentioned in runner
													// class to avoid busy port
													// problem

	@Before
	public void before() throws InterruptedException {
		runServerProcess();
		sleep(2_000); // waiting till the server process starts, can't use Latch
						// here - that's another process
	}

	@After
	public void after() {
		if (process != null && process.isAlive()) {
			process.destroyForcibly();
		}
	}

	@Test
	public void testClientReceived() throws InterruptedException {
		bus = new SocketBusImpl(SOCKET_PORT, SocketBusImpl.Mode.CLIENT);
		NamedPlayer player = new NamedPlayer(bus, "Mikhail Baytsurov");
		logger.info("creating client");
		bus.register(MIKE, player);
		bus.publish(new MessageImpl.Builder().payload("initial").sender(MIKE).receiver(IRINA).build());
		bus.shutdown();
		sleep(2_000); // waiting till the processes are communicating
		assertEquals("initial0011223344556677889", bus.lastMessageFor(MIKE));
		assertEquals(false, player.isActive());
		assertEquals(10, player.messageCount());
	}

	private void runServerProcess() {
		String classpath = Arrays.stream(((URLClassLoader) Thread.currentThread().getContextClassLoader()).getURLs())
				.map(URL::getFile).collect(Collectors.joining(File.pathSeparator));
		try {
			String JAVA_HOME_BIN = System.getProperty("java.home") + "/bin/java";
			process = new ProcessBuilder(JAVA_HOME_BIN, "-classpath", classpath,
					SocketGameServer.class.getCanonicalName(), String.valueOf(SOCKET_PORT)).inheritIO().start();
			logger.info("Started server at port [" + SOCKET_PORT + "]");
			Executors.newSingleThreadExecutor().submit(() -> {
				logger.info("process stopped with exitCode " + process.exitValue());
			});
		} catch (IOException e) {
			logger.error(e);
		}
	}

}
