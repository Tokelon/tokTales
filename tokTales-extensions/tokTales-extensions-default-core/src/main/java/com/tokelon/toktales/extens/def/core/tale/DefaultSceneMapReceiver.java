package com.tokelon.toktales.extens.def.core.tale;

import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.game.controller.map.MapController;
import com.tokelon.toktales.core.game.model.map.IBlockMap;
import com.tokelon.toktales.core.game.model.map.IBlockMapConfig;
import com.tokelon.toktales.core.game.model.map.MapPositionImpl;
import com.tokelon.toktales.core.game.model.map.elements.IMapElement;
import com.tokelon.toktales.core.game.model.map.elements.MapElementImpl;
import com.tokelon.toktales.core.game.model.map.elements.MapElementTypes;
import com.tokelon.toktales.core.game.states.BaseGamescene;

public class DefaultSceneMapReceiver implements IMapReceiver {

	private final BaseGamescene gamescene;
	
	public DefaultSceneMapReceiver(BaseGamescene gamescene) {
		this.gamescene = gamescene;
	}
	
	
	@Override
	public void receiveMap(IBlockMap map) throws MapException {

		IBlockMapConfig config = map.getConfig();
		if(config.getConfigMapSpawnX() >= map.getHorizontalSize() || config.getConfigMapSpawnY() >= map.getVerticalSize()) {
			TokTales.getLog().e("MapManager", "Loading map failed: Map config defines invalid spawn point");
			throw new MapException("Loading map failed: Map config defines invalid spawn point");
		}
		
		
		MapPositionImpl playerStartPos = new MapPositionImpl(config.getConfigMapSpawnX(), config.getConfigMapSpawnY());
		
		// Player element
		IMapElement pe = new MapElementImpl(0, MapElementTypes.TYPE_PLAYER);
		map.getBlockAt(playerStartPos).setElementOnLevel(pe, map.getLevelReference().levelForElement(pe)); // Set player on map
		

		
		MapController mapContr = new MapController(map);
		gamescene.setMapController(mapContr);
		
		
		float pworldx = TokTales.getGame().getWorld().getGrid().tileToWorld(playerStartPos.x);	// - 4
		float pworldy = TokTales.getGame().getWorld().getGrid().tileToWorld(playerStartPos.y);	// - 4

		//float cworldx = IWorld.WORLD_GRID.gridIndexCenterToWorld(playerStartPos.x);
		//float cworldy = IWorld.WORLD_GRID.gridIndexCenterToWorld(playerStartPos.y);
		
		//cworldx = IWorld.WORLD_GRID.gridIndexToWorld(4);
		//cworldy = IWorld.WORLD_GRID.gridIndexToWorld(4);

		
		gamescene.getPlayerController().getPlayer().setWorldCoordinates(pworldx, pworldy);
		gamescene.getCameraController().getCamera().setWorldCoordinates(pworldx, pworldy);
	}
	
	
}
