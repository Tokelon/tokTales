package com.tokelon.toktales.core.game.controller.map;

import com.tokelon.toktales.core.game.controller.AbstractController;
import com.tokelon.toktales.core.game.logic.ActionScheduler;
import com.tokelon.toktales.core.game.logic.ActionTakerImpl;
import com.tokelon.toktales.core.game.logic.IActionScheduler;
import com.tokelon.toktales.core.game.model.map.IBlockMap;
import com.tokelon.toktales.core.game.model.map.IMapPosition;
import com.tokelon.toktales.core.game.model.map.elements.IMapElement;
import com.tokelon.toktales.core.game.model.map.elements.MapElementTypes;

public class MapController extends AbstractController implements IMapController {

	public static final String TAG = "MapController";
	
	
	// TODO: Implement the action stack so that actions on the map are being saved and executed all at one time
	//private final LinkedBlockingQueue<IMapAction> actionStack = new LinkedBlockingQueue<IMapAction>();
	
	private final ActionScheduler actionScheduler = new ActionScheduler();
	private final ActionTakerImpl actionTaker = new ActionTakerImpl();
	
	
	private final IBlockMap map;
	
	
	public MapController(IBlockMap blockMap) {
		map = blockMap;
	}

	
	
	
	@Override
	public boolean positionIsValid(int px, int py) {
		return (px >= 0 && px < map.getHorizontalSize() && py >= 0 && py < map.getVerticalSize());
	}
	
	
	@Override
	public boolean positionIsWalkableForPlayer(int px, int py) {
		return map.getBlockAt(px, py).isWalkableForPlayer();
		
		/* TODO: right here we don't need synchronization
		 * but we need it where ever this function is called
		 * 
		 * if free -> do move	// this is two operations and needs to be synchronized (via action)
		 */

	}
	
	/*
	@Override
	public boolean positionIsFree(int px, int py) {
		return map.getBlockAt(px, py).isFree();
		
		/* TODO: right here we don't need synchronization
		 * but we need it where ever this function is called
		 * 
		 * if free -> do move	// this is two operations and needs to be synchronized (via action)
		 *//*
	}
	
	@Override
	public boolean positionIsValidAndFree(int px, int py) {
		return positionIsValid(px, py) && positionIsFree(px, py);
	}
	*/

	
	private void playerMove(int fromx, int fromy, int tox, int toy) {

		
		int playerLevel = map.getLevelReference().levelForElementType(MapElementTypes.TYPE_PLAYER);
		
		elementSyncMove(fromx, fromy, playerLevel, tox, toy, playerLevel);
		
		// TODO: Do locking somehow else (action stack)	??


		
		//Log.d(TAG, String.format("Player position changed to (%d, %d)", px, py));
	}
	
	




	@Override
	public IMapElement elementGet(int px, int py, int lvl) {	// TODO: Add source empty exception?
		return map.getBlockAt(px, py).getElementOnLevel(lvl);
	}

	@Override
	public IMapElement elementGet(IMapPosition position, int level) {
		return elementGet(position.x(), position.y(), level);
	}


	
	@Override
	public void elementSyncSet(int px, int py, int lvl, IMapElement element) {

		actionScheduler.requestActionOrError(actionTaker);
		try {
			if(map.getBlockAt(px, py).hasElementOnLevel(lvl)) {
				throw new DestinationOverrideException();
			}
			
			map.getBlockAt(px, py).setElementOnLevel(element, lvl);
		}
		finally {
			actionScheduler.finishAction(actionTaker);
		}
		
	}
	
	@Override
	public void elementSyncSet(IMapPosition position, int level, IMapElement element) {
		elementSyncSet(position.x(), position.y(), level, element);
	}

	

	@Override
	public IMapElement elementSyncRemove(int px, int py, int lvl) {		//TODO: Source check?
		
		actionScheduler.requestActionOrError(actionTaker);
		try {
			return map.getBlockAt(px, py).removeElementOnLevel(lvl);
		}
		finally {
			actionScheduler.finishAction(actionTaker);
		}
	}

	@Override
	public IMapElement elementSyncRemove(IMapPosition position, int level) {
		return elementSyncRemove(position.x(), position.y(), level);
	}

	
	
	@Override
	public void elementSyncMove(int fromx, int fromy, int fromlevel, int tox, int toy, int tolevel) {

		actionScheduler.requestActionOrError(actionTaker);
		try {
			if(!(map.getBlockAt(fromx, fromy).hasElementOnLevel(fromlevel))) {
				throw new SourceEmptyException();
			}
			if(map.getBlockAt(tox, toy).hasElementOnLevel(tolevel)) {
				throw new DestinationOverrideException();
			}
			
			
			IMapElement el = map.getBlockAt(fromx, fromy).removeElementOnLevel(fromlevel);
			
			map.getBlockAt(tox, toy).setElementOnLevel(el, tolevel);
		}
		finally {
			actionScheduler.finishAction(actionTaker);
		}
	}

	@Override
	public void elementSyncMove(IMapPosition fromPosition, int fromLevel, IMapPosition toPosition, int toLevel) {
		elementSyncMove(fromPosition.x(), fromPosition.y(), fromLevel, toPosition.x(), toPosition.y(), toLevel);
	}
	
	
	
	
	@Override
	public IActionScheduler getActionScheduler() {
		return actionScheduler;
	}
	
	@Override
	public IBlockMap getMap() {
		return map;
	}

}
