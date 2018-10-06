package com.tokelon.toktales.core.game.states;

import javax.inject.Inject;

/** Default implementation for {@link ITypedGameState}, extends from {@link BaseGamestate} using the default scene type.
 */
public class DefaultGamestate extends BaseGamestate<IGameScene> {

	
	@Inject
	public DefaultGamestate() {
		super(IGameScene.class);
	}
	
}
