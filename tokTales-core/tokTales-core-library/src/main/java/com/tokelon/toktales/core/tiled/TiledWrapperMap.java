package com.tokelon.toktales.core.tiled;

import java.util.HashMap;
import java.util.Map;

import com.tokelon.toktales.core.content.sprite.ISprite;
import com.tokelon.toktales.core.game.model.map.IBlock;
import com.tokelon.toktales.core.game.model.map.IBlockMap;
import com.tokelon.toktales.core.game.model.map.IBlockMapConfig;
import com.tokelon.toktales.core.game.model.map.ILevelReference;
import com.tokelon.toktales.core.game.model.map.IMapLayer;
import com.tokelon.toktales.core.game.model.map.IMapObject;
import com.tokelon.toktales.core.game.model.map.IMapPosition;
import com.tokelon.toktales.core.game.model.map.MapLayerImpl;
import com.tokelon.toktales.core.game.world.IObjectContainer;
import com.tokelon.toktales.core.game.world.IWorld;
import com.tokelon.toktales.core.game.world.ObjectContainer;
import com.tokelon.toktales.core.resources.IResourceSet;
import com.tokelon.toktales.core.resources.ResourceSet;
import com.tokelon.toktales.tools.core.tiled.model.ITMXObject;
import com.tokelon.toktales.tools.core.tiled.model.ITMXProperty;
import com.tokelon.toktales.tools.core.tiled.model.ITMXTile;
import com.tokelon.toktales.tools.core.tiled.model.ITiledMap;
import com.tokelon.toktales.tools.core.tiled.model.ITiledMapImagelayer;
import com.tokelon.toktales.tools.core.tiled.model.ITiledMapLayer;
import com.tokelon.toktales.tools.core.tiled.model.ITiledMapObjectgroup;
import com.tokelon.toktales.tools.core.tiled.model.ITiledMapProperties;

import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;

public class TiledWrapperMap implements IBlockMap {

	private static final int STARTING_LEVEL = 1;
	private static final int ADDITIONAL_LEVELS = 3;

	private static final String ADDITIONAL_NAME_1 = "top1";
	private static final String ADDITIONAL_NAME_2 = "top2";
	private static final String ADDITIONAL_NAME_3 = "top3";

	
	private final ITiledMap mTiledMap;
	private final ITiledMapConfig mConfig;
	private final IWorld mWorld;
	
	private final int mWidth;
	private final int mHeight;
	
	private final TiledMapBlock[][] blockArray;
	
	private final int finalLevelCount;
	private final TiledMapLevelReference levelReference;
	
	private final MapLayerImpl[] layers;
	
	private final ResourceSet resourceSet = new ResourceSet();
	
	private final Map<String, IMapLayer> layerNameMap;
	private final TObjectIntMap<String> layerLevelMap;
	
	private final ObjectContainer<IMapObject> objectContainer = new ObjectContainer<>();
	
	public TiledWrapperMap(ITiledMap tiledMap, ITiledMapConfig config, IWorld world) {
		if(tiledMap == null || config == null || world == null) {
			throw new NullPointerException();
		}
		
		this.mWidth = tiledMap.getWidth();
		this.mHeight = tiledMap.getHeight();
		
		this.finalLevelCount = tiledMap.getLevelCount() + ADDITIONAL_LEVELS;
		
		
		this.blockArray = new TiledMapBlock[mWidth][mHeight];
		for(int i=0; i < mWidth; i++) {
			for(int k=0; k < mHeight; k++) {
				
				blockArray[i][k] = new TiledMapBlock(finalLevelCount);
			}
		}

		
		this.mTiledMap = tiledMap;
		this.mConfig = config;
		this.mWorld = world;
		this.levelReference = new TiledMapLevelReference(finalLevelCount);	// TODO: Important - Implement level reference correctly!
		this.layers = new MapLayerImpl[finalLevelCount];
		this.layerNameMap = new HashMap<String, IMapLayer>();
		this.layerLevelMap = new TObjectIntHashMap<>();
	}
	
	
	
	public void build() {
		
		float hSizeMult = mWorld.getGridTileSize() / (float) mTiledMap.getTileWidth();
		float vSizeMult = mWorld.getGridTileSize() / (float) mTiledMap.getTileHeight();
	
		TiledTileElementFactory tileElementFactory = new TiledTileElementFactory();

		int levelAmount = mTiledMap.getLevelCount();			// Level count of tiled map!
		for(int ilevel = STARTING_LEVEL; ilevel <= levelAmount; ilevel++) {
			int layerType = mTiledMap.getTypeForLevel(ilevel);
			
			switch (layerType) {
			case ITiledMap.LEVEL_TYPE_LAYER:
				ITiledMapLayer layer = mTiledMap.getLayerAt(ilevel);
				
				
				
				/* TODO: Implement render order
				 * 
				 */
				
				int indexCounter = 0;
				for(int mh = 0; mh < mHeight; mh++) {						// Render-order?
					for(int mw = 0; mw < mWidth; mw++,  indexCounter++) {
						TiledMapBlock tBlock = blockArray[mw][mh];
						
						int tileRef = layer.getGIDForIndex(indexCounter);		// Can return 0 for no tile
						if(tileRef == 0) {
							continue;
						}
						
						ITMXTile tile = mTiledMap.getTileForGID(tileRef);
						if(tile == null) {
							// Will happen if there are broken tilesets
							
							//Prog.getLog().w("TiledWrapperMap", "map returned null for valid GID");
							continue;
						}
						
						
						TiledTileElement tElement = tileElementFactory.makeElement(tile);
						tBlock.setElementOnLevel(tElement, ilevel);
						
						
						if(!layer.isVisible()) {
							tElement.setVisible(false);
						}
					}
				}
				
				
				layers[ilevel-STARTING_LEVEL] = makeMapLayer(layer.getName(), layer.getProperties(), layer.isVisible());	// Add layer type as attribute?
				break;
			case ITiledMap.LEVEL_TYPE_OBJECTGROUP:
				ITiledMapObjectgroup objectgroup = mTiledMap.getObjectgroupAt(ilevel);
				
				
				for(int iobject = 0; iobject < objectgroup.getObjectCount(); iobject++) {
					// TODO: Add to correct block ?
					// calculate block ?
					
					ITMXObject object = objectgroup.getObject(iobject);
					
					ISprite sprite = null;
					if(object.hasGID()) {
						
						ITMXTile tile = mTiledMap.getTileForGID(object.getGID());
						if(tile != null) {
							TiledTileElement tElement = tileElementFactory.makeElement(tile);
							sprite = tElement.getSprite();
						}
					}
					
					TiledMapObject mapObject = TiledMapObject.createWithAdditional(object, ilevel, hSizeMult, vSizeMult, sprite);
					
					String initialName = mapObject.getObjectName();
					String assignedName = initialName;
					int dupCount = 1;
					while(objectContainer.containsObject(assignedName)) {
						assignedName = initialName + "_" + dupCount++;
					}
					objectContainer.addObject(assignedName, mapObject);
					
					// Add to other list?
				}
				
				
				layers[ilevel-STARTING_LEVEL] = makeMapLayer(objectgroup.getName(), objectgroup.getProperties(), objectgroup.isVisible());
				break;
			case ITiledMap.LEVEL_TYPE_IMAGELAYER:
				ITiledMapImagelayer imagelayer = mTiledMap.getImagelayerAt(ilevel);
				

				// TODO: What?
				
				
				layers[ilevel-STARTING_LEVEL] = makeMapLayer(imagelayer.getName(), imagelayer.getProperties(), imagelayer.isVisible());
				break;
			default:
				break;
			}
		}
		
		
		layers[finalLevelCount-STARTING_LEVEL] = new MapLayerImpl(ADDITIONAL_NAME_3);
		layers[finalLevelCount-STARTING_LEVEL-1] = new MapLayerImpl(ADDITIONAL_NAME_2);
		layers[finalLevelCount-STARTING_LEVEL-2] = new MapLayerImpl(ADDITIONAL_NAME_1);
		
		
		for(int i = 0; i < layers.length; i++) {
			IMapLayer layer = layers[i];
			
			layerNameMap.put(layer.getName(), layer);
			layerLevelMap.put(layer.getName(), STARTING_LEVEL + i);
		}
		
	}
	
	
	
	private MapLayerImpl makeMapLayer(String name, ITiledMapProperties properties, boolean visible, int... additionalAttributes) {
		MapLayerImpl mapLayer = new MapLayerImpl(name);
		
		for(ITMXProperty prop: properties.getPropertyList()) {
			String propName = prop.getName();

			
			if(MetaValuesTiledMap.PROPERTY_VALUE_ATTRIBUTED.equals(prop.getValue())) {
				
				if(MetaValuesTiledMap.PROPERTY_ID_LAYER_PLAYER.equals(propName)) {
					// Player
					mapLayer.setAttributed(IMapLayer.ATTRIBUTED_PLAYER);
				}
				else if(MetaValuesTiledMap.PROPERTY_ID_LAYER_FRINGE.equals(propName)) {
					// Fringe
					mapLayer.setAttributed(IMapLayer.ATTRIBUTED_FRINGE);
				}
				else if(MetaValuesTiledMap.PROPERTY_ID_LAYER_PLAYER_FRINGE.equals(propName)) {
					// Player fringe
					mapLayer.setAttributed(IMapLayer.ATTRIBUTED_PLAYER_FRINGE);
				}
				else if(MetaValuesTiledMap.PROPERTY_ID_LAYER_PLAYER_UNFRINGE.equals(propName)) {
					// Player unfringe
					mapLayer.setAttributed(IMapLayer.ATTRIBUTED_PLAYER_UNFRINGE);
				}
			}
		}
		
		
		if(!visible) {
			mapLayer.setAttributed(IMapLayer.ATTRIBUTED_INVISIBLE);
		}
		
		return mapLayer;
	}
	
	
	@Override
	public IObjectContainer<IMapObject> getObjectContainer() {
		return objectContainer;
	}
	
	@Override
	public IBlockMapConfig getConfig() {
		return mConfig;
	}
	
	@Override
	public String getName() {
		return mTiledMap.getName();
	}

	@Override
	public boolean hasFileNameAttached() {
		return mTiledMap.getName() != null;
	}

	@Override
	public String getFileName() {
		return mTiledMap.getName();
	}

	@Override
	public int getVerticalSize() {
		return mHeight;
	}

	@Override
	public int getHorizontalSize() {
		return mWidth;
	}

	@Override
	public IResourceSet getResources() {
		return resourceSet;
	}

	@Override
	public ILevelReference getLevelReference() {
		return levelReference;
	}

	@Override
	public IBlock getBlockAt(int posx, int posy) {
		if(posx < 0 || posy < 0 || posx >= mWidth || posy >= mHeight) {
			throw new IllegalArgumentException(String.format("Illegal block position: (%d,%d)", posx, posy));
		}
		
		return blockArray[posx][posy];
	}

	@Override
	public IBlock getBlockAt(IMapPosition position) {
		return getBlockAt(position.x(), position.y());
	}

	
	@Override
	public IMapLayer getLayerOnLevel(int level) {
		if(level < STARTING_LEVEL || level >= finalLevelCount+STARTING_LEVEL) {
			throw new IllegalArgumentException("Illegal level for layer: " +level);
		}
		
		return layers[level-STARTING_LEVEL];
	}
	
	@Override
	public IMapLayer getLayerForName(String name) {
		return layerNameMap.get(name);
	}
	
	@Override
	public int getLevelForLayer(String name) {
		return layerLevelMap.get(name);
	}
	
}
