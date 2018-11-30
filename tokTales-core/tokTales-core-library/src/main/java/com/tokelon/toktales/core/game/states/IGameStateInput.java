package com.tokelon.toktales.core.game.states;

import com.tokelon.toktales.core.engine.input.IInputConsumer;


/** Convenience way of gamestate input handing.
 * You can register your platform dependent input callbacks here.
 * <br>
 * This object will usually be implemented as a platform dependent extension. (ex. {@link IDesktopGameStateInput})
 * <br><br>
 * Per default the {@link IGameStateControl} will request this object and check whether it is compatible to the running platform.
 * If it is, it will invoke the standard callback methods provided by the extensions.
 * 
 * 
 */
public interface IGameStateInput extends IInputConsumer {
	
}
