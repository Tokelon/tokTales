package com.tokelon.toktales.extens.def.core.game.screen;

import java.util.Set;

import com.tokelon.toktales.core.content.RGBAColorImpl;
import com.tokelon.toktales.core.engine.render.IRenderAccess;
import com.tokelon.toktales.core.game.controller.IPlayerController;
import com.tokelon.toktales.core.game.model.IPlayer;
import com.tokelon.toktales.core.game.model.Rectangle2fImpl;
import com.tokelon.toktales.core.game.model.entity.IGameEntity;
import com.tokelon.toktales.core.game.screen.view.DefaultViewGridTransformer;
import com.tokelon.toktales.core.game.states.IExtendedGameScene;
import com.tokelon.toktales.core.game.states.IGameScene;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.game.world.IWorldGrid;
import com.tokelon.toktales.core.game.world.IWorldspace;
import com.tokelon.toktales.core.render.AbstractRenderer;
import com.tokelon.toktales.core.render.ShapeRenderer;
import com.tokelon.toktales.core.render.model.ILineModel;
import com.tokelon.toktales.core.render.model.IRectangleModel;
import com.tokelon.toktales.core.render.shapes.LineShape;
import com.tokelon.toktales.core.util.INamedOptions;
import com.tokelon.toktales.core.values.ControllerValues;

public class DebugRenderer extends AbstractRenderer implements IDebugRenderer {
	

	/**
	 * TODO: Move other renderers into extensions/
	 * And anything that is in predef or buildin
	 */
	
	private static final RGBAColorImpl gridColor = RGBAColorImpl.createFromCode("FF0000", 0.5f);
	private static final RGBAColorImpl cameraOriginColor = RGBAColorImpl.createFromCode("00FF00");
	private static final RGBAColorImpl playerCollisionBoxColor = RGBAColorImpl.createFromCode("42F4F4");
	private static final RGBAColorImpl entityCollisionBoxColor = RGBAColorImpl.createFromCode("F28D44");
	
	private static final float gridLineWidth = 1.0f;

	
	private final Rectangle2fImpl playerCollisionBounds = new Rectangle2fImpl();
	private final Rectangle2fImpl playerCollisionCameraBounds = new Rectangle2fImpl();
	
	private final Rectangle2fImpl entityCollisionBounds = new Rectangle2fImpl();
	private final Rectangle2fImpl entityCollisionCameraBounds = new Rectangle2fImpl();
	
	private final LineShape gridLine = new LineShape();
	
	private DefaultViewGridTransformer gridTransformer;

	private final ShapeRenderer shapeRenderer;

	
	private final IGameState gamestate;
	
	
	public DebugRenderer(IGameState gamestate) {
		this.gamestate = gamestate;
		
		IRenderAccess renderAccess = gamestate.getEngine().getRenderService().getRenderAccess();
		shapeRenderer = new ShapeRenderer(renderAccess);
	}
	
	
	@Override
	public void prepare(long currentTimeMillis) {
		// if !view
	}

	@Override
	public void drawLayer(INamedOptions options, String layerName) {
		assert false : "Not supported";
	}

	@Override
	public void drawFull(INamedOptions options) {
		IGameScene gamescene = gamestate.getActiveScene();
		IPlayerController playerController = gamescene.getControllerManager().getControllerAs(ControllerValues.CONTROLLER_PLAYER, IPlayerController.class);

		IWorldspace worldspace = null;
		if(gamescene instanceof IExtendedGameScene) {
			worldspace = ((IExtendedGameScene) gamescene).getWorldspace();
		}
		
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
		drawPlayerCollisionBox(playerController);
		drawEntitiesCollisionBoxes(worldspace);
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
		
		IPlayer player = playerController.getPlayer();
		
		
		getViewTransformer().getCurrentCamera().worldToCamera(player.getCollisionBounds(playerCollisionBounds), playerCollisionCameraBounds);
		
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
	public void drawEntitiesCollisionBoxes(IWorldspace worldspace) {
		
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
	protected void onContextCreated() {
		shapeRenderer.contextCreated();
		
	}

	@Override
	protected void onContextChanged() {
		gridTransformer = new DefaultViewGridTransformer(gamestate.getGame().getWorld().getGrid(), getViewTransformer());
		
		shapeRenderer.contextChanged(getViewTransformer(), getMatrixProjection());
	}
	
	@Override
	protected void onContextDestroyed() {
		shapeRenderer.contextDestroyed();
		
		gridTransformer = null;
	}
	

}
