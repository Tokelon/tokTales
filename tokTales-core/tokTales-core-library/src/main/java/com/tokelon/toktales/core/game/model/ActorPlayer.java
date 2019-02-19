package com.tokelon.toktales.core.game.model;

import javax.inject.Inject;

public class ActorPlayer implements IPlayer {
	
	
	private IActor playerActor;

	@Inject
	public ActorPlayer() {
		this(new Actor());
	}
	
	public ActorPlayer(IActor actor) {
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
