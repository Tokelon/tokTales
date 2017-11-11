package com.tokelon.toktales.tools.tiledmap.model;

import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.tokelon.toktales.tools.tiledmap.TiledMapFormatException;
import com.tokelon.toktales.tools.tiledmap.XMLValuesTiledMap;
import com.tokelon.toktales.tools.tiledmap.marshal.TMXTileImageRef;
import com.tokelon.toktales.tools.tiledmap.marshal.TiledMapMarshalTools;
import com.tokelon.toktales.tools.tiledmap.marshal.TiledMapUnmarshalException;

@XStreamAlias(XMLValuesTiledMap.NODE_NAME_MAP_TILESET)
public class TiledMapTilesetImpl implements ITiledMapTileset {

	
	/* This causes the mElemProperties to be writen out empty even when nullified!
	 * 
	 */
	public static final String[] FIELD_ORDER = {
		"mAttrFirstgid", "mAttrSource", "mAttrName", "mAttrTileWidth", "mAttrTileHeight", "mAttrSpacing", "mAttrMargin"
		, "mElemTileOffset", "mElemProperties", "mElemImage", "mElemTerraintypes", "mElemListTiles"
		
		, "FIELD_ORDER", "mFirstGID", "mTileWidth", "mTileHeight", "mSpacing", "mMargin", "mTileCountHorizontal", "mTileCountVertical", "mTileCountAll", "isReferenceToExternal", "isExternal", "mFallbackToExternal", "mExternalTileset", "hasImage", "imageListTiles"
	};
	
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_TILESET_FIRSTGID)
	private String mAttrFirstgid;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_TILESET_SOURCE)
	private String mAttrSource;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_TILESET_NAME)
	private String mAttrName;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_TILESET_TILEWIDTH)
	private String mAttrTileWidth;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_TILESET_TILEHEIGHT)
	private String mAttrTileHeight;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_TILESET_SPACING)
	private String mAttrSpacing;
	
	@XStreamAsAttribute
	@XStreamAlias(XMLValuesTiledMap.ATTR_NAME_MAP_TILESET_MARGIN)
	private String mAttrMargin;
	
	
	
	@XStreamAlias(XMLValuesTiledMap.NODE_NAME_MAP_TILESET_TILEOFFSET)
	private TMXTileoffsetImpl mElemTileOffset;
	
	@XStreamAlias(XMLValuesTiledMap.NODE_NAME_MAP_PROPERTIES)
	private TiledMapPropertiesImpl mElemProperties = new TiledMapPropertiesImpl();

	@XStreamAlias(XMLValuesTiledMap.NODE_NAME_MAP_TILESET_IMAGE)
	private TMXImageImpl mElemImage;
	
	
	@XStreamAlias(XMLValuesTiledMap.NODE_NAME_MAP_TILESET_TERRAINTYPES)
	private TMXTerraintypesImpl mElemTerraintypes;
	
	@XStreamImplicit(itemFieldName=XMLValuesTiledMap.NODE_NAME_MAP_TILESET_TILE)
	private List<TMXTileImpl> mElemListTiles = new ArrayList<TMXTileImpl>();
	
	
	public TiledMapTilesetImpl() {
		// Default Ctor
	}


	@XStreamOmitField private int mFirstGID;
	@XStreamOmitField private int mTileWidth;
	@XStreamOmitField private int mTileHeight;
	@XStreamOmitField private int mSpacing = 0;
	@XStreamOmitField private int mMargin = 0;
	
	@XStreamOmitField private int mTileCountHorizontal;
	@XStreamOmitField private int mTileCountVertical;
	
	@XStreamOmitField private int mTileCountAll;
	
	
	@XStreamOmitField private boolean isReferenceToExternal;
	@XStreamOmitField private boolean isExternal;
	@XStreamOmitField private boolean mFallbackToExternal = true;
	
	@XStreamOmitField private ITiledMapTileset mExternalTileset;
	
	@XStreamOmitField private boolean hasImage;
	@XStreamOmitField private List<ITMXTile> imageListTiles;
	
	
	

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
		
		isReferenceToExternal = mAttrSource != null;
		if(isReferenceToExternal) {
			// Is reference to external tileset and only contains firstgid and source
			mFirstGID = TiledMapMarshalTools.parseIntAttrPositive(mAttrFirstgid, "tileset first gid");
			isExternal = false;
		}
		else {
			isExternal = mAttrFirstgid == null;
			if(isExternal) {
				// Is external tileset
				// Does not have a firstgid
				
				mFirstGID = -1;
			}
			else {
				// Is normal tileset with firstgid
				mFirstGID = TiledMapMarshalTools.parseIntAttrPositive(mAttrFirstgid, "tileset first gid");
			}
			
			
			mTileWidth = TiledMapMarshalTools.parseIntAttrPositive(mAttrTileWidth, "tileset tile width");
			mTileHeight = TiledMapMarshalTools.parseIntAttrPositive(mAttrTileHeight, "tileset tile height");
			
			
			if(mAttrSpacing != null) {
				mSpacing = TiledMapMarshalTools.parseIntAttrNonNegative(mAttrSpacing, "tileset spacing");
			}
			if(mAttrMargin != null) {
				mMargin = TiledMapMarshalTools.parseIntAttrNonNegative(mAttrMargin, "tileset margin");	
			}
			
			
			
			setupTiles();
		}
		
	}
	
	private void setupTiles() {
		
		hasImage = mElemImage != null;
		
		
		if(hasImage) {
			
			/* TODO: Important - Need to incorporate SPACING and MARGIN because right now they are being ignored!
			 * 
			 */
			
			mTileCountHorizontal = mElemImage.getWidth() / mTileWidth;
			mTileCountVertical = mElemImage.getHeight() / mTileHeight;
			
			mTileCountAll = mTileCountHorizontal * mTileCountVertical;
			
			
			imageListTiles = new ArrayList<ITMXTile>(mTileCountAll);
			
			int idIndexCounter = 0;
			for(int iv = 0; iv < mTileCountVertical; iv++) {
				for(int ih = 0; ih < mTileCountHorizontal; ih++) {		// Render-order?
					
					
					/* Iterate over any possibly defined tiles (as xml nodes)
					 * 
					 */
					ITMXTile currTile = null;
					for(TMXTileImpl tile: mElemListTiles) {
						
						if(tile.getID() == idIndexCounter) {	// If its this tile
							currTile = tile;
							break;
						}
					}

					
					if(currTile == null) {

						TMXTileImageRef imgTile = new TMXTileImageRef(idIndexCounter, this);
						currTile = imgTile;
					}
					else {	// If tile was defined as a node
						
						currTile.setParentTileset(this);
					}
					
					// Add to list
					imageListTiles.add(currTile);
					idIndexCounter++;
				}
			}

		}
		else {
			mTileCountAll = mElemListTiles.size();
			
			for(TMXTileImpl tile: mElemListTiles) {
				tile.setParentTileset(this);
			}
		}
		
	}
	
	

	private void prepareMarshal() {

		TiledMapMarshalTools.nullListIfEmpty(mElemListTiles);

		TiledMapMarshalTools.nullPropertiesIfEmpty(mElemProperties);

	}

	
	
	
	
	public void setFirstGIDForExternal(int firstGID) {
		if(!isExternal) {
			throw new IllegalStateException("Cannot set first GID: this is not an external tileset");
		}
		if(firstGID <= 0) {
			throw new IllegalArgumentException("First GID must be > 0");
		}
		
		mFirstGID = firstGID;
	}
	
	
	// TODO: Document the fallback to external tileset
	// Decide whether to use it or not
	
	
	@Override
	public ITMXTile getTileForGID(int gid) {
		if(isReferenceToExternal && mFallbackToExternal) {
			return mExternalTileset.getTileForGID(gid);
		}
		
		if(gid < mFirstGID || gid >= mFirstGID + mTileCountAll) {
			throw new IllegalArgumentException("No tile for this gid");
		}
		
		
		int index = gid - mFirstGID;
		
		ITMXTile res;
		if(hasImage) {
			res = imageListTiles.get(index);
		}
		else {
			res = mElemListTiles.get(index);
		}
		
		return res;
	}
	
	
	@Override
	public boolean hasTileForGID(int gid) {
		if(isReferenceToExternal && mFallbackToExternal) {
			return mExternalTileset.hasTileForGID(gid);
		}
		
		return gid >= mFirstGID && gid < mFirstGID + mTileCountAll;
	}
	
	
	
	@Override
	public boolean isReferenceToExternal() {
		return isReferenceToExternal;
	}
	
	@Override
	public boolean isExternal() {
		return isExternal;
	}
	
	@Override
	public void setExternalTileset(ITiledMapTileset externalTileset) {
		if(!isReferenceToExternal && mFallbackToExternal) {
			throw new IllegalStateException("Cannot set external tileset: this is not a reference tileset");
		}
		if(externalTileset == null) {
			throw new NullPointerException();
		}

		mExternalTileset = externalTileset;
	}
	
	@Override
	public ITiledMapTileset getExternalTileset() {
		return mExternalTileset;
	}
	
	
	@Override
	public int getTileCount() {
		if(isReferenceToExternal && mFallbackToExternal) {
			return mExternalTileset.getTileCount();
		}
		
		return mTileCountAll;
	}
	
	
	@Override
	public int getFirstGID() {
		return mFirstGID;
	}

	@Override
	public String getSource() {
		return mAttrSource;
	}

	@Override
	public String getName() {
		if(isReferenceToExternal && mFallbackToExternal) {
			return mExternalTileset.getName();
		}
		
		return mAttrName;
	}

	@Override
	public int getTileWidth() {
		if(isReferenceToExternal && mFallbackToExternal) {
			return mExternalTileset.getTileWidth();
		}
		
		return mTileWidth;
	}

	@Override
	public int getTileHeight() {
		if(isReferenceToExternal && mFallbackToExternal) {
			return mExternalTileset.getTileHeight();
		}
		
		return mTileHeight;
	}

	@Override
	public int getSpacing() {
		if(isReferenceToExternal && mFallbackToExternal) {
			return mExternalTileset.getSpacing();
		}
		
		return mSpacing;
	}

	@Override
	public int getMargin() {
		if(isReferenceToExternal && mFallbackToExternal) {
			return mExternalTileset.getMargin();
		}
		
		return mMargin;
	}

	
	@Override
	public ITMXTileoffset getTileoffset() {
		if(isReferenceToExternal && mFallbackToExternal) {
			return mExternalTileset.getTileoffset();
		}
		
		return mElemTileOffset;
	}

	@Override
	public ITiledMapProperties getProperties() {
		if(isReferenceToExternal && mFallbackToExternal) {
			return mExternalTileset.getProperties();
		}
		
		return mElemProperties;
	}

	@Override
	public ITMXTerraintypes getTerraintypes() {
		if(isReferenceToExternal && mFallbackToExternal) {
			return mExternalTileset.getTerraintypes();
		}
		
		return mElemTerraintypes;
	}

	@Override
	public ITMXImage getImage() {
		if(isReferenceToExternal && mFallbackToExternal) {
			return mExternalTileset.getImage();
		}
		
		return mElemImage;
	}
	
	@Override
	public List<? extends ITMXTile> getTileList() {
		if(isReferenceToExternal && mFallbackToExternal) {
			return mExternalTileset.getTileList();
		}
		
		return mElemListTiles;
	}

	
	
	@Override
	public int getHorizontalOffsetFor(int tileIndex) {
		if(tileIndex < 0) {
			throw new IllegalArgumentException("Tile index must be >= 0");
		}
		if(isReferenceToExternal && mFallbackToExternal) {
			return mExternalTileset.getHorizontalOffsetFor(tileIndex);
		}
		
		if(!hasImage) {
			return 0;
		}
		
		
		int horizontalIndex = tileIndex % mTileCountHorizontal;
		return mTileWidth * horizontalIndex;
	}
	
	@Override
	public int getVerticalOffsetFor(int tileIndex) {
		if(tileIndex < 0) {
			throw new IllegalArgumentException("Tile index must be >= 0");
		}
		
		if(isReferenceToExternal && mFallbackToExternal) {
			return mExternalTileset.getVerticalOffsetFor(tileIndex);
		}
		
		if(!hasImage) {
			return 0;
		}
		
		int verticalIndex = tileIndex / mTileCountHorizontal;
		return mTileHeight * verticalIndex;
	}
	
		
	
	
	
	@Override
	public int getHorizontalTileCount() {
		if(isReferenceToExternal && mFallbackToExternal) {
			return mExternalTileset.getHorizontalTileCount();
		}
		
		if(!hasImage) {
			return 0;	// Maybe return -1 ?
		}
		
		return mTileCountHorizontal;
	}
	
	@Override
	public int getVerticalTileCount() {
		if(isReferenceToExternal && mFallbackToExternal) {
			return mExternalTileset.getVerticalTileCount();
		}
		
		if(!hasImage) {
			return 0;
		}
		
		return mTileCountVertical;
	}
	
}
