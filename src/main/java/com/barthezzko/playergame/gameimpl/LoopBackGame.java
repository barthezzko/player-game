package com.barthezzko.playergame.gameimpl;

import com.barthezzko.playergame.busimpl.LoopBackBusImpl;
import com.barthezzko.playergame.impl.GameRun;

public class LoopBackGame {

	public static void main(String[] args) {
		new GameRun(new LoopBackBusImpl()).startStandardScenario();
	}
	
}
