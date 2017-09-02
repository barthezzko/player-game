package com.barthezzko.playergame.impl;

import com.barthezzko.playergame.designed.GameRun;
import com.barthezzko.playergame.routers.LoopBackBusImpl;

public class LoopBackGame {

	public static void main(String[] args) {
		new GameRun(new LoopBackBusImpl()).startStandardScenario();
	}
	
}
