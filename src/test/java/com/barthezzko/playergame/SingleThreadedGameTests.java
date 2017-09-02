package com.barthezzko.playergame;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.barthezzko.playergame.designed.GameRun;
import com.barthezzko.playergame.model.Bus;
import com.barthezzko.playergame.routers.LoopBackBusImpl;
import com.barthezzko.playergame.routers.ThreadsBusImpl;

@RunWith(Parameterized.class)
public class SingleThreadedGameTests {

	private Bus bus;
	private Logger logger = Logger.getLogger(this.getClass());
	
	public SingleThreadedGameTests(String name, Bus bus) {
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
	public void testSimple() {
		if (bus instanceof ThreadsBusImpl){
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
