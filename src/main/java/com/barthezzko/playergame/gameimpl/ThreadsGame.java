package com.barthezzko.playergame.gameimpl;

import com.barthezzko.playergame.busimpl.ThreadsBusImpl;
import com.barthezzko.playergame.impl.GameRun;

public class ThreadsGame {

	public static void main(String[] args) {
		new GameRun(new ThreadsBusImpl()).startStandardScenario();
	}
	
}
