package com.tokelon.toktales.core.game.controller;

import com.tokelon.toktales.tools.core.objects.managers.IListenerHolder;

public interface IControllerManager extends IListenerHolder<IControllerChangeListener> {
	// Add modifiable controller manager?
	
	
	/** Sets the given controller with the given id.
	 * 
	 * @param controllerId
	 * @param controller
	 * @throws NullPointerException If controllerId or controller are null.
	 */
	public void setController(String controllerId, IController controller);

	/** Removes the controller with the given id.
	 * 
	 * @param controllerId
	 * @return The controller previously assigned, or null if there was none.
	 * @throws NullPointerException If controllerId is null.
	 */
	public IController removeController(String controllerId);
	
	/** Returns whether there is a controller for the given id.
	 * 
	 * @param controllerId
	 * @return True if there is a controller, false if not.
	 * @throws NullPointerException If controllerId is null.
	 */
	public boolean hasController(String controllerId);
	
	/** Returns whether there is a compatible to the given type controller for the given id.
	 * 
	 * @param controllerId
	 * @param type
	 * @return True if there is a controller, false if not.
	 * @throws NullPointerException If controllerId is null.
	 */
	public boolean hasControllerAs(String controllerId, Class<?> type);
	
	/** Returns the controller for the given id.
	 * 
	 * @param controllerID
	 * @return The controller for the id, or null if there is none.
	 * @throws NullPointerException If controllerId is null.
	 */
	public IController getController(String controllerID);
	
	/** Returns the controller for the given id, in the given type.
	 * 
	 * @param id
	 * @param type
	 * @return The controller for the id, or null if there is none or it's not compatible to the given type.
	 * @throws NullPointerException If controllerId or type are null.
	 */
	public <T extends IController> T getControllerAs(String id, Class<T> type);
	
}
