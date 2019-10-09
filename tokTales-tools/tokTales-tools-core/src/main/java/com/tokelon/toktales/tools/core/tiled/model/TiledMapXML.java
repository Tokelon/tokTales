package com.tokelon.toktales.tools.core.tiled.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.tokelon.toktales.tools.core.tiled.TiledMapFormatException;
import com.tokelon.toktales.tools.core.tiled.XMLValuesTiledMap;
import com.tokelon.toktales.tools.core.tiled.marshal.TiledMapMarshalTools;
import com.tokelon.toktales.tools.core.tiled.marshal.TiledMapUnmarshalException;

@XStreamAlias(XMLValuesTiledMap.NODE_NAME_MAP)
public class TiledMapXML implements ITiledMap {
	
	
	public static final String[] FIELD_ORDER = 
		{
		"mAttrVersion", "mAttrOrientation", "mAttrWidth", "mAttrHeight", "mAttrTileWidth", "mAttrTileHeight", "mAttrBackgroundColor", "mAttrRenderOrder"
		,"mElemProperties", "mElemListTilesets", "mElemListLayers", "mElemListObjectgroups", "mElemListImagelayers"
		
		/* Ommited */
		, "FIELD_ORDER", "mExternalTilesetList", "mFilename", "mMapName", "mOrientation", "mRenderOrder", "mWidth", "mHeight", "mTileWidth", "mTileHeight", "mLevelCount"
		};
	
	
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_VERSION)
	private String mAttrVersion;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_ORIENTATION)
	private String mAttrOrientation;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_WIDTH)
	private String mAttrWidth;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_HEIGHT)
	private String mAttrHeight;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_TILEWIDTH)
	private String mAttrTileWidth;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_TILEHEIGHT)
	private String mAttrTileHeight;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_BACKGROUNDCOLOR)		//required=false	NOT Working
	private String mAttrBackgroundColor;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_RENDERORDER)
	private String mAttrRenderOrder;
	


	@XStreamAlias(XMLValuesTiledMap.NODE_NAME_MAP_PROPERTIES)
	private TiledMapPropertiesImpl mElemProperties = new TiledMapPropertiesImpl();

	
	
	/* Important
	 * If there are multiple tilesets they need to be ordered according to their firstgid value (ascending)
	 * Starts at 1 and there are no gaps
	 */
	//@XStreamAlias(impl=TiledMapTilesetImpl.class)
	@XStreamImplicit(itemFieldName=XMLValuesTiledMap.NODE_NAME_MAP_TILESET)
	private List<TiledMapTilesetImpl> mElemListTilesets = new ArrayList<TiledMapTilesetImpl>();
	
	@XStreamImplicit(itemFieldName=XMLValuesTiledMap.NODE_NAME_MAP_LAYER) //, impl=TiledMapLayerImpl.class)
	private List<TiledMapLayerImpl> mElemListLayers = new ArrayList<TiledMapLayerImpl>();
	
	@XStreamImplicit(itemFieldName=XMLValuesTiledMap.NODE_NAME_MAP_OBJECTGROUP) //, impl=TiledMapObjectgroupImpl.class)
	private List<TiledMapObjectgroupImpl> mElemListObjectgroups = new ArrayList<TiledMapObjectgroupImpl>();

	@XStreamImplicit(itemFieldName=XMLValuesTiledMap.NODE_NAME_MAP_IMAGELAYER)//, impl=TiledMapImagelayerImpl.class)
	private List<TiledMapImagelayerImpl> mElemListImagelayers = new ArrayList<TiledMapImagelayerImpl>();
	
	
	public TiledMapXML() {
		// Default Ctor
	}

	
	@XStreamOmitField private ArrayList<ITiledMapTileset> mExternalTilesetList = new ArrayList<ITiledMapTileset>();
	
	@XStreamOmitField private String mFilename;
	@XStreamOmitField private String mMapName;

	@XStreamOmitField private int mOrientation = -1;
	@XStreamOmitField private int mRenderOrder = -1;
	@XStreamOmitField private int mWidth = -1;
	@XStreamOmitField private int mHeight = -1;
	@XStreamOmitField private int mTileWidth = -1;
	@XStreamOmitField private int mTileHeight = -1;
	
	@XStreamOmitField private int mLevelCount = -1;
	
	
	

	private Object readResolve() throws ObjectStreamException {
		// Not called for base classes!
		
		
		try {
			processUnmarshal();
		} catch (TiledMapFormatException e) {
			throw new TiledMapUnmarshalException(e);
		}
		
		
		//System.out.println("readResolve was called for object: " +this);
		return this;
	}

	private Object writeReplace() throws ObjectStreamException {
		// Not called for base classes?
		
		prepareMarshal();
		
		//System.out.println("writeReplace was called for object: " +this);
		return this;
	}
	
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		// ONLY called if class implements Serializable!
		
		//System.out.println("readObject was called for object: " +this);
	}
	private void writeObject(ObjectOutputStream out) throws IOException {
		// ONLY called if class implements Serializable!
		
		//System.out.println("writeObject was called for object: " +this);
	}
	
	
	
	private void processUnmarshal() throws TiledMapFormatException {
		/*
		if(!J8Arrays.stream(XMLValuesTiledMap.ATTR_VALUE_MAP_VERSIONS).anyMatch(v -> v.equalsIgnoreCase(mAttrVersion))) {
			throw new TiledMapFormatException("Unsupported map version: " +mAttrVersion);
		}
		*/
		
		
		mOrientation = parseOrientation(mAttrOrientation);
		if(mOrientation == -1) {
			throw new TiledMapFormatException("Invalid map orientation value: " +mAttrOrientation);
		}
		
		mRenderOrder = parseRenderOrder(mAttrRenderOrder);
		if(mRenderOrder == -1) {
			throw new TiledMapFormatException("Invalid map render order value: " +mAttrRenderOrder);
		}
		
		
		mWidth = TiledMapMarshalTools.parseIntAttrPositive(mAttrWidth, "map width");
		mHeight = TiledMapMarshalTools.parseIntAttrPositive(mAttrHeight, "map height");
		mTileWidth = TiledMapMarshalTools.parseIntAttrPositive(mAttrTileWidth, "map tile width");
		mTileHeight = TiledMapMarshalTools.parseIntAttrPositive(mAttrTileHeight, "map tile height");
		
		
		
		mLevelCount = calcLevelCount();
		
	}
	
	
	private void prepareMarshal() {
		
		// Don't update attr* values since we assume they have not been changed
		//mAttrWidth = Integer.toString(mWidth);
		
		
		// Set empty list to null so they don't get serialized as empty elements
		TiledMapMarshalTools.nullListIfEmpty(mElemListLayers);
		TiledMapMarshalTools.nullListIfEmpty(mElemListObjectgroups);
		TiledMapMarshalTools.nullListIfEmpty(mElemListImagelayers);
		
		TiledMapMarshalTools.nullListIfEmpty(mElemListTilesets);
		
		TiledMapMarshalTools.nullPropertiesIfEmpty(mElemProperties);
		
		
		// TODO: Need to customize the order in which the level holders are written out
		
	}
	
	
	

	private int parseOrientation(String value) {
		if(XMLValuesTiledMap.ATTR_VALUE_MAP_ORIENTATION_ORTHOGONAL.equals(value)) {
			return ORIENTATION_ORTHOGONAL;
		}
		else if(XMLValuesTiledMap.ATTR_VALUE_MAP_ORIENTATION_ISOMETRIC.equals(value)) {
			return ORIENTATION_ISOMETRIC;
		}
		else if(XMLValuesTiledMap.ATTR_VALUE_MAP_ORIENTATION_ISOMETRIC_STAGGERED.equals(value)) {
			return ORIENTATION_ISOMETRIC_STAGGERED;
		}
		else if(XMLValuesTiledMap.ATTR_VALUE_MAP_ORIENTATION_HEXAGONAL_STAGGERED.equals(value)) {
			return ORIENTATION_HEXAGONAL_STAGGERED;
		}
		else {
			return -1;	// Throw exception?
		}
	}
	
	private int parseRenderOrder(String value) {
		if(XMLValuesTiledMap.ATTR_VALUE_MAP_RENDER_ORDER_RIGHT_DOWN.equals(value)) {
			return RENDER_ORDER_RIGHT_DOWN;
		}
		else if(XMLValuesTiledMap.ATTR_VALUE_MAP_RENDER_ORDER_RIGHT_UP.equals(value)) {
			return RENDER_ORDER_RIGHT_UP;
		}
		else if(XMLValuesTiledMap.ATTR_VALUE_MAP_RENDER_ORDER_LEFT_DOWN.equals(value)) {
			return RENDER_ORDER_LEFT_DOWN;
		}
		else if(XMLValuesTiledMap.ATTR_VALUE_MAP_RENDER_ORDER_LEFT_UP.equals(value)) {
			return RENDER_ORDER_LEFT_UP;
		}
		else {
			return -1;
		}
	}
	
	
	private int calcLevelCount() {
		return mElemListLayers.size() + mElemListObjectgroups.size() + mElemListImagelayers.size();
	}
	
	
	
	
	
	public void setFilename(String name) {
		this.mFilename = name;
		this.mMapName = name.substring(0, name.indexOf('.'));
	}
	
		
	
	@Override
	public ITMXTile getTileForGID(int gid) {
		if(gid <= 0) {
			throw new IllegalArgumentException("gid must be > 0");
		}
		
		
		
		for(int i = 0; i < mElemListTilesets.size(); i++) {
			
			ITiledMapTileset currentTileset = mElemListTilesets.get(i);
			if(currentTileset.isReferenceToExternal()) {
				currentTileset = currentTileset.getExternalTileset();
			}
			

			if((i == mElemListTilesets.size() - 1) && currentTileset.hasTileForGID(gid)) {		// For last tileset in list
				
				return currentTileset.getTileForGID(gid);
			}
			else if(currentTileset.getFirstGID() > gid) {
				
				
				ITiledMapTileset lastTileset = mElemListTilesets.get(i - 1);
				if(lastTileset.isReferenceToExternal()) {
					lastTileset = lastTileset.getExternalTileset();
				}

				return lastTileset.getTileForGID(gid);
			}
		}
		
		return null;
	}

	
	

	@Override
	public String getName() {
		return mMapName;
	}
	
	@Override
	public String getFilename() {
		return mFilename;
	}

	@Override
	public int getWidth() {
		return mWidth;
	}

	@Override
	public int getHeight() {
		return mHeight;
	}
	
	@Override
	public int getTileWidth() {
		return mTileWidth;
	}
	
	@Override
	public int getTileHeight() {
		return mTileHeight;
	}

	@Override
	public int getLevelCount() {
		return mLevelCount;
	}

	@Override
	public int getTypeForLevel(int level) {
		
		for(ITiledMapLayer layer: mElemListLayers) {
			if(layer.getLevel() == level) {
				return LEVEL_TYPE_LAYER;
			}
		}
		
		for(ITiledMapObjectgroup objectgroup: mElemListObjectgroups) {
			if(objectgroup.getLevel() == level) {
				return LEVEL_TYPE_OBJECTGROUP;
			}
		}
		
		for(ITiledMapImagelayer imagelayer: mElemListImagelayers) {
			if(imagelayer.getLevel() == level) {
				return LEVEL_TYPE_IMAGELAYER;
			}
		}
		

		return -1;
	}

	@Override
	public ITiledMapLayer getLayerAt(int level) {

		for(ITiledMapLayer layer: mElemListLayers) {
			if(layer.getLevel() == level) {
				return layer;
			}
		}

		return null;
	}

	@Override
	public ITiledMapObjectgroup getObjectgroupAt(int level) {

		for(ITiledMapObjectgroup objectgroup: mElemListObjectgroups) {
			if(objectgroup.getLevel() == level) {
				return objectgroup;
			}
		}
		
		return null;
	}

	@Override
	public ITiledMapImagelayer getImagelayerAt(int level) {

		for(ITiledMapImagelayer imagelayer: mElemListImagelayers) {
			if(imagelayer.getLevel() == level) {
				return imagelayer;
			}
		}
		
		return null;
	}

	
	@Override
	public List<? extends ITiledMapLayer> getLayerList() {
		return mElemListLayers;
	}

	@Override
	public List<? extends ITiledMapObjectgroup> getObjectgroupList() {
		return mElemListObjectgroups;
	}

	@Override
	public List<? extends ITiledMapImagelayer> getImagelayerList() {
		return mElemListImagelayers;
	}

	@Override
	public List<? extends ITiledMapTileset> getTilesetList() {
		return mElemListTilesets;
	}
	
	@Override
	public List<ITiledMapTileset> getExternalTilesetList() {
		return mExternalTilesetList;
	}

	@Override
	public ITiledMapProperties getProperties() {
		return mElemProperties;
	}
	
}
