package com.barthezzko.playergame;

import static org.junit.Assert.assertEquals;

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
import com.barthezzko.playergame.model.Bus;

@RunWith(Parameterized.class)
public class SameVMGameTests extends TestBase {

	private Bus bus;

	public SameVMGameTests(String name, Bus bus) {
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
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error(e);
			}
		}
		assertEquals("initial00112233445566778899", bus.lastMessageFor("mike"));
	}

}
