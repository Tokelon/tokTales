package com.tokelon.toktales.core.game.states;

/** Maps inputs to controls (actions).
 * 
 */
public interface IControlScheme { // TODO: Move to control package?

	/** Indicates that a key is not mapped to any actions.
	 */
	public static final String UNMAPPED = "control_scheme_unmapped";

	/** Indicates that this scheme does not support interpreting.
	 */
	public static final String UNRESOLVED = "control_scheme_unresolved";
	
	
	
	/** Maps the given virtual key to an action.
	 * 
	 * @param vk
	 * @return An action, or {@link #UNMAPPED} if the key could not be mapped.
	 */
	public String map(int vk);
	
	// Add separate mapping for actions?
	//public String mapAction(int action);
	
	
	
	// Way to put more logic into here | let this do the logic instead of the input handler?
	// TODO: Really have this?
	/**
	 * Will return {@link #UNRESOLVED} if this implementation does not support interpreting.
	 * <br>
	 * Will return {@link #UNMAPPED} if there is no mapping for the given key and action.
	 * 
	 * @param vk
	 * @param action
	 * @return
	 */
	public String interpret(int vk, int action);
	
	
	
	public class EmptyControlScheme implements IControlScheme {

		@Override
		public String map(int vk) {
			return UNMAPPED;
		}
		
		@Override
		public String interpret(int vk, int action) {
			return UNRESOLVED;
		}
	}

}
