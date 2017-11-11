package com.tokelon.toktales.core.game.controller;


public interface IControllerManager {

	/**
	 * 
	 * @param controllerID
	 * @param controller
	 * @throws NullPointerException If controllerID is null.
	 */
	public void setController(String controllerID, IController controller);
	public IController getController(String controllerID);
	

	public <T extends IController> T getControllerAs(String id, Class<T> type);
	
	/*
	public boolean hasController(String name);
	public IController getController(String name);
	public void setController(String name, IController controller);
	public void removeController(String name);
	*/
	
	
	
	//public IMapController getMapController();
	//public void setMapController(IMapController controller);
	
	//public IPlayerController getPlayerController();
	//public void setPlayerController(IPlayerController controller);
	
	//public ICameraController getCameraController();
	//public void setCameraController(ICameraController controller);

	
	//public void setChangeListener(IControllerChangeListener listener);
	//public void removeChangeListener();
	
	public interface IControllerChangeListener {
		
		public void onControllerSet(IControllerManager manager, String controllerID);
	}
	
	
}
