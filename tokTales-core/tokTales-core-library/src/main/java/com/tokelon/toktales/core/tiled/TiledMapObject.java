package com.tokelon.toktales.core.tiled;

import com.tokelon.toktales.core.content.sprite.ISprite;
import com.tokelon.toktales.core.game.model.ExtendablePolyline2fImpl;
import com.tokelon.toktales.core.game.model.Rectangle2fImpl;
import com.tokelon.toktales.core.game.model.IPolyline2f.IExtendablePolyline2f;
import com.tokelon.toktales.core.game.model.IRectangle2f.IMutableRectangle2f;
import com.tokelon.toktales.core.game.model.map.IMapObject;
import com.tokelon.toktales.core.game.world.EllipseGeometry;
import com.tokelon.toktales.core.game.world.IWorldGeometry;
import com.tokelon.toktales.core.game.world.PolygonGeometry;
import com.tokelon.toktales.core.game.world.PolylineGeometry;
import com.tokelon.toktales.core.game.world.RectangleGeometry;
import com.tokelon.toktales.tools.tiledmap.model.ITMXEllipse;
import com.tokelon.toktales.tools.tiledmap.model.ITMXObject;
import com.tokelon.toktales.tools.tiledmap.model.ITMXPolygon;
import com.tokelon.toktales.tools.tiledmap.model.ITMXPolyline;
import com.tokelon.toktales.tools.tiledmap.model.ITiledMapProperties;

public class TiledMapObject implements IMapObject {

	protected final String objectName;
	protected final String objectType;
	protected float layerPosition;
	protected float worldX;
	protected float worldY;
	protected float width;
	protected float height;
	protected int rotation;
	protected boolean visible;
	protected int id;
	protected int gid;
	protected IWorldGeometry geometry;
	protected ISprite sprite;
	protected ITiledMapProperties properties;
	
	protected TiledMapObject(String name, String type) {
		if(name == null || type == null) {
			throw new NullPointerException();
		}
		
		this.objectName = name;
		this.objectType = type;
	}
	
		
	@Override
	public IWorldGeometry getGeometry() {
		return geometry;
	}
	
	@Override
	public float getLayerPosition() {
		return layerPosition;
	}
	
	@Override
	public String getObjectName() {
		return objectName;
	}

	@Override
	public String getObjectType() {
		return objectType;
	}

	@Override
	public float getWorldX() {
		return worldX;
	}

	@Override
	public float getWorldY() {
		return worldY;
	}

	@Override
	public float getWidth() {
		return width;
	}

	@Override
	public float getHeight() {
		return height;
	}

	@Override
	public int getRotation() {
		return rotation;
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public boolean hasGID() {
		return gid != -1;
	}

	@Override
	public int getGID() {
		return gid;
	}

	@Override
	public boolean hasSprite() {
		return sprite != null;
	}
	
	@Override
	public ISprite getSprite() {
		return sprite;
	}
	
	// TODO: getter for wrapped properties
	
	
	/* Is the ITMXTile tile needed in here?
	 * If yes, make create with tile
	 * 
	 */
	
	public static TiledMapObject create(ITMXObject object, float layerPosition, float hMult, float vMult) {
		return createWithAdditional(object, layerPosition, hMult, vMult, null);
	}
	
	public static TiledMapObject createWithAdditional(ITMXObject object, float layerPosition, float hMult, float vMult, ISprite sprite) {
		/* So... if a tile object has one of the names (null, "", "0", "1", "2", "3"),
		 * the rendering call for the sprite of that object crashed 'hard' .....HOW???
		 * 
		 */
		String objectName = object.getName() == null ? "" : object.getName();	//randomString()
		String objectType = object.getType() == null ? "" : object.getType();
		TiledMapObject result = new TiledMapObject(objectName, objectType);
		
		result.layerPosition = layerPosition;
		
		result.worldX = object.getX() * hMult;
		result.worldY = object.getY() * vMult;
		result.width = object.getWidth() * hMult;
		result.height = object.getHeight() * vMult;
		
		result.rotation = object.getRotation();
		result.visible = object.isVisible();
		result.id = object.getID();
		result.gid = object.hasGID() ? object.getGID() : -1;
		result.geometry = parseGeometry(object, hMult, vMult);
		result.sprite = sprite;
		result.properties = object.getProperties();
		
		if(object.hasGID()) {
			// Fixes that tile objects have their y position in the bottom left instead of the top left
			result.worldY -= result.height;
		}
		
		return result;
	}
	
	private static String randomString() {
		return Long.toHexString(Double.doubleToLongBits(Math.random()));
	}
	

	private static IWorldGeometry parseGeometry(ITMXObject object, float hMult, float vMult) {
		IWorldGeometry geometry;
		if(object.hasPolygon()) {
			ITMXPolygon tmxPolygon = object.getPolygon();
			
			IExtendablePolyline2f polyline = createPolyline(object.getX(), object.getY(), tmxPolygon.getPointsX(), tmxPolygon.getPointsY(), tmxPolygon.getPointCount(), hMult, vMult);
			geometry = new PolygonGeometry(polyline);
		}
		else if(object.hasPolyline()) {
			ITMXPolyline tmxPolyline = object.getPolyline();
			
			IExtendablePolyline2f polyline = createPolyline(object.getX(), object.getY(), tmxPolyline.getPointsX(), tmxPolyline.getPointsY(), tmxPolyline.getPointCount(), hMult, vMult);
			geometry = new PolylineGeometry(polyline);
		}
		else if(object.hasEllipse()) {
			ITMXEllipse ellipse = object.getEllipse();
			
			IMutableRectangle2f rectangle = createRectangle(object.getX(), object.getY(), object.getWidth(), object.getHeight(), hMult, vMult);
			geometry = new EllipseGeometry(rectangle);
		}
		else {
			IMutableRectangle2f rectangle = createRectangle(object.getX(), object.getY(), object.getWidth(), object.getHeight(), hMult, vMult);
			
			if(object.hasGID()) {	// Fixes the tile objects y in bottom left
				rectangle.moveBy(0, -rectangle.height());
			}
			
			geometry = new RectangleGeometry(rectangle);
		}
		
		return geometry;
	}
	
	private static IMutableRectangle2f createRectangle(int x, int y, int width, int height, float hMult, float vMult) {
		return new Rectangle2fImpl().set(
				x * hMult,
				y * vMult,
				(x + width) * hMult,
				(y + height) * vMult);
	}
	
	private static IExtendablePolyline2f createPolyline(int x, int y, float[] pointsX, float[] pointsY, int pointCount, float hMult, float vMult) {
		ExtendablePolyline2fImpl polyline = new ExtendablePolyline2fImpl();
		for(int i = 0; i < pointCount; i++) {
			polyline.addPointAt(i, (x + pointsX[i]) * hMult, (y + pointsY[i]) * vMult);
		}
		
		return polyline;
	}
	

}
