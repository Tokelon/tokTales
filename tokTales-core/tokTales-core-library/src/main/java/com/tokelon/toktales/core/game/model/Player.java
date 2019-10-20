package com.tokelon.toktales.core.game.model;

import javax.inject.Inject;

import com.tokelon.toktales.tools.script.annotation.ScriptRelevant;

public class Player implements IPlayer {
	
	
	private IActor playerActor;
	
	@ScriptRelevant("uses default ctor")
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
