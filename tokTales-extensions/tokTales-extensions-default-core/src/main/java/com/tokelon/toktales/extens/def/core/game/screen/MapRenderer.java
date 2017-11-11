package com.tokelon.toktales.extens.def.core.game.screen;

import org.joml.Matrix4f;

import com.tokelon.toktales.core.content.sprite.ISprite;
import com.tokelon.toktales.core.content.sprite.ISpriteAsset;
import com.tokelon.toktales.core.content.sprite.ISpriteManager;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.game.controller.map.IMapController;
import com.tokelon.toktales.core.game.graphic.GameGraphicTypes;
import com.tokelon.toktales.core.game.graphic.ISpriteGraphic;
import com.tokelon.toktales.core.game.graphic.IBaseGraphic.IGraphicType;
import com.tokelon.toktales.core.game.model.Point2fImpl;
import com.tokelon.toktales.core.game.model.Rectangle2fImpl;
import com.tokelon.toktales.core.game.model.Rectangle2iImpl;
import com.tokelon.toktales.core.game.model.map.IBlock;
import com.tokelon.toktales.core.game.model.map.IBlockMap;
import com.tokelon.toktales.core.game.model.map.IMapLayer;
import com.tokelon.toktales.core.game.model.map.MapPositionImpl;
import com.tokelon.toktales.core.game.model.map.elements.IMapElement;
import com.tokelon.toktales.core.game.model.map.elements.MapElementTypes;
import com.tokelon.toktales.core.game.model.map.elements.IMapElement.IElementType;
import com.tokelon.toktales.core.game.model.map.predef.IGroundElement;
import com.tokelon.toktales.core.game.screen.view.DefaultViewGridTransformer;
import com.tokelon.toktales.core.game.screen.view.IViewGridTransformer;
import com.tokelon.toktales.core.game.screen.view.IViewTransformer;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.render.IKeyedTextureManager;
import com.tokelon.toktales.core.render.IRenderDriver;
import com.tokelon.toktales.core.render.IRenderTexture;
import com.tokelon.toktales.core.render.RenderException;
import com.tokelon.toktales.core.render.model.ISpriteModel;
import com.tokelon.toktales.core.render.model.SpriteModel;
import com.tokelon.toktales.core.util.INamedOptions;
import com.tokelon.toktales.core.util.NamedOptionsImpl;
import com.tokelon.toktales.core.values.RenderDriverOptions;
import com.tokelon.toktales.tools.tiledmap.ITiledTileElement;
import com.tokelon.toktales.tools.tiledmap.TiledMapElementTypes;

public class MapRenderer implements IMapRenderer {

	public static final String TAG = "MapRenderer";
	
	
	private final IGameState mGamestate;
	private final ISpriteManager mSpriteManager;
	
	
	private final Matrix4f matrixProjection = new Matrix4f();
	private final Matrix4f matrixView = new Matrix4f();
	private final Matrix4f matrixProjectionAndView = new Matrix4f();
	
	private final Rectangle2fImpl cameraBounds = new Rectangle2fImpl();
	private final Rectangle2iImpl cameraGridBounds = new Rectangle2iImpl();
	
	private final DrawingMeta dmeta = new DrawingMeta();

	
	private IKeyedTextureManager<ISprite> mTextureManager;
	
	private IRenderDriver spriteDriver;
	private final SpriteModel spriteModel = new SpriteModel();
	
	private final NamedOptionsImpl drawingOptions = new NamedOptionsImpl();
	
	
	private IViewTransformer mViewTransformer;
	private IViewGridTransformer mGridTransformer;

	
	public MapRenderer(IGameState gamestate) {
		this.mGamestate = gamestate;
		this.mSpriteManager = gamestate.getGame().getContentManager().getSpriteManager();

		
		spriteModel.setInvertYAxis(true);

	}
	
	
	@Override
	public void contextCreated() {
		
		spriteDriver = mGamestate.getEngine().getRenderService().getRenderAccess().requestDriver(ISpriteModel.class.getName());
		if(spriteDriver == null) {
			throw new RenderException("No render driver found for: " +ISpriteModel.class.getName());
		}
		
		spriteDriver.create();
		
		
		
		mTextureManager = mGamestate.getEngine().getRenderService().getRenderAccess().requestKeyedTextureManager(ISprite.class);
		if(mTextureManager == null) {
			throw new RenderException("No texture manager found");
		}

		
		spriteModel.setTextureManager(mTextureManager);
		
	}

	@Override
	public void contextChanged(IViewTransformer viewTransformer, Matrix4f projectionMatrix) {
		this.mViewTransformer = viewTransformer;
		this.mGridTransformer = new DefaultViewGridTransformer(mGamestate.getGame().getWorld().getGrid(), viewTransformer);
		
		matrixProjection.set(projectionMatrix);
		
		matrixView.lookAt(
				0.0f, 0.0f, 0.0f,
				0.0f, 0.0f, -1.0f,
				0.0f, 1.0f, 0.0f);
		
		matrixProjection.mul(matrixView, matrixProjectionAndView);
	}
	
	@Override
	public void contextDestroyed() {
		if(spriteDriver != null) {
			spriteDriver.destroy();
			spriteDriver = null;
		}
		
		if(mTextureManager != null) {
			mTextureManager.clear();
			mTextureManager = null;
		}
		
		spriteModel.setTextureManager(null);
		
		mGridTransformer = null;
		mViewTransformer = null;
	}
	
	

	@Override
	public void prepare(long currentTimeMillis) {
		if(mGridTransformer == null) {
			assert false : "Cannot prepare without view";
			return;
		}
		
		dmeta.tilePixeSize = mGridTransformer.getScreenTileSize();
		
		
		float cameraShiftX = mViewTransformer.cameraToViewportX(mGridTransformer.getCameraShiftX());
		float cameraShiftY = mViewTransformer.cameraToViewportY(mGridTransformer.getCameraShiftY());
		
		matrixView.identity();
		matrixView.setLookAt(
				// Eye
				(float)cameraShiftX, (float)cameraShiftY, 0.0f,
				// Center
				(float)cameraShiftX, (float)cameraShiftY, -1.0f,
				0.0f, 1.0f, 0.0f);	// Up
		
		matrixProjection.mul(matrixView, matrixProjectionAndView);
		
	}

	@Override
	public void drawLayer(INamedOptions options, String layerName) {
		IMapController mapController = mGamestate.getActiveScene().getMapController();
		
		// TODO: MapController should never be NULL !!
		if(mapController == null) {
			TokTales.getLog().e(TAG, "Draw was called but no Map is available");
			return;
		}
		
		drawMapLayer(mapController, layerName);
	}
	
	@Override
	public void drawFull(INamedOptions options) {
		assert false : "Not supported";

		/* This does not work because depth testing does not play along with transparency
		 * 
		IMapController mapController = mGamestate.getMapController();
		if(mapController == null) {
			TokTales.getLog().e(TAG, "Draw was called but no Map is available");
			return;
		}
		
		ILevelReference lref = mapController.getMap().getLevelReference();
		
		//for(int level = lref.getLowestLevel(); level <= lref.getHighestLevel(); level++) {			
		for(int level = lref.getHighestLevel(); level >= lref.getLowestLevel(); level--) {

			drawLayer(options, level, lref.getHighestLevel() - level);
		}
		*/
	}
	
	
	@Override
	public void drawMapLayer(IMapController mapController, String layerName) {
		if(mGridTransformer == null) {
			assert false : "Cannot draw without view";
			return;
		}
		
		
		IBlockMap map = mapController.getMap();
		
		IMapLayer mapLayer = map.getLayerForName(layerName);
		if(mapLayer == null) {
			// If no such layer in map, ignore
			return;
		}
		if(mapLayer.isAttributed(IMapLayer.ATTRIBUTED_INVISIBLE)) {
			// Skip invisible layers
			return;
		}
		
		int mapLevel = map.getLevelForLayer(layerName);
		
		
		// Enable sprite driver
		spriteDriver.use(matrixProjectionAndView);
		
		
		//dmeta.layerDepth = - (map.getLevelReference().getHighestLevel() - layerIndex) - 1;
		//TokTales.getLog().d(TAG, "Drawing on depth: " + dmeta.layerDepth);
		
		
		mViewTransformer.getCurrentCamera().getWorldBounds(cameraBounds);
		mGridTransformer.getGrid().worldToGrid(cameraBounds, cameraGridBounds);
		
		//Add 1 because our bounds are 0 indexed and we actually want the number of blocks inside the camera
		int blocksVertical = cameraGridBounds.height() == 0 ? 0 : Math.abs(cameraGridBounds.bottom() - cameraGridBounds.top()) + 1;
		int blocksHorizontal = cameraGridBounds.width() == 0 ? 0 : Math.abs(cameraGridBounds.right() - cameraGridBounds.left()) + 1;
		
		for(int y = 0; y < blocksVertical; y++) {
			for(int x = 0; x < blocksHorizontal; x++) {
				
				dmeta.indexPoint.set(x, y);
				
				// gridPosition will contain the indexed grid position of the camera
				dmeta.gridPosition.x = cameraGridBounds.left() + x;
				dmeta.gridPosition.y = cameraGridBounds.top() + y;
				
				// Check if grid position is inside the map
				if(dmeta.gridPosition.x < 0 || dmeta.gridPosition.x >= map.getHorizontalSize()
						|| dmeta.gridPosition.y < 0 || dmeta.gridPosition.y >= map.getVerticalSize()) {
					
					// Position outside map
					continue;
				}
				
				
				// Source bounds for tile
				mGridTransformer.visibleScreenBoundsForTile(dmeta.gridPosition, dmeta.tileSourceBounds);
				
				// Destination offset
				dmeta.tileTranslation.set(mViewTransformer.getCurrentViewport().transformX(x * dmeta.tilePixeSize), mViewTransformer.getCurrentViewport().transformY(y * dmeta.tilePixeSize));
				
				
				IBlock block = map.getBlockAt(dmeta.gridPosition);
				IMapElement element = block.getElementOnLevel(mapLevel);
				
				if(element != null && element.isVisible()) {
					elementDraw(element, dmeta);
				}
				
			}
		}
		
		
		spriteDriver.release();
	}
	
	
	private void elementDraw(IMapElement element, DrawingMeta dmeta) {
		
		IElementType elemType = element.getElementType();
		
		if(TiledMapElementTypes.TYPE_TILED_TILE.matches(elemType)) {
			ITiledTileElement tileElement = (ITiledTileElement) element;
			
			drawElementSprite(tileElement.getSprite(), dmeta);
		}
		else if(MapElementTypes.TYPE_GROUND.matches(elemType)) {
			IGroundElement groundElement = (IGroundElement) element;
			IGraphicType groundGraphicType = groundElement.getGraphic().getGraphicType();
			
			if(groundGraphicType.matches(GameGraphicTypes.TYPE_SPRITE)) {
				ISpriteGraphic spriteGraphic = (ISpriteGraphic) groundElement.getGraphic();
				
				drawElementSprite(spriteGraphic.getSprite(), dmeta);
			}
		}
		else if(MapElementTypes.TYPE_PLAYER.matches(elemType)) {
			// Ignore
		}
		else {
			TokTales.getLog().logOnce('w', TAG + element.getClass(), TAG, "Cannot draw element: " +element);
		}
		
	}

	
	private void drawElementSprite(ISprite sprite, DrawingMeta dmeta) {
	
		ISpriteAsset spriteAsset = mSpriteManager.getSpriteAsset(sprite);
		if(spriteAsset == null) {
			// Asset has not been loaded yet
			return;
		}
		
		boolean assetIsSpecial = mSpriteManager.assetIsSpecial(spriteAsset);
		if(assetIsSpecial) {
			// Do not draw special sprites like error sprites?
		}
		
		IRenderTexture spriteTexture = mGamestate.getEngine().getContentService().extractAssetTexture(spriteAsset.getContent());
		if(spriteTexture == null) {
			// TODO: Implement special asset
			return;
		}

		
		// If only a part of the sprite should be rendered
		if(dmeta.tileSourceBounds.width() != dmeta.tilePixeSize || dmeta.tileSourceBounds.height() != dmeta.tilePixeSize) {
			
			spriteModel.setScaling2D(dmeta.tileSourceBounds.width(), dmeta.tileSourceBounds.height());
			
			spriteModel.setTranslation(
					dmeta.tileTranslation.x + dmeta.tileSourceBounds.left(),
					dmeta.tileTranslation.y + dmeta.tileSourceBounds.top(),
					0.0f);
					//(float)dmeta.layerDepth); // TODO: What to do about this?
			
			// Normalize by dividing
			spriteModel.getTextureScaling().set(
					dmeta.tileSourceBounds.width() / dmeta.tilePixeSize,
					dmeta.tileSourceBounds.height() / dmeta.tilePixeSize);
			
			spriteModel.getTextureTranslation().set(
					dmeta.tileSourceBounds.left() / dmeta.tilePixeSize,	//dmeta.tileTranslation.x / dmeta.tilePixeSize, WRONG
					dmeta.tileSourceBounds.top() / dmeta.tilePixeSize);	//dmeta.tileTranslation.y / dmeta.tilePixeSize);

		}
		else {
			spriteModel.setScaling2D(dmeta.tilePixeSize, dmeta.tilePixeSize);
			spriteModel.setTranslation(dmeta.tileTranslation.x, dmeta.tileTranslation.y, (float)dmeta.layerDepth);
			
			spriteModel.setTextureScaling(1.0f, 1.0f);
			spriteModel.setTextureTranslation(0.0f, 0.0f);
		}

		
		spriteModel.setTarget(sprite, spriteTexture);
		
		
		drawingOptions.set(RenderDriverOptions.DRAWING_OPTION_IGNORE_SPRITESET, assetIsSpecial);	// make a variable for the name ?
		
		spriteDriver.draw(spriteModel, drawingOptions);
		
	}
	
	
	
	
	private class DrawingMeta {
		
		private float tilePixeSize = 0;
		private int layerDepth = 0;
		
		private final Point2fImpl indexPoint = new Point2fImpl();
		private final MapPositionImpl gridPosition = new MapPositionImpl();
		private final Rectangle2fImpl tileSourceBounds = new Rectangle2fImpl();
		private final Point2fImpl tileTranslation = new Point2fImpl();
		
	}
	
	
}
