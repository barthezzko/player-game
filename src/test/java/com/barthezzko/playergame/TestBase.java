package com.barthezzko.playergame;

import org.apache.log4j.Logger;

public class TestBase {

	protected Logger logger = Logger.getLogger(this.getClass());

	protected void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			logger.error(e);
		}
	}
}
