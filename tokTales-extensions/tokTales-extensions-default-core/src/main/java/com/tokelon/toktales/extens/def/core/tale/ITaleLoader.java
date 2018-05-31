package com.tokelon.toktales.extens.def.core.tale;

import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.extens.def.core.tale.states.ITaleGamescene;

public interface ITaleLoader {

	
	/** Loads the tale for given path.
	 * 
	 * @param taleAppPath The path to the tale directory.
	 * @return The resulting tale game scene.
	 * @throws TaleException If an error occurs while loading the tale.
	 */
	public ITaleGamescene loadTale(String taleAppPath) throws TaleException;
	
	
	/** Loads the tale for the given path
	 * and adds the resulting gamescene to the given gamestate, with the given scene name. 
	 * 
	 * @param taleAppPath The path to the tale directory.
	 * @param gamesceneName The name with which the resulting scene should be added to the gamestate.
	 * @param gamestate The gamestate to add the resulting scene to.
	 * @throws TaleException If an error occurs while loading the tale.
	 */
	public void loadTaleIntoGamestate(String taleAppPath, String gamesceneName, IGameState gamestate) throws TaleException;
	
	/** Loads the tale for the given path
	 * and creates a new gamestate with the given name, containing the resulting gamescene with the given scene name. 
	 * 
	 * @param taleAppPath The path to the tale directory.
	 * @param gamesceneName The name with which the resulting scene should be added to the gamestate.
	 * @param gamestateName The name with which the resulting state should be added to the game.
	 * @throws TaleException If an error occurs while loading the tale.
	 */
	public void loadTaleIntoGame(String taleAppPath, String gamesceneName, String gamestateName) throws TaleException;
	
}
