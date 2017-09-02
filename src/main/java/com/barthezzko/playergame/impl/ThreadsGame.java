package com.barthezzko.playergame.impl;

import com.barthezzko.playergame.designed.GameRun;
import com.barthezzko.playergame.routers.ThreadsBusImpl;

public class ThreadsGame {

	public static void main(String[] args) {
		new GameRun(new ThreadsBusImpl()).startStandardScenario();
	}
	
}
