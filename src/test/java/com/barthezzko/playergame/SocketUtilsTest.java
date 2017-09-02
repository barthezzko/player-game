package com.barthezzko.playergame;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.barthezzko.playergame.impl.MessageImpl;
import com.barthezzko.playergame.misc.PlayerGameException;
import com.barthezzko.playergame.model.Message;
import com.barthezzko.playergame.sockets.SocketUtils;

public class SocketUtilsTest {

	@Test
	public void test() {
		assertEquals("alice:john:hello", SocketUtils
				.marshall(new MessageImpl.Builder().payload("hello").sender("john").receiver("alice").build()));
	}

	@Test
	public void testToMsg() {
		Message msg = SocketUtils.unmarshall("marie:tony:simple message here");
		assertEquals("simple message here", msg.getPayload());
		assertEquals("tony", msg.getSender());
		assertEquals("marie", msg.getReceiver());
	}

	@Test(expected = PlayerGameException.class)
	public void failMoreThan3() {
		SocketUtils.unmarshall("marie:tony:simple message here:123");
	}
	
	@Test(expected = PlayerGameException.class)
	public void failLessThan3() {
		SocketUtils.unmarshall("marie:tony");
	}
}
