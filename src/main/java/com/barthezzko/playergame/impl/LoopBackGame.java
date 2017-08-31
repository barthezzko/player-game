package com.barthezzko.playergame.impl;

import com.barthezzko.playergame.designed.Game;
import com.barthezzko.playergame.routers.LoopBackRouterImpl;

public class LoopBackGame {

	public static void main(String[] args) {
		new Game(new LoopBackRouterImpl());
	}
	
}
