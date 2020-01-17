package com.tokelon.toktales.extens.def.core.game.states.localmap;

import com.tokelon.toktales.core.game.states.ITypedGameState;
import com.tokelon.toktales.extens.def.core.game.states.integration.IConsoleIntegration;

public interface ILocalMapGamestate extends ITypedGameState<ILocalMapGamescene> {

	/* On overriding state dependencies with custom ones:
	 * - Getters
	 * 1. You can either override the existing getter return type, or create a custom getter.
	 * 2. If you override the existing getter, you have to provide an empty implementation.
	 * 3. If you create a custom getter, you can return null as long as the dependency has not been set. 
	 * 4. The normal getter must never return null, but instead an empty implementation, as long as a custom dependency has not been set.
	 * - Setters
	 * 5. You have to create a custom setter, which must call the normal setter.
	 * 6. The normal setter must work with the base implementation, it cannot throw any unsupported exceptions.
	 * 
	 */


	/* For this we can simply override the return type because we provide a default implementation. */
	@Override
	public ILocalMapControlHandler getStateControlHandler();

	
	/* Here we have to make this a separate method instead of overriding getStateRender() with our return type,
	 * because overriding it would mean we'd have to provide an empty implementation. Which would be quite difficult.
	 */
	/**
	 * @return The custom state render, or null if there is none.
	 */
	public ILocalMapStateRenderer getStateRenderCustom();
	
	
	/** Returns the console integration for this state.
	 * 
	 * @return A console integration.
	 */
	public IConsoleIntegration getIntegrationConsole();
	
}
