package com.tokelon.toktales.extensions.core.tale.procedure;

import javax.inject.Inject;

import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.core.game.IGame;
import com.tokelon.toktales.core.game.controller.map.IMapController;
import com.tokelon.toktales.core.game.controller.map.MapController;
import com.tokelon.toktales.core.game.model.map.IBlockMap;
import com.tokelon.toktales.core.game.model.map.IBlockMapConfig;
import com.tokelon.toktales.core.game.model.map.MapPositionImpl;
import com.tokelon.toktales.core.game.model.map.elements.IMapElement;
import com.tokelon.toktales.core.game.model.map.elements.MapElementImpl;
import com.tokelon.toktales.core.game.model.map.elements.MapElementTypes;
import com.tokelon.toktales.extensions.core.tale.TaleException;
import com.tokelon.toktales.extensions.core.tale.state.ITaleGamescene;

public class SetMapTaleProcedure implements ISetMapTaleProcedure {


	private final ILogging logging;
	
	@Inject
	public SetMapTaleProcedure(ILogging logging) {
		this.logging = logging;
	}
	

	@Override
	public IMapController run(ITaleGamescene taleScene, IGame game, IBlockMap map) throws TaleException {
		IBlockMapConfig config = map.getConfig();
		if(config.getConfigMapSpawnX() >= map.getHorizontalSize() || config.getConfigMapSpawnY() >= map.getVerticalSize()) {
			throw new TaleException("Loading map failed: Map config defines invalid spawn point");
		}
		
		
		MapPositionImpl playerStartPos = new MapPositionImpl(config.getConfigMapSpawnX(), config.getConfigMapSpawnY());
		
		// Player element
		IMapElement pe = new MapElementImpl(0, MapElementTypes.TYPE_PLAYER);
		map.getBlockAt(playerStartPos).setElementOnLevel(pe, map.getLevelReference().levelForElement(pe)); // Set player on map
		

		
		MapController mapContr = new MapController(logging, map);
		
		float pworldx = game.getWorld().getGrid().tileToWorld(playerStartPos.x); // - 4
		float pworldy = game.getWorld().getGrid().tileToWorld(playerStartPos.y); // - 4

		//float cworldx = IWorld.WORLD_GRID.gridIndexCenterToWorld(playerStartPos.x);
		//float cworldy = IWorld.WORLD_GRID.gridIndexCenterToWorld(playerStartPos.y);
		
		//cworldx = IWorld.WORLD_GRID.gridIndexToWorld(4);
		//cworldy = IWorld.WORLD_GRID.gridIndexToWorld(4);

		
		taleScene.getPlayerController().getPlayer().getActor().setWorldCoordinates(pworldx, pworldy);
		taleScene.getCameraController().getCamera().setWorldCoordinates(pworldx, pworldy);
		
		
		return mapContr;
	}
	
}
