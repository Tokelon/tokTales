package com.tokelon.toktales.tools.tiled.marshal;

import com.tokelon.toktales.tools.tiled.ITiledMapLevelHolder;
import com.tokelon.toktales.tools.tiled.marshal.ReflectionConverterWrapper.ConverterListener;

public class TiledMapLevelHolderConverterListener implements ConverterListener {

	
	private int levelCounter = 1;

	
	@Override
	public Object beforeMarshal(Object original) {
		// Nothing
		
		return original;
	}

	@Override
	public Object afterUnmarshal(Object original) {
		
		if(original instanceof ITiledMapLevelHolder) {
			ITiledMapLevelHolder levelHolder = (ITiledMapLevelHolder) original;
			
			levelHolder.setLevel(levelCounter++);
		}
		else {
			// Log
		}
		
		
		return original;
	}

}
