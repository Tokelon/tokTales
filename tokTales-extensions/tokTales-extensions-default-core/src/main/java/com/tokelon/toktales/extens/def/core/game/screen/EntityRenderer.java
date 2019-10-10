package com.tokelon.toktales.extens.def.core.game.screen;

import java.util.Locale;
import java.util.Set;

import org.joml.Matrix4f;

import com.tokelon.toktales.core.content.manage.sprite.ISpriteAssetManager;
import com.tokelon.toktales.core.content.sprite.ISprite;
import com.tokelon.toktales.core.content.sprite.ISpriteAsset;
import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.inject.ISupplier;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.core.engine.render.IRenderService;
import com.tokelon.toktales.core.game.graphic.GameGraphicTypes;
import com.tokelon.toktales.core.game.graphic.IBaseGraphic;
import com.tokelon.toktales.core.game.graphic.ISpriteGraphic;
import com.tokelon.toktales.core.game.model.Point2fImpl;
import com.tokelon.toktales.core.game.model.Rectangle2fImpl;
import com.tokelon.toktales.core.game.model.entity.IGameEntity;
import com.tokelon.toktales.core.game.model.map.IMapLayer;
import com.tokelon.toktales.core.game.screen.view.IViewTransformer;
import com.tokelon.toktales.core.game.states.GameStateSuppliers;
import com.tokelon.toktales.core.game.states.IExtendedGameScene;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.game.states.ITypedGameState;
import com.tokelon.toktales.core.game.world.IWorldspace;
import com.tokelon.toktales.core.render.IRenderDriver;
import com.tokelon.toktales.core.render.ITexture;
import com.tokelon.toktales.core.render.ITextureCoordinator;
import com.tokelon.toktales.core.render.RenderException;
import com.tokelon.toktales.core.render.model.ISpriteModel;
import com.tokelon.toktales.core.render.model.SpriteModel;
import com.tokelon.toktales.core.values.RenderDriverOptions;
import com.tokelon.toktales.tools.core.objects.options.INamedOptions;
import com.tokelon.toktales.tools.core.objects.options.NamedOptionsImpl;

public class EntityRenderer implements IEntityRenderer {


	public static final String OPTION_MAP_LAYER = "map_layer";
	public static final String OPTION_DRAW_DEPTH = "draw_depth";

	
	private final Matrix4f matrixProjection = new Matrix4f();
	private final Matrix4f matrixView = new Matrix4f();
	private final Matrix4f matrixProjectionAndView = new Matrix4f();

	
	private final Point2fImpl entityWorldCoordinates = new Point2fImpl();
	private final Point2fImpl entityCameraCoordinates = new Point2fImpl();
	private final Point2fImpl entityScreenCoordinates = new Point2fImpl();

	private final Rectangle2fImpl entityDestinationBounds = new Rectangle2fImpl();

	
	private final SpriteModel spriteModel = new SpriteModel();

	private final NamedOptionsImpl drawingOptions = new NamedOptionsImpl();

	
	
	private IRenderDriver spriteDriver;
	
	private IViewTransformer mViewTransformer;

	
	private final ILogger logger;
	private final IRenderService renderService;
	private final ISpriteAssetManager spriteAssetManager;
	private final ISupplier<ITextureCoordinator> textureCoordinatorSupplier;
	private final ISupplier<IWorldspace> worldspaceSupplier;

	public EntityRenderer(
			ILogging logging,
			IEngine engine,
			ISpriteAssetManager spriteAssetManager,
			ISupplier<ITextureCoordinator> textureCoordinatorSupplier,
			ISupplier<IWorldspace> worldspaceSupplier
	) {
		this.logger = logging.getLogger(getClass());
		this.renderService = engine.getRenderService();
		this.spriteAssetManager = spriteAssetManager;
		this.textureCoordinatorSupplier = textureCoordinatorSupplier;
		this.worldspaceSupplier = worldspaceSupplier;
	
		
		spriteModel.setInvertYAxis(true);
	}
	
	
	@Override
	public void contextCreated() {
		
		spriteDriver = renderService.getRenderAccess().requestDriver(ISpriteModel.class.getName());
		if(spriteDriver == null) {
			throw new RenderException("No render driver found for: " +ISpriteModel.class.getName());
		}
		
		spriteDriver.create();
		
		
		spriteModel.setTextureCoordinator(textureCoordinatorSupplier.get());
	}

	
	@Override
	public void contextChanged(IViewTransformer viewTransformer, Matrix4f projectionMatrix) {
		this.mViewTransformer = viewTransformer;
		
		
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
		
		spriteModel.setTextureCoordinator(null);
		
		mViewTransformer = null;
	}
	
	
	

	@Override
	public void prepare(long currentTimeMillis) {
		// if !view
		
		// Nothing
	}

	@Override
	public void drawLayer(INamedOptions options, String layerName) {
		if(options.has(OPTION_MAP_LAYER)) {
			IMapLayer mapLayer = (IMapLayer) options.get(OPTION_MAP_LAYER);
			
			boolean playerRender = mapLayer.isAttributed(IMapLayer.ATTRIBUTED_PLAYER);
			boolean playerFringe = mapLayer.isAttributed(IMapLayer.ATTRIBUTED_PLAYER_FRINGE);
			boolean playerUnfringe = mapLayer.isAttributed(IMapLayer.ATTRIBUTED_PLAYER_UNFRINGE);
			
			// TODO: Implement fringe or remove it completely
			if(!(playerRender || playerFringe)) {		// || playerFringe || playerUnfringe
				// playerFringe is only here to support older (test) maps
				
				// Nothing to render on this layer
				return;
			}
		}
		
		// TODO: What to do about this?
		int drawDepth = options.getAsExactOrDefault(OPTION_DRAW_DEPTH, -1);
		
		
		IWorldspace worldspace = worldspaceSupplier.get();
		if(worldspace == null) {
			logger.info("Draw was called but no worldspace is available");
			return;
		}
		
		drawEntities(worldspace);
	}

	
	@Override
	public void drawFull(INamedOptions options) {
		IWorldspace worldspace = worldspaceSupplier.get();
		if(worldspace == null) {
			logger.info("Draw was called but no worldspace is available");
			return;
		}
		
		drawEntities(worldspace);
	}

	
	@Override
	public void drawEntities(IWorldspace worldspace) {
		if(mViewTransformer == null) {
			assert false : "Cannot draw without view";
			return;
		}
		
		
		Set<String> entityIDSet = worldspace.getEntityIDSet();
		for(String entityID: entityIDSet) {
			
			if(entityID.toLowerCase(Locale.ENGLISH).contains("player")) {
				// TODO: How to implement this ?
				// Do not render anything player related
				continue;
			}
			
			
			IGameEntity entity = worldspace.getEntity(entityID);

			if(!entity.isActive() || !entity.isVisible()) {
				// Do not render
				continue;
			}
		
			
			IBaseGraphic entityGraphic = entity.getGraphic();
			if(entityGraphic == null) {
				// uhhh skip it I guess?
				continue;
			}
			
			
			// World coordinates
			entity.getRawWorldCoordinates(entityWorldCoordinates);
			
			// Convert from world coordinates to camera coordinates
			mViewTransformer.getCurrentCamera().worldToCamera(entityWorldCoordinates, entityCameraCoordinates);
			
			// Transform from local (camera) coordinates to screen coordinates
			mViewTransformer.cameraToScreen(entityCameraCoordinates, entityScreenCoordinates);

			
			//if(playerEntity.getRender() == IMapEntity.RENDER_FULL) {

			entityDestinationBounds.set(entityScreenCoordinates.x
					, entityScreenCoordinates.y
					, entityScreenCoordinates.x + mViewTransformer.cameraToViewportX(entity.getWidth())
					, entityScreenCoordinates.y + mViewTransformer.cameraToViewportY(entity.getHeight())
					);
			
			
			if(GameGraphicTypes.TYPE_SPRITE.matches(entityGraphic.getGraphicType())) {
				
				ISpriteGraphic spriteGraphic = (ISpriteGraphic) entityGraphic;
				ISprite entitySprite = spriteGraphic.getSprite();

				
				ISpriteAsset entitySpriteAsset = spriteAssetManager.getAsset(entitySprite.getAssetKey());
				if(!spriteAssetManager.isAssetValid(entitySpriteAsset)) {
					continue;
				}
				
				boolean assetIsSpecial = spriteAssetManager.isAssetSpecial(entitySpriteAsset);
				ITexture entityTexture = entitySpriteAsset.getTexture();

				
				spriteModel.setScaling2D(entityDestinationBounds.width(), entityDestinationBounds.height());
				spriteModel.setTranslation2D(entityDestinationBounds.left(), entityDestinationBounds.top()); // (float)drawDepth

				spriteModel.setTextureScaling(1.0f, 1.0f);
				spriteModel.setTextureTranslation(0.0f, 0.0f);
				
				spriteModel.setTargetSprite(entitySprite);
				spriteModel.setTargetTexture(entityTexture);
				
				
				drawingOptions.set(RenderDriverOptions.DRAWING_OPTION_IGNORE_SPRITESET, assetIsSpecial);
				spriteDriver.drawQuick(matrixProjectionAndView, spriteModel, drawingOptions);

			}
			else {
				// I dont even know
				logger.warn("Cannot draw: Unknown graphic type");
			}
			
			
			/*	
			else if(playerEntity.getRender() == IMapEntity.RENDER_PARTIAL) {
					// On the viewport edge
					// TODO: Implement ?
				}
				
			*/	
		} //end for loop
		
	}
	
	
	public static class EntityRendererFactory implements IEntityRendererFactory {
		
		@Override
		public EntityRenderer create(
				IEngineContext engineContext,
				ISupplier<ITextureCoordinator> textureCoordinatorSupplier,
				ISupplier<IWorldspace> worldspaceSupplier		
		) {
			return new EntityRenderer(
					engineContext.getLogging(),
					engineContext.getEngine(),
					engineContext.getGame().getContentManager().getSpriteAssetManager(),
					textureCoordinatorSupplier,
					worldspaceSupplier
			);
		}
		
		
		@Override
		public EntityRenderer createForGamestate(IGameState gamestate, ISupplier<IWorldspace> worldspaceSupplier) {
			return new EntityRenderer(
					gamestate.getLogging(),
					gamestate.getEngine(),
					gamestate.getGame().getContentManager().getSpriteAssetManager(),
					() -> gamestate.getStateRender().getTextureCoordinator(),
					worldspaceSupplier
			);
		}
		
		@Override
		public EntityRenderer createForTypedGamestate(ITypedGameState<? extends IExtendedGameScene> typedGamestate) {
			return new EntityRenderer(
					typedGamestate.getLogging(),
					typedGamestate.getEngine(),
					typedGamestate.getGame().getContentManager().getSpriteAssetManager(),
					() -> typedGamestate.getStateRender().getTextureCoordinator(),
					GameStateSuppliers.ofWorldspaceFromGamestate(typedGamestate)
			);
		}
	}
	
}
