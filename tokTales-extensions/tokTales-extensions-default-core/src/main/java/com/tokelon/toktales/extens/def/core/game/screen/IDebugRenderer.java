package com.tokelon.toktales.extens.def.core.game.screen;

import com.tokelon.toktales.core.content.IContentManager;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.game.controller.IPlayerController;
import com.tokelon.toktales.core.game.screen.ISegmentRenderer;
import com.tokelon.toktales.core.game.states.IExtendedGameScene;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.game.states.ITypedGameState;
import com.tokelon.toktales.core.game.world.IWorldspace;
import com.tokelon.toktales.core.render.ITextureCoordinator;
import com.tokelon.toktales.core.util.function.Supplier;
import com.tokelon.toktales.tools.registry.IBasicRegistry;

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
				IContentManager contentManager,
				IBasicRegistry assetKeyRegistry,
				Supplier<ITextureCoordinator> textureCoordinatorSupplier,
				Supplier<IPlayerController> playerControllerSupplier,
				Supplier<IWorldspace> worlspaceSupplier
		);

		public IDebugRenderer createForGamestate(
				IGameState gamestate,
				Supplier<IWorldspace> worlspaceSupplier
		);

		public IDebugRenderer createForTypedGamestate(
				ITypedGameState<? extends IExtendedGameScene> typedGamestate
		);
	}
	
	// TODO: Use this instead of factory?
	public interface IDebugRendererBuilder {
		// Add getters for values that have setters?
		
		public IDebugRenderer build(
				Supplier<ITextureCoordinator> textureCoordinatorSupplier,
				Supplier<IPlayerController> playerControllerSupplier,
				Supplier<IWorldspace> worlspaceSupplier
		);
		
		public IDebugRenderer build(
				IGameState gamestate,
				Supplier<IWorldspace> worlspaceSupplier
		);
		
		public IDebugRenderer build(
				ITypedGameState<? extends IExtendedGameScene> typedGamestate
		);
		
		
		public IDebugRendererBuilder withEngineContext(IEngineContext engineContext);
		public IDebugRendererBuilder withContentManager(IContentManager contentManager);
		public IDebugRendererBuilder withAssetKeyRegistry(IBasicRegistry assetKeyRegistry);
	}
	
}
