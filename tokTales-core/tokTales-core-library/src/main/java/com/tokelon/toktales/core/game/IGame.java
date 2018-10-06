package com.tokelon.toktales.core.game;

import com.tokelon.toktales.core.config.IConfigManager;
import com.tokelon.toktales.core.content.IContentManager;
import com.tokelon.toktales.core.editor.IEditorManager;
import com.tokelon.toktales.core.game.control.IGameControl;
import com.tokelon.toktales.core.game.control.ITimeManager;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.game.states.IGameStateControl;
import com.tokelon.toktales.core.game.world.IWorld;

public interface IGame {

	
	public IGameAdapter getGameAdapter();
	
	//public void regMainRenderer(MainRenderer renderer);
	//public IRenderer getMainRenderer();
	
	public IGameState getActiveState();
	
	public IWorld getWorld();
	
	
	
	
	public IGameControl getGameControl();
	
	public IGameStateControl getStateControl();
	
	public ITimeManager getTimeManager();
	
	public IGameScriptManager getScriptManager();
	
	
	public IGameLogicManager getLogicManager();
	
	
	
	
	//public IProgramControl getProgramControl();
	
	public IConfigManager getConfigManager();
		
	public IEditorManager getEditorManager();
	
	public IContentManager getContentManager();
	

}
