package com.tokelon.toktales.core.game.states;

/** Receives actions and handles the appropriate controls.
 *
 */
public interface IControlHandler { // TODO: Move to control package?
	
	
	
	// maybe add for optimization? passing null instead of creating empty array
	//public boolean handleAction(String target, String action);
	
	public boolean handleAction(String target, String action, Object... params);

	
	
	public class EmptyControlHandler implements IControlHandler {

		@Override
		public boolean handleAction(String target, String action, Object... params) {
			return false;
		}
	}
	
}
