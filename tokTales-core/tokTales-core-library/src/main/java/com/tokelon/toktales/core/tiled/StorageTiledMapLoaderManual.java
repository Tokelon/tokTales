package com.tokelon.toktales.core.tiled;

import java.io.IOException;
import java.io.InputStream;

import com.tokelon.toktales.core.game.logic.map.IMapLoader;
import com.tokelon.toktales.core.game.logic.map.MapLoaderException;
import com.tokelon.toktales.core.game.model.map.IBlockMap;
import com.tokelon.toktales.core.prog.annotation.Unfinished;
import com.tokelon.toktales.tools.config.ICiniConfig;
import com.tokelon.toktales.tools.tiledmap.MapFormatException;

@Unfinished
public class StorageTiledMapLoaderManual implements IMapLoader {
	
	// TODO: Implement this if needed
	
	
	public void setTarget(InputStream mapFileIn, String mapFileName) {
		
	}
	//public void setTarget(InputStream mapFileIn, String mapFileName, TilesetFileProvider tilesetFileProvider, ICiniConfig mapConfig)
		
	

	@Override
	public void runComplete() throws MapLoaderException, MapFormatException,
			IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void initialize() throws MapFormatException, IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void load() throws MapLoaderException, MapFormatException,
			IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void process() throws MapLoaderException {
		// TODO Auto-generated method stub

	}

	@Override
	public IBlockMap getLoadedMap() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
