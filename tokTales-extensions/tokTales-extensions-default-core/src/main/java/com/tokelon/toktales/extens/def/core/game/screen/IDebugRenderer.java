package com.tokelon.toktales.extens.def.core.game.screen;

import com.tokelon.toktales.core.content.manage.codepoint.ICodepointManager;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.game.controller.IPlayerController;
import com.tokelon.toktales.core.game.screen.ISegmentRenderer;
import com.tokelon.toktales.core.game.states.IExtendedGameScene;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.game.states.ITypedGameState;
import com.tokelon.toktales.core.game.world.IWorldspace;
import com.tokelon.toktales.core.render.ITextureCoordinator;
import com.tokelon.toktales.core.util.function.Supplier;

public interface IDebugRenderer extends ISegmentRenderer {

	
	public void drawDebug(IPlayerController playerController, IWorldspace worldspace);
	
	
	public void drawFrameInfo();
	
	public void drawGrid();
	
	public void drawCameraOrigin();
	
	public void drawPlayerCollisionBox(IPlayerController playerController);
	
	public void drawEntityCollisionBoxes(IWorldspace worldspace);
	
	public void drawEntityDebugInfos(IWorldspace worldspace);

	
	
	public interface IDebugRendererFactory {

		public IDebugRenderer create(
				IEngineContext engineContext,
				ICodepointManager codepointManager,
				Supplier<ITextureCoordinator> textureCoordinatorSupplier,
				Supplier<IPlayerController> playerControllerSupplier,
				Supplier<IWorldspace> worlspaceSupplier
		);

		public IDebugRenderer createForGamestate(
				IGameState gamestate,
				ICodepointManager codepointManager,
				Supplier<IWorldspace> worlspaceSupplier
		);

		public IDebugRenderer createForTypedGamestate(
				ITypedGameState<? extends IExtendedGameScene> typedGamestate,
				ICodepointManager codepointManager
		);
	}
	
}
