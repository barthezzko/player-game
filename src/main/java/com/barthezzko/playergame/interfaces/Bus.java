package com.barthezzko.playergame.interfaces;

public interface Bus extends Publisher, Router {

	default void shutdown(){}
}
