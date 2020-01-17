package com.tokelon.toktales.extensions.core.game.screen;

import org.joml.Matrix4f;

import com.tokelon.toktales.core.content.manage.sprite.ISpriteAssetManager;
import com.tokelon.toktales.core.content.sprite.ISprite;
import com.tokelon.toktales.core.content.sprite.ISpriteAsset;
import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.core.engine.render.IRenderService;
import com.tokelon.toktales.core.game.controller.IPlayerController;
import com.tokelon.toktales.core.game.graphic.GameGraphicTypes;
import com.tokelon.toktales.core.game.graphic.IBaseGraphic;
import com.tokelon.toktales.core.game.graphic.ISpriteGraphic;
import com.tokelon.toktales.core.game.model.IActor;
import com.tokelon.toktales.core.game.model.Point2fImpl;
import com.tokelon.toktales.core.game.model.Rectangle2fImpl;
import com.tokelon.toktales.core.game.model.map.IMapLayer;
import com.tokelon.toktales.core.game.screen.view.IViewTransformer;
import com.tokelon.toktales.core.game.states.GameStateSuppliers;
import com.tokelon.toktales.core.game.states.IExtendedGameScene;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.game.states.ITypedGameState;
import com.tokelon.toktales.core.render.IRenderDriver;
import com.tokelon.toktales.core.render.ITexture;
import com.tokelon.toktales.core.render.ITextureCoordinator;
import com.tokelon.toktales.core.render.RenderException;
import com.tokelon.toktales.core.render.model.ISpriteModel;
import com.tokelon.toktales.core.render.model.SpriteModel;
import com.tokelon.toktales.core.values.RenderDriverOptions;
import com.tokelon.toktales.tools.core.inject.ISupplier;
import com.tokelon.toktales.tools.core.objects.options.INamedOptions;
import com.tokelon.toktales.tools.core.objects.options.NamedOptionsImpl;

public class PlayerRenderer implements IPlayerRenderer {


	public static final String OPTION_MAP_LAYER = "map_layer";
	public static final String OPTION_DRAW_DEPTH = "draw_depth";

	
	
	private final Rectangle2fImpl playerDestinationBounds = new Rectangle2fImpl();
	
	private final Point2fImpl playerWorldCoordinates = new Point2fImpl();
	private final Point2fImpl playerCameraCoordinates = new Point2fImpl();
	private final Point2fImpl playerScreenCoordinates = new Point2fImpl();


	private final Matrix4f matrixProjection = new Matrix4f();
	private final Matrix4f matrixView = new Matrix4f();
	private final Matrix4f matrixProjectionAndView = new Matrix4f();
	
	
	private final SpriteModel spriteModel = new SpriteModel();
	
	private final NamedOptionsImpl drawingOptions = new NamedOptionsImpl();

	
	private IViewTransformer mViewTransformer;

	private IRenderDriver spriteDriver;
	
	
	private final ILogger logger;
	private final IRenderService renderService;
	private final ISpriteAssetManager spriteAssetManager;
	private final ISupplier<ITextureCoordinator> textureCoordinatorSupplier;
	private final ISupplier<IPlayerController> playerControllerSupplier;
	
	public PlayerRenderer(
			ILogging logging,
			IEngine engine,
			ISpriteAssetManager spriteAssetManager,
			ISupplier<ITextureCoordinator> textureCoordinatorSupplier,
			ISupplier<IPlayerController> playerControllerSupplier
	) {
		this.logger = logging.getLogger(getClass());
		this.renderService = engine.getRenderService();
		this.spriteAssetManager = spriteAssetManager;
		this.textureCoordinatorSupplier = textureCoordinatorSupplier;
		this.playerControllerSupplier = playerControllerSupplier;
		
		
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

		
		IPlayerController playerController = playerControllerSupplier.get();
		if(playerController == null) {
			logger.info("Draw was called but no player is available");
			return;
		}
		
		drawPlayer(playerController);
	}
	
	
	@Override
	public void drawPlayer(IPlayerController playerController) {
		if(mViewTransformer == null) {
			assert false : "Cannot draw without view";
			return;
		}
		
		
		IActor playerActor = playerController.getPlayer().getActor();
		
		if(!playerActor.isActive() || !playerActor.isVisible()) {
			return;
		}
		
		IBaseGraphic playerGraphic = playerActor.getGraphic();
		if(playerGraphic == null) {
			// uhhh skip it I guess?
			return;
		}
		

		
		playerActor.getRawWorldCoordinates(playerWorldCoordinates);
		
		mViewTransformer.getCurrentCamera().worldToCamera(playerWorldCoordinates, playerCameraCoordinates);
		
		mViewTransformer.cameraToScreen(playerCameraCoordinates, playerScreenCoordinates);
		
		
		//if(playerEntity.getRender() == IMapEntity.RENDER_FULL) {
		
		playerDestinationBounds.set(
				playerScreenCoordinates.x,
				playerScreenCoordinates.y,
				playerScreenCoordinates.x + mViewTransformer.cameraToViewportX(playerActor.getWidth()),
				playerScreenCoordinates.y + mViewTransformer.cameraToViewportY(playerActor.getHeight())
				);
		
		
		
		if(GameGraphicTypes.TYPE_SPRITE.matches(playerGraphic.getGraphicType())) {
			
			ISpriteGraphic spriteGraphic = (ISpriteGraphic) playerGraphic;
			ISprite playerSprite = spriteGraphic.getSprite();
			
			ISpriteAsset playerSpriteAsset = spriteAssetManager.getAsset(playerSprite.getAssetKey());
			if(!spriteAssetManager.isAssetValid(playerSpriteAsset)) {
				return;
			}
			
			boolean assetIsSpecial = spriteAssetManager.isAssetSpecial(playerSpriteAsset);
			ITexture playerTexture = playerSpriteAsset.getTexture();

			
			spriteModel.setScaling2D(playerDestinationBounds.width(), playerDestinationBounds.height());
			spriteModel.setTranslation2D(playerDestinationBounds.left(), playerDestinationBounds.top());	//(float)drawDepth
			
			// TODO: Textures
			//textureModel.setTextureRectangleToFull();
			spriteModel.setTextureScaling(1.0f, 1.0f);
			spriteModel.setTextureTranslation(0.0f, 0.0f);
			
			
			spriteModel.setTargetSprite(playerSprite);
			spriteModel.setTargetTexture(playerTexture);
			

			drawingOptions.set(RenderDriverOptions.DRAWING_OPTION_IGNORE_SPRITESET, assetIsSpecial);
			spriteDriver.drawQuick(matrixProjectionAndView, spriteModel, drawingOptions);
			

		}
		else {
			logger.warn("Cannot draw sprite: Unknown graphic type");
		}
		
		
		/*	
		else if(playerEntity.getRender() == IMapEntity.RENDER_PARTIAL) {
				// On the viewport edge
				// TODO: Implement ?
			}
			
		*/
		
	}
	

	
	@Override
	public void drawFull(INamedOptions options) {
		drawPlayer(playerControllerSupplier.get());
	}
	
	
	public static class PlayerRendererFactory implements IPlayerRendererFactory {
		
		@Override
		public PlayerRenderer create(
				IEngineContext engineContext,
				ISupplier<ITextureCoordinator> textureCoordinatorSupplier,
				ISupplier<IPlayerController> playerControllerSupplier		
		) {
			return new PlayerRenderer(
					engineContext.getLogging(),
					engineContext.getEngine(),
					engineContext.getGame().getContentManager().getSpriteAssetManager(),
					textureCoordinatorSupplier,
					playerControllerSupplier
			);
		}

		
		@Override
		public PlayerRenderer createForGamestate(IGameState gamestate) {
			return new PlayerRenderer(
					gamestate.getLogging(),
					gamestate.getEngine(),
					gamestate.getGame().getContentManager().getSpriteAssetManager(),
					() -> gamestate.getStateRender().getTextureCoordinator(),
					GameStateSuppliers.ofPlayerControllerFromManager(gamestate)
			);
		}
		
		@Override
		public PlayerRenderer createForTypedGamestate(ITypedGameState<? extends IExtendedGameScene> typedGamestate) {
			return new PlayerRenderer(
					typedGamestate.getLogging(),
					typedGamestate.getEngine(),
					typedGamestate.getGame().getContentManager().getSpriteAssetManager(),
					() -> typedGamestate.getStateRender().getTextureCoordinator(),
					GameStateSuppliers.ofPlayerControllerFromGamestate(typedGamestate)
			);
		}
	}
	
}
