package com.tokelon.toktales.extens.def.core.game.screen;

import java.util.Map;

import org.joml.Vector4f;

import com.tokelon.toktales.core.content.graphics.IRGBAColor;
import com.tokelon.toktales.core.content.graphics.RGBAColorImpl;
import com.tokelon.toktales.core.content.manage.sprite.ISpriteAssetManager;
import com.tokelon.toktales.core.content.sprite.ISprite;
import com.tokelon.toktales.core.content.sprite.ISpriteAsset;
import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.render.IRenderService;
import com.tokelon.toktales.core.game.controller.map.IMapController;
import com.tokelon.toktales.core.game.model.IPolyline2f.IExtendablePolyline2f;
import com.tokelon.toktales.core.game.model.Point2fImpl;
import com.tokelon.toktales.core.game.model.Rectangle2fImpl;
import com.tokelon.toktales.core.game.model.map.IMapObject;
import com.tokelon.toktales.core.game.states.GameStateSuppliers;
import com.tokelon.toktales.core.game.states.IExtendedGameScene;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.game.states.ITypedGameState;
import com.tokelon.toktales.core.game.world.IEllipseGeometry;
import com.tokelon.toktales.core.game.world.IObjectContainer;
import com.tokelon.toktales.core.game.world.IPolygonGeometry;
import com.tokelon.toktales.core.game.world.IPolylineGeometry;
import com.tokelon.toktales.core.game.world.IRectangleGeometry;
import com.tokelon.toktales.core.game.world.IWorldGeometry;
import com.tokelon.toktales.core.render.AbstractRenderer;
import com.tokelon.toktales.core.render.IRenderDriver;
import com.tokelon.toktales.core.render.ITexture;
import com.tokelon.toktales.core.render.ITextureCoordinator;
import com.tokelon.toktales.core.render.RenderException;
import com.tokelon.toktales.core.render.model.ILineModel;
import com.tokelon.toktales.core.render.model.IPointModel;
import com.tokelon.toktales.core.render.model.IRectangleModel;
import com.tokelon.toktales.core.render.model.ISpriteModel;
import com.tokelon.toktales.core.render.model.ITriangleModel;
import com.tokelon.toktales.core.render.model.LineModel;
import com.tokelon.toktales.core.render.model.RectangleModel;
import com.tokelon.toktales.core.render.model.SpriteModel;
import com.tokelon.toktales.core.render.model.TriangleModel;
import com.tokelon.toktales.core.util.function.Supplier;
import com.tokelon.toktales.core.util.options.INamedOptions;
import com.tokelon.toktales.core.util.options.NamedOptionsImpl;
import com.tokelon.toktales.core.values.RenderDriverOptions;

public class ObjectRenderer extends AbstractRenderer implements IObjectRenderer {

	public static final String TAG = "ObjectRenderer";


	private final Rectangle2fImpl spriteCoordinates = new Rectangle2fImpl();
	
	private final Rectangle2fImpl objectRectCoordinates = new Rectangle2fImpl();
	private final RectangleModel objectRectModel = new RectangleModel();
	private final IRGBAColor colorRectangle = RGBAColorImpl.createFromCodeWithAlpha("#F0F", 0.5f);
	private final Vector4f colorVectorRectangle = new Vector4f(colorRectangle.getRed(), colorRectangle.getGreen(), colorRectangle.getBlue(), colorRectangle.getAlpha());
	
	private final Rectangle2fImpl objectEllipseCoordinates = new Rectangle2fImpl();
	private final RectangleModel objectEllipseModel = new RectangleModel();
	private final IRGBAColor colorEllipse = RGBAColorImpl.createFromCodeWithAlpha("#0FA", 0.5f);
	private final Vector4f colorVectorEllipse = new Vector4f(colorEllipse.getRed(), colorEllipse.getGreen(), colorEllipse.getBlue(), colorEllipse.getAlpha());

	private final Point2fImpl objectPolylineCoordinatesFirst = new Point2fImpl();
	private final Point2fImpl objectPolylineCoordinatesSecond = new Point2fImpl();
	private final LineModel objectPolylineModel = new LineModel();
	private final IRGBAColor colorPolyline = RGBAColorImpl.createFromCodeWithAlpha("#FF0", 1.0f);
	private final Vector4f colorVectorPolyline = new Vector4f(colorPolyline.getRed(), colorPolyline.getGreen(), colorPolyline.getBlue(), colorPolyline.getAlpha());

	private final Point2fImpl objectPolygonCoordinatesFirst = new Point2fImpl();
	private final Point2fImpl objectPolygonCoordinatesSecond = new Point2fImpl();
	private final Point2fImpl objectPolygonCoordinatesThird = new Point2fImpl();
	private final TriangleModel objectPolygonModel = new TriangleModel();
	private final IRGBAColor colorPolygon = RGBAColorImpl.createFromCodeWithAlpha("#5F0", 0.5f);
	private final Vector4f colorVectorPolygon = new Vector4f(colorPolygon.getRed(), colorPolygon.getGreen(), colorPolygon.getBlue(), colorPolygon.getAlpha());
	
	private final SpriteModel spriteModel = new SpriteModel();

	private final NamedOptionsImpl drawingOptions = new NamedOptionsImpl();

	private boolean logStop = false;
	private IRenderDriver lastUsedDriver = null;
	

	
	private IRenderDriver shapeDriver;
	private IRenderDriver spriteDriver;
	
	private final ILogger logger;
	private final IRenderService renderService;
	private final ISpriteAssetManager spriteAssetManager;
	private final Supplier<ITextureCoordinator> textureCoordinatorSupplier;
	private final Supplier<IMapController> mapControllerSupplier;
	
	public ObjectRenderer(
			ILogger logger,
			IEngine engine,
			ISpriteAssetManager spriteAssetManager,
			Supplier<ITextureCoordinator> textureCoordinatorSupplier,
			Supplier<IMapController> mapControllerSupplier
	) {
		this.logger = logger;
		this.renderService = engine.getRenderService();
		this.spriteAssetManager = spriteAssetManager;
		this.textureCoordinatorSupplier = textureCoordinatorSupplier;
		this.mapControllerSupplier = mapControllerSupplier;
		
		
		objectRectModel.setFill(true);
		objectRectModel.setTargetColor(colorVectorRectangle);
		
		objectEllipseModel.setFill(true);
		objectEllipseModel.setTargetColor(colorVectorEllipse);
		
		objectPolylineModel.setTargetColor(colorVectorPolyline);
		
		objectPolygonModel.setFill(true);
		objectPolygonModel.setTargetColor(colorVectorPolygon);
		
		
		spriteModel.setInvertYAxis(true);
	}
	
	
	

	@Override
	protected void onContextCreated() {
		shapeDriver = renderService.getRenderAccess().requestDriver(IPointModel.class.getName() + ILineModel.class.getName() + ITriangleModel.class.getName() + IRectangleModel.class.getName());
		if(shapeDriver == null) {
			throw new RenderException("No render driver found for shapes");
		}
		
		shapeDriver.create();
		

		spriteDriver = renderService.getRenderAccess().requestDriver(ISpriteModel.class.getName());
		if(spriteDriver == null) {
			throw new RenderException("No render driver found for sprites");
		}
		
		spriteDriver.create();
		

		spriteModel.setTextureCoordinator(textureCoordinatorSupplier.get());
	}

	@Override
	protected void onContextChanged() {
		// Nothing
	}

	@Override
	protected void onContextDestroyed() {
		if(shapeDriver != null) {
			shapeDriver.destroy();
			shapeDriver = null;
		}
		
		if(spriteDriver != null) {
			spriteDriver.destroy();
			spriteDriver = null;
		}
		
		spriteModel.setTextureCoordinator(null);
		lastUsedDriver = null;
	}

	
	
	@Override
	public void prepare(long currentTimeMillis) {
		// if !view
		
		// Nothing
	}

	@Override
	public void drawLayer(INamedOptions options, String layerName) {
		IMapController mapController = mapControllerSupplier.get();
		if(mapController == null) {
			logger.i(TAG, "Draw was called but no Map is available");
			return;
		}
		
		drawObjectsOnMapLayer(mapController, layerName);
	}
	
	
	@Override
	public void drawObjectsOnMapLayer(IMapController mapController, String layerName) {
		if(!hasView()) {
			assert false : "Cannot draw without view";
			return;
		}
		

		// TODO: How to handle layer?
		int level = mapController.getMap().getLevelForLayer(layerName);

		
		// Dynamic use implemented
		//shapeDriver.use(getMatrixProjectionAndView());
		
		IObjectContainer<IMapObject> objectContainer = mapController.getMap().getObjectContainer();
		
		Map<String, IMapObject> objectMap = objectContainer.getObjectMap();
		synchronized (objectMap) {
			for(IMapObject mapObject: objectMap.values()) {
				if(!logStop) { // TODO: Remove
					//logger.d("ObjectRenderer", "Rendering Object: " +mapObject.getObjectName());	
				}
				
				// TODO: Important - Implement only rendering when in camera!
				if(!mapObject.isVisible() || mapObject.getLayerPosition() != level) {
					continue;
				}
				
				
				IWorldGeometry geometry = mapObject.getGeometry();
				if(mapObject.hasSprite()) {
					ISprite sprite = mapObject.getSprite();
					
					ISpriteAsset spriteAsset = spriteAssetManager.getAsset(sprite.getAssetKey());
					if(!spriteAssetManager.isAssetValid(spriteAsset)) {
						continue;
					}
					
					boolean assetIsSpecial = spriteAssetManager.isAssetSpecial(spriteAsset);
					ITexture spriteTexture = spriteAsset.getTexture();
					
					spriteCoordinates.set(
							mapObject.getWorldX(),
							mapObject.getWorldY(),
							mapObject.getWorldX() + mapObject.getWidth(),
							mapObject.getWorldY() + mapObject.getHeight()
					);
					getViewTransformer().getCurrentCamera().worldToCamera(spriteCoordinates, spriteCoordinates);
					getViewTransformer().cameraToScreen(spriteCoordinates, spriteCoordinates);
					
					spriteModel.setScaling2D(spriteCoordinates.getWidth(), spriteCoordinates.getHeight());
					spriteModel.setTranslation2D(spriteCoordinates.left(), spriteCoordinates.top());
					
					spriteModel.setTextureScaling(1.0f, 1.0f);
					spriteModel.setTextureTranslation(0.0f, 0.0f);
					
					spriteModel.setTargetSprite(sprite);
					spriteModel.setTargetTexture(spriteTexture);
					
					drawingOptions.set(RenderDriverOptions.DRAWING_OPTION_IGNORE_SPRITESET, assetIsSpecial);
					
					lazyUseRenderDriver(spriteDriver);
					spriteDriver.draw(spriteModel, drawingOptions);
				}
				else if(geometry instanceof IRectangleGeometry) {
					IRectangleGeometry rectGeo = (IRectangleGeometry) geometry;
					
					getViewTransformer().getCurrentCamera().worldToCamera(rectGeo.getRectangle(), objectRectCoordinates);
					getViewTransformer().cameraToScreen(objectRectCoordinates, objectRectCoordinates);
					
					objectRectModel.setTargetCoordinates(
							objectRectCoordinates.getLeft(),
							objectRectCoordinates.getTop(),
							objectRectCoordinates.getLeft(),
							objectRectCoordinates.getBottom(),
							objectRectCoordinates.getRight(),
							objectRectCoordinates.getBottom(),
							objectRectCoordinates.getRight(),
							objectRectCoordinates.getTop()
					);
					
					lazyUseRenderDriver(shapeDriver);
					shapeDriver.draw(objectRectModel, drawingOptions);
				}
				else if(geometry instanceof IEllipseGeometry) {
					IEllipseGeometry ellGeo = (IEllipseGeometry) geometry;
					
					getViewTransformer().getCurrentCamera().worldToCamera(ellGeo.getRectangle(), objectEllipseCoordinates);
					getViewTransformer().cameraToScreen(objectEllipseCoordinates, objectEllipseCoordinates);
					
					objectEllipseModel.setTargetCoordinates(
							objectEllipseCoordinates.getLeft(),
							objectEllipseCoordinates.getTop() + objectEllipseCoordinates.getHeight() / 2.0f,
							objectEllipseCoordinates.getLeft() + objectEllipseCoordinates.getWidth() / 2.0f,
							objectEllipseCoordinates.getBottom(),
							objectEllipseCoordinates.getRight(),
							objectEllipseCoordinates.getBottom() - objectEllipseCoordinates.getHeight() / 2.0f,
							objectEllipseCoordinates.getRight() - objectEllipseCoordinates.getWidth() / 2.0f,
							objectEllipseCoordinates.getTop()
					);
					
					lazyUseRenderDriver(shapeDriver);
					shapeDriver.draw(objectEllipseModel, drawingOptions);
				}
				else if(geometry instanceof IPolylineGeometry) {
					IPolylineGeometry polylineGeo = (IPolylineGeometry) geometry;
					
					lazyUseRenderDriver(shapeDriver);
					
					IExtendablePolyline2f polyline = polylineGeo.getPolyline();
					for(int i = 0; i < polyline.getPointCount() - 1; i++) {
						
						polyline.getPointAt(i, objectPolylineCoordinatesFirst);
						getViewTransformer().getCurrentCamera().worldToCamera(objectPolylineCoordinatesFirst, objectPolylineCoordinatesFirst);
						getViewTransformer().cameraToScreen(objectPolylineCoordinatesFirst, objectPolylineCoordinatesFirst);
						
						polyline.getPointAt(i + 1, objectPolylineCoordinatesSecond);
						getViewTransformer().getCurrentCamera().worldToCamera(objectPolylineCoordinatesSecond, objectPolylineCoordinatesSecond);
						getViewTransformer().cameraToScreen(objectPolylineCoordinatesSecond, objectPolylineCoordinatesSecond);
						
						objectPolylineModel.setTargetCoordinates(
								objectPolylineCoordinatesFirst.x(),
								objectPolylineCoordinatesFirst.y(),
								objectPolylineCoordinatesSecond.x(),
								objectPolylineCoordinatesSecond.y());
						
						shapeDriver.draw(objectPolylineModel, drawingOptions);
					}
				}
				else if(geometry instanceof IPolygonGeometry) {
					IPolygonGeometry polygonGeo = (IPolygonGeometry) geometry;
					
					IExtendablePolyline2f polyline = polygonGeo.getPolyline();
					
					polyline.getPointAt(0, objectPolygonCoordinatesFirst);
					getViewTransformer().getCurrentCamera().worldToCamera(objectPolygonCoordinatesFirst, objectPolygonCoordinatesFirst);
					getViewTransformer().cameraToScreen(objectPolygonCoordinatesFirst, objectPolygonCoordinatesFirst);

					lazyUseRenderDriver(shapeDriver);
					
					for(int i = 1; i < polyline.getPointCount() - 1; i++) {
						polyline.getPointAt(i, objectPolygonCoordinatesSecond);
						getViewTransformer().getCurrentCamera().worldToCamera(objectPolygonCoordinatesSecond, objectPolygonCoordinatesSecond);
						getViewTransformer().cameraToScreen(objectPolygonCoordinatesSecond, objectPolygonCoordinatesSecond);

						polyline.getPointAt(i + 1, objectPolygonCoordinatesThird);
						getViewTransformer().getCurrentCamera().worldToCamera(objectPolygonCoordinatesThird, objectPolygonCoordinatesThird);
						getViewTransformer().cameraToScreen(objectPolygonCoordinatesThird, objectPolygonCoordinatesThird);
						
						objectPolygonModel.setTargetCoordinates(
								objectPolygonCoordinatesFirst.x(),
								objectPolygonCoordinatesFirst.y(),
								objectPolygonCoordinatesSecond.x(),
								objectPolygonCoordinatesSecond.y(),
								objectPolygonCoordinatesThird.x(),
								objectPolygonCoordinatesThird.y()
						);
						
						shapeDriver.draw(objectPolygonModel, drawingOptions);
					}
				}
				else {
					// cannot draw
				}
				
			}
		}
		
		// Dynamic use implemented
		//shapeDriver.release();
		lazyReleaseRenderDriver();

		logStop = true;
	}
	
	
	private void lazyUseRenderDriver(IRenderDriver driver) {
		if(lastUsedDriver == driver) {
			// Nothing to do
		}
		else if(lastUsedDriver == null) {
			driver.use(getMatrixProjectionAndView());
			lastUsedDriver = driver;
		}
		else {
			lastUsedDriver.release();
			
			driver.use(getMatrixProjectionAndView());
			lastUsedDriver = driver;
		}
	}
	
	private void lazyReleaseRenderDriver() {
		if(lastUsedDriver == null) {
			// Nothing to do
		}
		else {
			lastUsedDriver.release();
			lastUsedDriver = null;
		}
	}

	
	@Override
	public void drawFull(INamedOptions options) {
		assert false : "Not supported";
	}
	
	
	public static class ObjectRendererFactory implements IObjectRendererFactory {
		
		@Override
		public ObjectRenderer create(
				IEngineContext engineContext,
				Supplier<ITextureCoordinator> textureCoordinatorSupplier,
				Supplier<IMapController> mapControllerSupplier		
		) {
			return new ObjectRenderer(
					engineContext.getLog(),
					engineContext.getEngine(),
					engineContext.getGame().getContentManager().getSpriteAssetManager(),
					textureCoordinatorSupplier,
					mapControllerSupplier
			);
		}
		
		
		@Override
		public ObjectRenderer createForGamestate(IGameState gamestate) {
			return new ObjectRenderer(
					gamestate.getLog(),
					gamestate.getEngine(),
					gamestate.getGame().getContentManager().getSpriteAssetManager(),
					() -> gamestate.getStateRender().getTextureCoordinator(),
					GameStateSuppliers.ofMapControllerFromManager(gamestate)
			);
		}
		
		@Override
		public ObjectRenderer createForTypedGamestate(ITypedGameState<? extends IExtendedGameScene> gamestate) {
			return new ObjectRenderer(
					gamestate.getLog(),
					gamestate.getEngine(),
					gamestate.getGame().getContentManager().getSpriteAssetManager(),
					() -> gamestate.getStateRender().getTextureCoordinator(),
					GameStateSuppliers.ofMapControllerFromGamestate(gamestate)
			);
		}
	}

}
