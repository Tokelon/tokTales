package com.tokelon.toktales.extens.def.core.tale;

import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.core.game.controller.map.MapController;
import com.tokelon.toktales.core.game.logic.map.IMapReceiver;
import com.tokelon.toktales.core.game.logic.map.MapException;
import com.tokelon.toktales.core.game.model.map.IBlockMap;
import com.tokelon.toktales.core.game.model.map.IBlockMapConfig;
import com.tokelon.toktales.core.game.model.map.MapPositionImpl;
import com.tokelon.toktales.core.game.model.map.elements.IMapElement;
import com.tokelon.toktales.core.game.model.map.elements.MapElementImpl;
import com.tokelon.toktales.core.game.model.map.elements.MapElementTypes;
import com.tokelon.toktales.extens.def.core.tale.states.ITaleGamescene;

public class DefaultSceneMapReceiver implements IMapReceiver {
	// TODO: Rename to TaleSceneMapReceiver or if possible replace -> Move into TaleGamescene?

	
	private final ILogging logging;
	private final ITaleGamescene gamescene;
	
	public DefaultSceneMapReceiver(ILogging logging, ITaleGamescene gamescene) {
		this.logging = logging;
		this.gamescene = gamescene;
	}
	
	
	@Override
	public void receiveMap(IBlockMap map) throws MapException {

		IBlockMapConfig config = map.getConfig();
		if(config.getConfigMapSpawnX() >= map.getHorizontalSize() || config.getConfigMapSpawnY() >= map.getVerticalSize()) {
			throw new MapException("Loading map failed: Map config defines invalid spawn point");
		}
		
		
		MapPositionImpl playerStartPos = new MapPositionImpl(config.getConfigMapSpawnX(), config.getConfigMapSpawnY());
		
		// Player element
		IMapElement pe = new MapElementImpl(0, MapElementTypes.TYPE_PLAYER);
		map.getBlockAt(playerStartPos).setElementOnLevel(pe, map.getLevelReference().levelForElement(pe)); // Set player on map
		

		
		MapController mapContr = new MapController(logging, map);
		gamescene.setMap(mapContr);
		
		
		float pworldx = TokTales.getGame().getWorld().getGrid().tileToWorld(playerStartPos.x);	// - 4
		float pworldy = TokTales.getGame().getWorld().getGrid().tileToWorld(playerStartPos.y);	// - 4

		//float cworldx = IWorld.WORLD_GRID.gridIndexCenterToWorld(playerStartPos.x);
		//float cworldy = IWorld.WORLD_GRID.gridIndexCenterToWorld(playerStartPos.y);
		
		//cworldx = IWorld.WORLD_GRID.gridIndexToWorld(4);
		//cworldy = IWorld.WORLD_GRID.gridIndexToWorld(4);

		
		gamescene.getPlayerController().getPlayer().getActor().setWorldCoordinates(pworldx, pworldy);
		gamescene.getCameraController().getCamera().setWorldCoordinates(pworldx, pworldy);
	}
	
}
