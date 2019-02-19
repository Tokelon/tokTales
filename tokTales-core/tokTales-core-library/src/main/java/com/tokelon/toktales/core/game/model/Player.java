package com.tokelon.toktales.core.game.model;

import javax.inject.Inject;

public class Player implements IPlayer {
	
	
	private IActor playerActor;

	@Inject
	public Player() {
		this(new Actor());
	}
	
	public Player(IActor actor) {
		this.playerActor = actor;
	}
	
	


	@Override
	public IActor getActor() {
		return playerActor;
	}

	@Override
	public void setActor(IActor actor) {
		this.playerActor = actor;
	}
	
}
