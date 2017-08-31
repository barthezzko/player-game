package com.barthezzko.playergame.impl;

import com.barthezzko.playergame.designed.Game;
import com.barthezzko.playergame.routers.ThreadsBusImpl;

public class ThreadsGame {

	public static void main(String[] args) {
		new Game(new ThreadsBusImpl());
	}
	
}
