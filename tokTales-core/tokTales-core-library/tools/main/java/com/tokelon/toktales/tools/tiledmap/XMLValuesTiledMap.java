package com.tokelon.toktales.tools.tiledmap;

public class XMLValuesTiledMap {
	
	public static final String ATTR_VALUE_MAP_VERSION = "1.0";
	
	public static final String ATTR_VALUE_MAP_ORIENTATION_ORTHOGONAL = "orthogonal";
	public static final String ATTR_VALUE_MAP_ORIENTATION_ISOMETRIC = "isometric";
	public static final String ATTR_VALUE_MAP_ORIENTATION_ISOMETRIC_STAGGERED = "staggered";
	public static final String ATTR_VALUE_MAP_ORIENTATION_HEXAGONAL_STAGGERED = "hexagonal";
	
	public static final String ATTR_VALUE_MAP_RENDER_ORDER_RIGHT_DOWN = "right-down";
	public static final String ATTR_VALUE_MAP_RENDER_ORDER_RIGHT_UP = "right-up";
	public static final String ATTR_VALUE_MAP_RENDER_ORDER_LEFT_DOWN = "left-down";
	public static final String ATTR_VALUE_MAP_RENDER_ORDER_LEFT_UP = "left-up";
	
	public static final String ATTR_VALUE_VISIBLE_SHOWN = "1";
	public static final String ATTR_VALUE_VISIBLE_HIDDEN = "0";
	
	public static final String ATTR_VALUE_ENCODING_BASE64 = "base64";
	public static final String ATTR_VALUE_ENCODING_CSV = "csv";
	
	public static final String ATTR_VALUE_COMPRESSION_GZIP = "gzip";
	public static final String ATTR_VALUE_COMPRESSION_ZLIB = "zlib";
	
	
	
	public static final float ATTR_DEFAULT_MAP_LAYER_OPACITY = (float) 1.0;
	public static final boolean ATTR_DEFAULT_MAP_LAYER_VISIBLE = true;
	
	public static final float ATTR_DEFAULT_MAP_MULT_OBJECTGROUP_OPACITY = (float) 1.0;
	public static final boolean ATTR_DEFAULT_MAP_MULT_OBJECTGROUP_VISIBLE = true;
	
	public static final int ATTR_DEFAULT_MAP_MULT_OBJECTGROUP_OBJECT_WIDTH = 0;
	public static final int ATTR_DEFAULT_MAP_MULT_OBJECTGROUP_OBJECT_HEIGHT = 0;
	public static final int ATTR_DEFAULT_MAP_MULT_OBJECTGROUP_OBJECT_ROTATION = 0;
	public static final boolean ATTR_DEFAULT_MAP_MULT_OBJECTGROUP_OBJECT_VISIBLE = true;
	
	public static final float ATTR_DEFAULT_MAP_IMAGELAYER_OPACITY = (float) 1.0;
	public static final boolean ATTR_DEFAULT_MAP_IMAGELAYER_VISIBLE = true;
	
	
	

	public static final String NODE_NAME_MAP = "map";
	public static final String ATTR_NAME_MAP_VERSION = "version";
	public static final String ATTR_NAME_MAP_ORIENTATION = "orientation";
	public static final String ATTR_NAME_MAP_WIDTH = "width";
	public static final String ATTR_NAME_MAP_HEIGHT = "height";
	public static final String ATTR_NAME_MAP_TILEWIDTH = "tilewidth";
	public static final String ATTR_NAME_MAP_TILEHEIGHT = "tileheight";
	public static final String ATTR_NAME_MAP_BACKGROUNDCOLOR = "backgroundcolor";	// Optional
	public static final String ATTR_NAME_MAP_RENDERORDER = "renderorder";
	
	public static final String NODE_NAME_MAP_PROPERTIES = "properties";
	
	
	public static final String NODE_NAME_MAP_TILESET = "tileset";
	public static final String ATTR_NAME_MAP_TILESET_FIRSTGID = "firstgid";
	public static final String ATTR_NAME_MAP_TILESET_SOURCE = "source";
	public static final String ATTR_NAME_MAP_TILESET_NAME = "name";
	public static final String ATTR_NAME_MAP_TILESET_TILEWIDTH = "tilewidth";
	public static final String ATTR_NAME_MAP_TILESET_TILEHEIGHT = "tileheight";
	public static final String ATTR_NAME_MAP_TILESET_SPACING = "spacing";
	public static final String ATTR_NAME_MAP_TILESET_MARGIN = "margin";
	
	
	public static final String NODE_NAME_MAP_TILESET_TILEOFFSET = "tileoffset";
	public static final String ATTR_NAME_MAP_TILESET_TILEOFFSET_X = "x";
	public static final String ATTR_NAME_MAP_TILESET_TILEOFFSET_Y = "y";
	
	public static final String NODE_NAME_MAP_TILESET_PROPERTIES = "properties";
	
	
	public static final String NODE_NAME_MAP_TILESET_IMAGE = "image";
	public static final String ATTR_NAME_MAP_TILESET_IMAGE_FORMAT = "format";
	public static final String ATTR_NAME_MAP_TILESET_IMAGE_SOURCE = "source";
	public static final String ATTR_NAME_MAP_TILESET_IMAGE_TRANS = "trans";
	public static final String ATTR_NAME_MAP_TILESET_IMAGE_WIDTH = "width";		// optional
	public static final String ATTR_NAME_MAP_TILESET_IMAGE_HEIGHT = "height";	// optional
	
	public static final String NODE_NAME_MAP_TILESET_IMAGE_DATA = "data";
	
	
	public static final String NODE_NAME_MAP_TILESET_TERRAINTYPES = "terraintypes";
	
	
	public static final String NODE_NAME_MAP_TILESET_TERRAINTYPES_TERRAIN = "terrain";
	public static final String ATTR_NAME_MAP_TILESET_TERRAINTYPES_TERRAIN_NAME = "name";
	public static final String ATTR_NAME_MAP_TILESET_TERRAINTYPES_TERRAIN_TILE = "tile";
	
	public static final String NODE_NAME_MAP_TILESET_TERRAINTYPES_TERRAIN_PROPERTIES = "properties";
	
	
	public static final String NODE_NAME_MAP_TILESET_TILE = "tile";
	public static final String ATTR_NAME_MAP_TILESET_TILE_ID = "id";
	public static final String ATTR_NAME_MAP_TILESET_TILE_TERRAIN = "terrain";
	public static final String ATTR_NAME_MAP_TILESET_TILE_PROBABILITY = "probability";	//optional
	
	public static final String NODE_NAME_MAP_TILESET_TILE_PROPERTIES = "properties";
	public static final String NODE_NAME_MAP_TILESET_TILE_IMAGE = "image";
	
	
	public static final String NODE_NAME_MAP_TILESET_TILE_OBJECTGROUP = "objectgroup";
	
	
	public static final String NODE_NAME_MAP_LAYER = "layer";
	public static final String ATTR_NAME_MAP_LAYER_NAME = "name";
	public static final String ATTR_NAME_MAP_LAYER_WIDTH = "width";			// These two are apparently 'deprecated'
	public static final String ATTR_NAME_MAP_LAYER_HEIGHT = "height";		// but required
	public static final String ATTR_NAME_MAP_LAYER_OPACITY = "opacity";
	public static final String ATTR_NAME_MAP_LAYER_VISIBLE = "visible";
	
	public static final String NODE_NAME_MAP_LAYER_PROPERTIES = "properties";	
	public static final String NODE_NAME_MAP_LAYER_DATA = "data";

	
	public static final String NODE_NAME_MAP_OBJECTGROUP = "objectgroup";
	
	
	public static final String NODE_NAME_MAP_IMAGELAYER = "imagelayer";
	public static final String ATTR_NAME_MAP_IMAGELAYER_NAME = "name";
	public static final String ATTR_NAME_MAP_IMAGELAYER_X = "x";
	public static final String ATTR_NAME_MAP_IMAGELAYER_Y = "y";
	public static final String ATTR_NAME_MAP_IMAGELAYER_OPACITY = "opacity";
	public static final String ATTR_NAME_MAP_IMAGELAYER_VISIBLE = "visible";
	
	public static final String NODE_NAME_MAP_IMAGELAYER_PROPERTIES = "properties";
	public static final String NODE_NAME_MAP_IMAGELAYER_IMAGE = "image";
	
	
	public static final String NODE_NAME_MAP_MULT_PROPERTIES_PROPERTY = "property";
	public static final String ATTR_NAME_MAP_MULT_PROPERTIES_PROPERTY_NAME = "name";
	public static final String ATTR_NAME_MAP_MULT_PROPERTIES_PROPERTY_VALUE = "value";
	
	
	public static final String NODE_NAME_MAP_MULT_DATA = "data";
	public static final String ATTR_NAME_MAP_MULT_DATA_ENCODING = "encoding";
	public static final String ATTR_NAME_MAP_MULT_DATA_COMPRESSION = "compression";
	
	public static final String NODE_NAME_MAP_MULT_DATA_TILE = "tile";
	public static final String ATTR_NAME_MAP_MULT_DATA_TILE_GID = "gid";		// Not the same as <tile> in tileset
	
	
	public static final String NODE_NAME_MAP_MULT_OBJECTGROUP = "objectgroup";
	public static final String ATTR_NAME_MAP_MULT_OBJECTGROUP_NAME = "name";
	public static final String ATTR_NAME_MAP_MULT_OBJECTGROUP_COLOR = "color";
	public static final String ATTR_NAME_MAP_MULT_OBJECTGROUP_OPACITY = "opacity";
	public static final String ATTR_NAME_MAP_MULT_OBJECTGROUP_VISIBLE = "visible";
	
	public static final String NODE_NAME_MAP_MULT_OBJECTGROUP_PROPERTIES = "properties";
	
	
	public static final String NODE_NAME_MAP_MULT_OBJECTGROUP_OBJECT = "object";
	public static final String ATTR_NAME_MAP_MULT_OBJECTGROUP_OBJECT_ID = "id";
	public static final String ATTR_NAME_MAP_MULT_OBJECTGROUP_OBJECT_NAME = "name";
	public static final String ATTR_NAME_MAP_MULT_OBJECTGROUP_OBJECT_TYPE = "type";
	public static final String ATTR_NAME_MAP_MULT_OBJECTGROUP_OBJECT_X = "x";
	public static final String ATTR_NAME_MAP_MULT_OBJECTGROUP_OBJECT_Y = "y";
	public static final String ATTR_NAME_MAP_MULT_OBJECTGROUP_OBJECT_WIDTH = "width";
	public static final String ATTR_NAME_MAP_MULT_OBJECTGROUP_OBJECT_HEIGHT = "height";
	public static final String ATTR_NAME_MAP_MULT_OBJECTGROUP_OBJECT_ROTATION = "rotation";
	public static final String ATTR_NAME_MAP_MULT_OBJECTGROUP_OBJECT_GID = "gid";			// optional
	public static final String ATTR_NAME_MAP_MULT_OBJECTGROUP_OBJECT_VISIBLE = "visible";
	
	public static final String NODE_NAME_MAP_MULT_OBJECTGROUP_OBJECT_PROPERTIES = "properties";
	public static final String NODE_NAME_MAP_MULT_OBJECTGROUP_OBJECT_ELLIPSE = "ellipse";
	
	public static final String NODE_NAME_MAP_MULT_OBJECTGROUP_OBJECT_POLYGON = "polygon";
	public static final String ATTR_NAME_MAP_MULT_OBJECTGROUP_OBJECT_POLYGON_POINTS = "points";
	
	public static final String NODE_NAME_MAP_MULT_OBJECTGROUP_OBJECT_POLYLINE = "polyline";
	public static final String ATTR_NAME_MAP_MULT_OBJECTGROUP_OBJECT_POLYLINE_POINTS = "points";
	
	
	
}
