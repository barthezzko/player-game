package com.barthezzko.playergame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.barthezzko.playergame.busimpl.LoopBackBusImpl;
import com.barthezzko.playergame.busimpl.ThreadsBusImpl;
import com.barthezzko.playergame.impl.GameRun;
import com.barthezzko.playergame.impl.NamedPlayer;
import com.barthezzko.playergame.model.Bus;
import com.barthezzko.playergame.model.Listener;

@RunWith(Parameterized.class)
public class SameVMGameTest extends TestBase {

	private Bus bus;

	public SameVMGameTest(String name, Bus bus) {
		this.bus = bus;
	}

	@Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(
				new Object[][] { { "loopback", new LoopBackBusImpl() }, { "multithreaded", new ThreadsBusImpl() } });
	}

	@Before
	public void before() {
		new GameRun(bus).startStandardScenario();
	}

	@Test
	public void testStandardScenario() {
		if (bus instanceof ThreadsBusImpl) {
			logger.info("Waiting for run to finish threads interaction...");
			while(((ThreadsBusImpl)bus).isWorking()){
				logger.info("yielding...");
				Thread.yield();
			}
		}
		assertEquals("initial00112233445566778899", bus.lastMessageFor("mike"));
		Listener listener = bus.getListener("mike");
		assertEquals(false, listener.isActive());
		assertTrue(listener instanceof NamedPlayer);
		assertEquals(10, ((NamedPlayer) listener).messageCount());
	}

}
