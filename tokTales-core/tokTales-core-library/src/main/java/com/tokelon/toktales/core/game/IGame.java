package com.tokelon.toktales.core.game;

import com.tokelon.toktales.core.config.IConfigManager;
import com.tokelon.toktales.core.content.IContentManager;
import com.tokelon.toktales.core.editor.IEditorManager;
import com.tokelon.toktales.core.game.control.IGameControl;
import com.tokelon.toktales.core.game.control.ITimeManager;
import com.tokelon.toktales.core.game.state.IGameState;
import com.tokelon.toktales.core.game.state.IGameStateControl;
import com.tokelon.toktales.core.game.world.IWorld;

public interface IGame {

	//public void regMainRenderer(MainRenderer renderer);
	//public IRenderer getMainRenderer();
	
	//public IProgramControl getProgramControl();
	
	
	public IGameState getActiveState();
	
	
	public IGameAdapter getGameAdapter();
	
	public IWorld getWorld();
	
	
	public IGameControl getGameControl();
	
	public IGameStateControl getStateControl();
	
	public ITimeManager getTimeManager();
	
	public IGameScriptManager getScriptManager();
	
	
	public IGameLogicManager getLogicManager();
	
	
	public IConfigManager getConfigManager();
		
	public IEditorManager getEditorManager();
	
	public IContentManager getContentManager();

	
	public IRegistryManager getRegistryManager();

}
