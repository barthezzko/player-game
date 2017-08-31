package com.barthezzko.playergame.interfaces;

public interface Bus extends Publisher, Router {

	void shutdown();
}
