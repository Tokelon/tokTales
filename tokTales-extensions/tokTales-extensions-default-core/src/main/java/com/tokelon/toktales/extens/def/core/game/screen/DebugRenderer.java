package com.tokelon.toktales.extens.def.core.game.screen;

import java.util.Set;

import javax.inject.Inject;

import com.tokelon.toktales.core.content.IContentManager;
import com.tokelon.toktales.core.content.graphics.RGBAColorImpl;
import com.tokelon.toktales.core.content.manage.font.ITextureFontAsset;
import com.tokelon.toktales.core.content.manage.font.ITextureFontAssetKey;
import com.tokelon.toktales.core.content.text.ITextureFont;
import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.inject.annotation.GlobalAssetKeyRegistry;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.render.IRenderAccess;
import com.tokelon.toktales.core.game.IGame;
import com.tokelon.toktales.core.game.controller.IPlayerController;
import com.tokelon.toktales.core.game.model.IActor;
import com.tokelon.toktales.core.game.model.Point2fImpl;
import com.tokelon.toktales.core.game.model.Rectangle2fImpl;
import com.tokelon.toktales.core.game.model.entity.IGameEntity;
import com.tokelon.toktales.core.game.screen.view.DefaultViewGridTransformer;
import com.tokelon.toktales.core.game.states.GameStateSuppliers;
import com.tokelon.toktales.core.game.states.IExtendedGameScene;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.game.states.ITypedGameState;
import com.tokelon.toktales.core.game.world.IWorldGrid;
import com.tokelon.toktales.core.game.world.IWorldspace;
import com.tokelon.toktales.core.render.AbstractRenderer;
import com.tokelon.toktales.core.render.CharRenderer;
import com.tokelon.toktales.core.render.ITextureCoordinator;
import com.tokelon.toktales.core.render.ShapeRenderer;
import com.tokelon.toktales.core.render.model.ILineModel;
import com.tokelon.toktales.core.render.model.IRectangleModel;
import com.tokelon.toktales.core.render.shapes.LineShape;
import com.tokelon.toktales.core.util.FrameTool;
import com.tokelon.toktales.core.util.function.Supplier;
import com.tokelon.toktales.core.util.options.INamedOptions;
import com.tokelon.toktales.tools.registry.IBasicRegistry;

public class DebugRenderer extends AbstractRenderer implements IDebugRenderer {
	
	public static final String TAG = "DebugRenderer";
	
	public static final String ASSET_KEY_ID_FONT_MAIN = "DEBUG_RENDERER-ASSET_KEY_ID_FONT_MAIN";

	
	private static final RGBAColorImpl gridColor = RGBAColorImpl.createFromCode("FF0000", 0.5f);
	private static final RGBAColorImpl cameraOriginColor = RGBAColorImpl.createFromCode("00FF00");
	private static final RGBAColorImpl playerCollisionBoxColor = RGBAColorImpl.createFromCode("42F4F4");
	private static final RGBAColorImpl entityCollisionBoxColor = RGBAColorImpl.createFromCode("F28D44");
	private static final RGBAColorImpl frameInfoColor = RGBAColorImpl.createFromCode("00FF00");
	private static final RGBAColorImpl flashColor = RGBAColorImpl.createFromCode("FF0000A0");

	private static final float gridLineWidth = 1.0f;
	private static final float fontSize = 8f;
	private static final float charDistance = 0f;
	private static final float lineDistance = 2f;

	
	private final Rectangle2fImpl playerCollisionBounds = new Rectangle2fImpl();
	private final Rectangle2fImpl playerCollisionCameraBounds = new Rectangle2fImpl();
	
	private final Rectangle2fImpl entityCollisionBounds = new Rectangle2fImpl();
	private final Rectangle2fImpl entityCollisionCameraBounds = new Rectangle2fImpl();
	
	private final Point2fImpl entityRawCoordinates = new Point2fImpl();
	private final Point2fImpl entityRawCameraCoordinates = new Point2fImpl();
	
	private final LineShape gridLine = new LineShape();
	
	
	// Flashes the screen red if the update delta is high
	private boolean highUpdateDeltaFlashEnabled = false;
	
	private DefaultViewGridTransformer gridTransformer;

	private final FrameTool frameTool = new FrameTool();
	private int lastFps;
	private long lastFpsTime;
	private long lastGameTime;
	private long lastDelta;
	private long lastGameDelta;
	private int flashCountdown = 0;

	
	private ShapeRenderer shapeRenderer;
	private CharRenderer charRenderer;

	
	private final ILogger logger;
	private final IEngine engine;
	private final IGame game;
	private final IContentManager contentManager;
	private final IBasicRegistry assetKeyRegistry;
	private final Supplier<ITextureCoordinator> textureCoordinatorSupplier;
	private final Supplier<IPlayerController> playerControllerSupplier;
	private final Supplier<IWorldspace> worlspaceSupplier;
	
	public DebugRenderer(
			IEngineContext engineContext,
			IContentManager contentManager,
			IBasicRegistry assetKeyRegistry,
			Supplier<ITextureCoordinator> textureCoordinatorSupplier,
			Supplier<IPlayerController> playerControllerSupplier,
			Supplier<IWorldspace> worlspaceSupplier
	) {
		
		this.logger = engineContext.getLog();
		this.engine = engineContext.getEngine();
		this.game = engineContext.getGame();
		this.contentManager = contentManager;
		this.assetKeyRegistry = assetKeyRegistry;
		this.textureCoordinatorSupplier = textureCoordinatorSupplier;
		this.playerControllerSupplier = playerControllerSupplier;
		this.worlspaceSupplier = worlspaceSupplier;
		
		lastFpsTime = System.currentTimeMillis();
		lastGameTime = engineContext.getGame().getTimeManager().getGameTimeMillis();
	}
	
	
	@Override
	protected void onContextCreated() {
		IRenderAccess renderAccess = engine.getRenderService().getRenderAccess();
		
		shapeRenderer = new ShapeRenderer(renderAccess);
		shapeRenderer.contextCreated();
		
		charRenderer = new CharRenderer(renderAccess, textureCoordinatorSupplier.get(), contentManager.getCodepointAssetManager());
		charRenderer.contextCreated();
	}

	@Override
	protected void onContextChanged() {
		gridTransformer = new DefaultViewGridTransformer(game.getWorld().getGrid(), getViewTransformer());
		
		shapeRenderer.contextChanged(getViewTransformer(), getMatrixProjection());
		charRenderer.contextChanged(getViewTransformer(), getMatrixProjection());
	}
	
	@Override
	protected void onContextDestroyed() {
		shapeRenderer.contextDestroyed();
		charRenderer.contextDestroyed();
		
		gridTransformer = null;
	}
	
	
	@Override
	public void prepare(long currentTimeMillis) {
		// if !view
		charRenderer.setFont(getFont());
		charRenderer.setSize(fontSize, fontSize);
	}

	@Override
	public void drawLayer(INamedOptions options, String layerName) {
		assert false : "Not supported";
	}

	@Override
	public void drawFull(INamedOptions options) {
		IPlayerController playerController = playerControllerSupplier.get();
		IWorldspace worldspace = worlspaceSupplier.get();

		
		drawDebug(playerController, worldspace);
	}
	
	
	@Override
	public void drawDebug(IPlayerController playerController, IWorldspace worldspace) {
		if(!hasView()) {
			assert false : "Cannot draw without view";
			return;
		}
		
		drawGrid();
		drawCameraOrigin();
		if(playerController != null) {
			drawPlayerCollisionBox(playerController);	
		}
		if(worldspace != null) {
			drawEntityCollisionBoxes(worldspace);
			drawEntityDebugInfos(worldspace);	
		}
		drawFrameInfo();
	}
	
	
	@Override
	public void drawFrameInfo()	{
		if(!hasView()) {
			assert false : "Cannot draw without view";
			return;
		}
		int fpsDraw = lastFps;
		long deltaDraw = lastDelta, gameDeltaDraw = lastGameDelta;
		
		int fps = frameTool.countFps();
		if(fps != -1) {
			lastFps = fps;
			fpsDraw = fps;
		}
		
		
		long delta = System.currentTimeMillis() - lastFpsTime;
		lastFpsTime = System.currentTimeMillis();
		if(Math.abs(delta - lastDelta) > 2) {
			lastDelta = delta;
			deltaDraw = delta;
		}
		
		
		long gameDelta = game.getTimeManager().getGameTimeMillis() - lastGameTime;
		lastGameTime = game.getTimeManager().getGameTimeMillis();
		if(Math.abs(gameDelta - lastGameDelta) > 2) {
			lastGameDelta = gameDelta;
			gameDeltaDraw = gameDelta;
		}
		
		
		float x = 0f, y = 0f;
		drawText("FPS: " + fpsDraw, x, y, fontSize, charDistance);
		y += fontSize + lineDistance;
		drawText("MS DT: " + deltaDraw, x, y, fontSize, charDistance);
		y += fontSize + lineDistance;
		drawText("GM DT: " + gameDeltaDraw, x, y, fontSize, charDistance);
		
		
		if(highUpdateDeltaFlashEnabled) {
			if(delta > 20) {
				flashCountdown = 15;
			}
			
			if(flashCountdown > 0) {
				float width = getViewTransformer().getCurrentViewport().getWidth();
				float height = getViewTransformer().getCurrentViewport().getHeight();

				shapeRenderer.setColor(flashColor);
				shapeRenderer.setFill(true);
				shapeRenderer.drawRectangle(0f, 0f, width, height);
				shapeRenderer.setFill(false);
				flashCountdown--;
			}
		}
		
	}
	
	private void drawText(String text, float posx, float posy, float fontSize, float charDistance) {
		float x = posx, y = posy;
		charRenderer.setColor(frameInfoColor);
		charRenderer.startBatchDraw();
		for(char c: text.toCharArray()) {
			charRenderer.setPosition(x, y);
			charRenderer.drawChar(c);
			
			x += fontSize + charDistance;
		}
		charRenderer.finishBatchDraw();
	}
	
	
	@Override
	public void drawGrid() {
		if(!hasView()) {
			assert false : "Cannot draw without view";
			return;
		}
		
		IWorldGrid grid = gridTransformer.getGrid();
		float tileSize = grid.getTileSize();
		
		float lineLengthV = getViewTransformer().getCurrentViewport().getHeight();
		float lineLengthH = getViewTransformer().getCurrentViewport().getWidth();

		shapeRenderer.setColor(gridColor);
		shapeRenderer.setLineAlignment(ILineModel.ALIGNMENT_INNER);
		
		// Horizontal lines
		gridLine.setPosition(0.0f, - gridTransformer.getCameraShiftY());
		gridLine.setShape(0.0f, 0.0f, lineLengthH, 0.0f);
		gridLine.setWidth(gridLineWidth);
		
		while(gridLine.getPositionY() < getViewTransformer().getCurrentViewport().getHeight()) {
			shapeRenderer.drawLineShape(gridLine);
			
			gridLine.setRelativePosition(0.0f, tileSize);
		}
		
		// Vertical lines
		gridLine.setPosition(- gridTransformer.getCameraShiftX(), 0.0f);
		gridLine.setShape(0.0f, 0.0f, 0.0f, lineLengthV);
		
		while(gridLine.getPositionX() < getViewTransformer().getCurrentViewport().getWidth()) {
			shapeRenderer.drawLineShape(gridLine);
			
			gridLine.setRelativePosition(tileSize, 0.0f);
		}
		
	}

	
	@Override
	public void drawCameraOrigin() {
		if(!hasView()) {
			assert false : "Cannot draw without view";
			return;
		}
		
		float wx = getViewTransformer().getCurrentCamera().worldToCameraX(getViewTransformer().getCurrentCamera().getWorldX());
		float wy = getViewTransformer().getCurrentCamera().worldToCameraY(getViewTransformer().getCurrentCamera().getWorldY());
		
		shapeRenderer.setColor(cameraOriginColor);
		shapeRenderer.drawPoint(wx, wy, 2f);
	}
	
	
	@Override
	public void drawPlayerCollisionBox(IPlayerController playerController) {
		if(!hasView()) {
			assert false : "Cannot draw without view";
			return;
		}
		
		IActor playerActor = playerController.getPlayer().getActor();
		
		
		getViewTransformer().getCurrentCamera().worldToCamera(playerActor.getCollisionBounds(playerCollisionBounds), playerCollisionCameraBounds);
		
		shapeRenderer.setColor(playerCollisionBoxColor);
		shapeRenderer.setFill(false);
		shapeRenderer.setOutlineType(IRectangleModel.OUTLINE_TYPE_INNER);
		shapeRenderer.setOutlineWidth(1f);
		shapeRenderer.drawRectangle(
				playerCollisionCameraBounds.left(), playerCollisionCameraBounds.top(),
				playerCollisionCameraBounds.left(), playerCollisionCameraBounds.bottom(),
				playerCollisionCameraBounds.right(), playerCollisionCameraBounds.bottom(),
				playerCollisionCameraBounds.right(), playerCollisionCameraBounds.top()
		);
		
	}
	
	
	@Override
	public void drawEntityCollisionBoxes(IWorldspace worldspace) {
		
		Set<String> entityIDSet = worldspace.getEntityIDSet();
		for(String entityID: entityIDSet) {
			
			IGameEntity entity = worldspace.getEntity(entityID);

			if(!entity.isActive()) { //!entity.isVisible()
				continue;
			}
			
			getViewTransformer().getCurrentCamera().worldToCamera(entity.getCollisionBounds(entityCollisionBounds), entityCollisionCameraBounds);
			
			shapeRenderer.setColor(entityCollisionBoxColor);
			shapeRenderer.setFill(false);
			shapeRenderer.setOutlineType(IRectangleModel.OUTLINE_TYPE_INNER);
			shapeRenderer.setOutlineWidth(1f);
			shapeRenderer.drawRectangle(
					entityCollisionCameraBounds.left(), entityCollisionCameraBounds.top(),
					entityCollisionCameraBounds.left(), entityCollisionCameraBounds.bottom(),
					entityCollisionCameraBounds.right(), entityCollisionCameraBounds.bottom(),
					entityCollisionCameraBounds.right(), entityCollisionCameraBounds.top()
			);
		}
		
	}
	
	@Override
	public void drawEntityDebugInfos(IWorldspace worldspace) {
		Set<String> entityIDSet = worldspace.getEntityIDSet();
		for(String entityID: entityIDSet) {
			
			IGameEntity entity = worldspace.getEntity(entityID);
			
			if(!entity.isActive()) { //!entity.isVisible()
				continue;
			}
			

			getViewTransformer().getCurrentCamera().worldToCamera(entity.getCollisionBounds(entityCollisionBounds), entityCollisionCameraBounds);
			String entityRawCoordsText = String.format("[%.2f, %.2f]", entityCollisionBounds.left(), entityCollisionBounds.top());
			
			getViewTransformer().getCurrentCamera().worldToCamera(entity.getRawWorldCoordinates(entityRawCoordinates), entityRawCameraCoordinates);
			float renderx = entityRawCameraCoordinates.x;
			float rendery = entityRawCameraCoordinates.y - 10;
			float textSize = 5f;
			
			charRenderer.setColor(entityCollisionBoxColor);
			drawText(entityRawCoordsText, renderx, rendery, textSize, 0);
		}
	}
	
	
	private ITextureFont getFont() {
		ITextureFontAssetKey fontAssetKey = assetKeyRegistry.resolveAs(ASSET_KEY_ID_FONT_MAIN, ITextureFontAssetKey.class);
		ITextureFontAsset asset = contentManager.getFontAssetManager().getAssetIfKeyValid(fontAssetKey, ASSET_KEY_ID_FONT_MAIN);
		return contentManager.getFontAssetManager().isAssetValid(asset) ? asset.getFont() : null;
	}
	
	
	
	public static class DebugRendererFactory implements IDebugRendererFactory {
		private final IBasicRegistry assetKeyRegistry;

		@Inject
		public DebugRendererFactory(@GlobalAssetKeyRegistry IBasicRegistry assetKeyRegistry) {
			this.assetKeyRegistry = assetKeyRegistry;
		}

		
		@Override
		public DebugRenderer create(
				IEngineContext engineContext,
				IContentManager contentManager,
				IBasicRegistry assetKeyRegistry,
				Supplier<ITextureCoordinator> textureCoordinatorSupplier,
				Supplier<IPlayerController> playerControllerSupplier,
				Supplier<IWorldspace> worlspaceSupplier
		) {
			return new DebugRenderer(engineContext, contentManager, assetKeyRegistry, textureCoordinatorSupplier, playerControllerSupplier, worlspaceSupplier);
		}


		@Override
		public DebugRenderer createForGamestate(IGameState gamestate, Supplier<IWorldspace> worlspaceSupplier) {
			return new DebugRenderer(
					gamestate.getEngineContext(),
					gamestate.getGame().getContentManager(),
					assetKeyRegistry,
					() -> gamestate.getStateRender().getTextureCoordinator(),
					GameStateSuppliers.ofPlayerControllerFromManager(gamestate),
					worlspaceSupplier
			);
		}

		@Override
		public DebugRenderer createForTypedGamestate(ITypedGameState<? extends IExtendedGameScene> typedGamestate) {
			return new DebugRenderer(
					typedGamestate.getEngineContext(),
					typedGamestate.getGame().getContentManager(),
					assetKeyRegistry,
					() -> typedGamestate.getStateRender().getTextureCoordinator(),
					GameStateSuppliers.ofPlayerControllerFromManager(typedGamestate),
					GameStateSuppliers.ofWorldspaceFromGamestate(typedGamestate)
			);
		}
	}
	
}
