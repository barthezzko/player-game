package com.barthezzko.playergame;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.barthezzko.playergame.designed.Game;
import com.barthezzko.playergame.model.Bus;
import com.barthezzko.playergame.routers.LoopBackRouterImpl;
import com.barthezzko.playergame.routers.ThreadsBusImpl;

@RunWith(Parameterized.class)
public class SingleThreadedGameTests {

	private Bus bus;
	private Logger logger = Logger.getLogger(this.getClass());

	public SingleThreadedGameTests(String name, Bus bus) {
		this.bus = bus;
	}

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays
				.asList(new Object[][] { { "mike", new LoopBackRouterImpl() }, { "irina", new ThreadsBusImpl() } });
	}

	@Before
	public void before() {
		new Game(bus);
	}

	@Test
	public void testSimple() {
		logger.info(bus.getMessagesFor("mike"));
		assertEquals("initial00112233445566778899", bus.lastMessageFor("mike"));
	}

}
