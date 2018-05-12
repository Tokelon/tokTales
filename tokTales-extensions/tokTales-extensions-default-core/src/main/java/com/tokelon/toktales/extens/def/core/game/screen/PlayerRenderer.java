package com.tokelon.toktales.extens.def.core.game.screen;

import org.joml.Matrix4f;

import com.tokelon.toktales.core.content.sprite.ISprite;
import com.tokelon.toktales.core.content.sprite.ISpriteAsset;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.game.controller.IPlayerController;
import com.tokelon.toktales.core.game.graphic.GameGraphicTypes;
import com.tokelon.toktales.core.game.graphic.IBaseGraphic;
import com.tokelon.toktales.core.game.graphic.ISpriteGraphic;
import com.tokelon.toktales.core.game.model.IPlayer;
import com.tokelon.toktales.core.game.model.Point2fImpl;
import com.tokelon.toktales.core.game.model.Rectangle2fImpl;
import com.tokelon.toktales.core.game.model.map.IMapLayer;
import com.tokelon.toktales.core.game.screen.view.IViewTransformer;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.render.IRenderDriver;
import com.tokelon.toktales.core.render.ITexture;
import com.tokelon.toktales.core.render.RenderException;
import com.tokelon.toktales.core.render.model.ISpriteModel;
import com.tokelon.toktales.core.render.model.SpriteModel;
import com.tokelon.toktales.core.util.INamedOptions;
import com.tokelon.toktales.core.util.NamedOptionsImpl;
import com.tokelon.toktales.core.values.RenderDriverOptions;

public class PlayerRenderer implements IPlayerRenderer {

	public static final String TAG = "PlayerRenderer";
	
	
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
	
	private final IGameState mGamestate;

	public PlayerRenderer(IGameState gamestate) {
		this.mGamestate = gamestate;

		spriteModel.setInvertYAxis(true);
	}
	
	
	
	@Override
	public void contextCreated() {
		
		spriteDriver = mGamestate.getEngine().getRenderService().getRenderAccess().requestDriver(ISpriteModel.class.getName());
		if(spriteDriver == null) {
			throw new RenderException("No render driver found for: " +ISpriteModel.class.getName());
		}
		
		spriteDriver.create();
		
		
		spriteModel.setTextureCoordinator(mGamestate.getStateRender().getTextureCoordinator());
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
		int drawDepth = options.getOrDefault(OPTION_DRAW_DEPTH, -1);

		
		IPlayerController playerController = mGamestate.getActiveScene().getPlayerController();

		drawPlayer(playerController);
	}
	
	
	@Override
	public void drawPlayer(IPlayerController playerController) {
		if(mViewTransformer == null) {
			assert false : "Cannot draw without view";
			return;
		}
		
		
		IPlayer player = playerController.getPlayer();
		
		if(!player.isActive() || !player.isVisible()) {
			return;
		}
		
		IBaseGraphic playerGraphic = player.getGraphic();
		if(playerGraphic == null) {
			// uhhh skip it I guess?
			return;
		}
		

		
		player.getRawWorldCoordinates(playerWorldCoordinates);
		
		mViewTransformer.getCurrentCamera().worldToCamera(playerWorldCoordinates, playerCameraCoordinates);
		
		mViewTransformer.cameraToScreen(playerCameraCoordinates, playerScreenCoordinates);
		
		
		//if(playerEntity.getRender() == IMapEntity.RENDER_FULL) {
		
		playerDestinationBounds.set(
				playerScreenCoordinates.x,
				playerScreenCoordinates.y,
				playerScreenCoordinates.x + mViewTransformer.cameraToViewportX(player.getWidth()),
				playerScreenCoordinates.y + mViewTransformer.cameraToViewportY(player.getHeight())
				);
		
		
		
		if(GameGraphicTypes.TYPE_SPRITE.matches(playerGraphic.getGraphicType())) {
			
			ISpriteGraphic spriteGraphic = (ISpriteGraphic) playerGraphic;
			ISprite playerSprite = spriteGraphic.getSprite();

			ISpriteAsset playerSpriteAsset = mGamestate.getGame().getContentManager().getSpriteManager().getSpriteAsset(playerSprite);
			if(playerSpriteAsset == null) {
				// Asset not loaded yet
				return;
			}
			
			boolean assetIsSpecial = mGamestate.getGame().getContentManager().getSpriteManager().assetIsSpecial(playerSpriteAsset);
			if(assetIsSpecial) {
				// Do what?
			}

			ITexture playerTexture = mGamestate.getEngine().getContentService().extractAssetTexture(playerSpriteAsset.getContent());
			if(playerTexture == null) {
				return;	// TODO: Workaround for special assets
			}
			
			
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
			TokTales.getLog().w(TAG, "Cannot draw sprite: Unknown graphic type");
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
		drawPlayer(mGamestate.getActiveScene().getPlayerController());
	}
	
}
