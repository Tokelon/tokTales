package com.tokelon.toktales.core.game.controller.map;

import com.tokelon.toktales.core.game.controller.IController;
import com.tokelon.toktales.core.game.logic.IActionScheduler;
import com.tokelon.toktales.core.game.model.map.IBlockMap;
import com.tokelon.toktales.core.game.model.map.IMapPosition;
import com.tokelon.toktales.core.game.model.map.elements.IMapElement;

public interface IMapController extends IController {

	public IActionScheduler getActionScheduler();
	
	
	// Needed?
	//public IEntityContainer getEntityContainer();
	
	public IBlockMap getMap();
	
	
	public boolean positionIsValid(int px, int py);
	
	public boolean positionIsWalkableForPlayer(int px, int py);
	
	//public boolean positionIsValidAndWalkableForPlayer(int px, int py);
	
	
	
	
	public IMapElement elementGet(int px, int py, int lvl);
	public IMapElement elementGet(IMapPosition position, int level);
	
	
	/**
	 * 
	 * @param px
	 * @param py
	 * @param lvl
	 * @param element
	 * @throws DestinationOverrideException If the destination level is not empty.
	 */
	public void elementSyncSet(int px, int py, int lvl, IMapElement element);		//elementAtomicSet
	/**
	 * 
	 * @param position
	 * @param level
	 * @param element
	 * @throws DestinationOverrideException If the destination level is not empty.
	 */
	public void elementSyncSet(IMapPosition position, int level, IMapElement element);
	
	
	public IMapElement elementSyncRemove(int px, int py, int lvl);
	public IMapElement elementSyncRemove(IMapPosition position, int level);
	
	/**
	 * 
	 * @param fromx
	 * @param fromy
	 * @param fromlevel
	 * @param tox
	 * @param toy
	 * @param tolevel
	 * @throws SourceEmptyException If the source level is empty.
	 * @throws DestinationOverrideException If the destination level is not empty.
	 */
	public void elementSyncMove(int fromx, int fromy, int fromlevel, int tox, int toy, int tolevel);
	/**
	 * 
	 * @param fromPosition
	 * @param fromLevel
	 * @param toPosition
	 * @param toLevel
	 * @throws SourceEmptyException If the source level is empty.
	 * @throws DestinationOverrideException If the destination level is not empty.
	 */
	public void elementSyncMove(IMapPosition fromPosition, int fromLevel, IMapPosition toPosition, int toLevel);
	
	//public void elementSyncCopy()...
	
	/*
	public void elementSyncMoveAndOverride(
	*/
	
	
	public interface IMapControllerFactory {
		
		public IMapController create(IBlockMap map);
	}
		
}
