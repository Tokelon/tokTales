package com.tokelon.toktales.core.game.logic.map;

import java.io.IOException;
import java.io.OutputStream;

import com.tokelon.toktales.core.engine.storage.StorageException;
import com.tokelon.toktales.core.game.model.map.IBlockMap;

public interface IMapSaver {

	// TODO: Correctly document this.
	
	
	public void setMap(IBlockMap map);
	
	
	public void setTarget(String mapFileName) throws StorageException;
	
	public void setTarget(OutputStream mapFileOut);
	
	
	public void runComplete() throws IOException;
	
	public void initialize();
	
	public void process();
	
	public void save() throws IOException;
	
	
}
