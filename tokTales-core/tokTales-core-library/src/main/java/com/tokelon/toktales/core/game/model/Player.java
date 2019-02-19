package com.tokelon.toktales.core.game.model;

import javax.inject.Inject;

import com.tokelon.toktales.core.prog.annotation.Scripting;

public class Player implements IPlayer {
	
	
	private IActor playerActor;
	
	@Scripting("uses default ctor")
	public Player() {
		this(new Actor());
	}
	
	@Inject
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
