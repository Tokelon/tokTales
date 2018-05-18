package com.tokelon.toktales.extens.def.core.game.states.localmap;

import com.tokelon.toktales.core.game.states.IGameSceneControl;
import com.tokelon.toktales.extens.def.core.game.states.consover.IConsoleOverlayGamestate;

public interface ILocalMapGamestate extends IConsoleOverlayGamestate {


	/* We have to make this a separate method instead of overriding getStateRender() with our return type,
	 * because overriding it would mean we'd have to provide an empty implementation. Which would be quite difficult.
	 */
	
	/**
	 * @return The custom state render, or null if there is none.
	 */
	public ILocalMapStateRenderer getStateRenderCustom();
	
	
	
	/* For these we can simply override the return type because we provide a default implementation. */
	
	@Override
	public ILocalMapControlHandler getStateControlHandler();
	
	@Override
	public IGameSceneControl<ILocalMapGamescene> getSceneControl();

	@Override
	public ILocalMapGamescene getActiveScene();

}
