package com.tokelon.toktales.core.game.logic.map;

import com.tokelon.toktales.core.game.model.map.IBlockMap;

public interface IMapReceiver {

	public void receiveMap(IBlockMap map) throws MapException;
	
}
