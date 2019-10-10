package com.tokelon.toktales.tools.tiled.model;

import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.tokelon.toktales.tools.tiled.TiledMapFormatException;
import com.tokelon.toktales.tools.tiled.XMLValuesTiledMap;
import com.tokelon.toktales.tools.tiled.marshal.TiledMapMarshalTools;
import com.tokelon.toktales.tools.tiled.marshal.TiledMapUnmarshalException;


public class TMXTerraintypesImpl implements ITMXTerraintypes {

	@XStreamImplicit(itemFieldName=XMLValuesTiledMap.NODE_NAME_MAP_TILESET_TERRAINTYPES_TERRAIN)
	private List<TMXTerrainImpl> mElemListTerrain = new ArrayList<TMXTerrainImpl>();
	
	
	public TMXTerraintypesImpl() {
		// Default Ctor
	}
	
	
	


	private Object readResolve() throws ObjectStreamException {
		// Not called for base classes!
		
		try {
			processUnmarshal();
		} catch (TiledMapFormatException e) {
			throw new TiledMapUnmarshalException(e);
		}
		
		return this;
	}

	private Object writeReplace() throws ObjectStreamException {
		// Not called for base classes?
		
		prepareMarshal();
		
		return this;
	}
	
	
	private void processUnmarshal() throws TiledMapFormatException {

		// Nothing
	}


	private void prepareMarshal() {
		
		TiledMapMarshalTools.nullListIfEmpty(mElemListTerrain);
	}




	@Override
	public boolean hasTerrain(String terrainName) {
		if(terrainName == null) {
			throw new NullPointerException();
		}
		
		for(ITMXTerrain t: mElemListTerrain) {
			if(terrainName.equals(t.getName())) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public ITMXTerrain getTerrain(String terrainName) {
		if(terrainName == null) {
			throw new NullPointerException();
		}
		
		for(ITMXTerrain t: mElemListTerrain) {
			if(terrainName.equals(t.getName())) {
				return t;
			}
		}
		
		return null;
	}

	
	@Override
	public int getTerrainCount() {
		return mElemListTerrain.size();
	}

	@Override
	public List<? extends ITMXTerrain> getTerrainList() {
		return mElemListTerrain;
	}
	
}
