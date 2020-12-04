package com.tokelon.toktales.extensions.core.game.state.localmap;

import javax.inject.Inject;

import com.tokelon.toktales.core.game.controller.ICameraController.ICameraControllerFactory;
import com.tokelon.toktales.core.game.controller.IControllerManager;
import com.tokelon.toktales.core.game.controller.IPlayerController;
import com.tokelon.toktales.core.game.controller.map.IMapController;
import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.model.entity.IGameEntity;
import com.tokelon.toktales.core.game.state.IGameState;
import com.tokelon.toktales.core.game.state.scene.InjectGameScene;
import com.tokelon.toktales.core.game.world.IWorldspace;
import com.tokelon.toktales.core.render.IRenderCall;
import com.tokelon.toktales.core.render.order.IRenderOrder;
import com.tokelon.toktales.extensions.core.game.controller.DefaultDialogController;
import com.tokelon.toktales.extensions.core.game.controller.IDialogController;
import com.tokelon.toktales.extensions.core.game.model.IScreenDialog;
import com.tokelon.toktales.extensions.core.game.model.ScreenDialog;
import com.tokelon.toktales.extensions.core.game.renderer.DialogRenderer;
import com.tokelon.toktales.extensions.core.game.renderer.DialogRenderer.DialogRendererFactory;
import com.tokelon.toktales.extensions.core.game.state.localmap.ILocalMapControlHandler.EmptyLocalMapControlHandler;

public class DialogBaseLocalMapGamescene extends LocalMapGamescene {

	
	private static final double CALLBACK_POSITION_DIALOG = 2d;
	

	
	private DialogRenderCallback dialogRenderCallback;
	
	private IDialogController dialogController;
	
	@Inject
	public DialogBaseLocalMapGamescene() {
		this(null, null, null, null, null, null, null, null, null);
	}
	
	public DialogBaseLocalMapGamescene(IScreenDialog defaultScreenDialog, IDialogController defaultDialogController) {
		this(null, null, null, null, null, null, null, defaultScreenDialog, defaultDialogController);
	}
	
	protected DialogBaseLocalMapGamescene(
			IControllerManager defaultControllerManager,
			ICamera defaultCamera,
			ILocalMapControlHandler defaultControlHandler,
			IWorldspace defaultWorldspace,
			ICameraControllerFactory defaultCameraControllerFactory,
			IPlayerController defaultPlayerController,
			IMapController defaultMapController,
			IScreenDialog defaultScreenDialog,
			IDialogController defaultDialogController
	) {
		super(defaultControllerManager, defaultCamera, defaultControlHandler == null ? new DialogBaseLocalMapControlHandler() : defaultControlHandler,
				defaultWorldspace, defaultCameraControllerFactory, defaultPlayerController, defaultMapController);

		IScreenDialog screenDialog = defaultScreenDialog == null ? new ScreenDialog(10, 10, 10) : defaultScreenDialog;
		this.dialogController = defaultDialogController == null ? new DefaultDialogController(screenDialog) : defaultDialogController;
	}

	
	protected IDialogController getDialogController() {
		return dialogController;
	}
	
	protected void setDialogController(IDialogController dialogController) {
		this.dialogController = dialogController;
	}
	
	
	
	@Override
	public void onAssign() {
		super.onAssign();

		dialogRenderCallback = new DialogRenderCallback(getGamestate(), this);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		dialogRenderCallback.register();
	}
	
	@Override
	public void onStop() {
		super.onStop();
		
		dialogRenderCallback.unregister();
	}
	
	
	
	protected static class DialogBaseLocalMapControlHandler extends EmptyLocalMapControlHandler {
		private DialogBaseLocalMapGamescene gamescene;

		@InjectGameScene
		protected void injectGamescene(DialogBaseLocalMapGamescene gamescene) {
			this.gamescene = gamescene;
		}
		
		@Override
		public boolean handleInteract() {
			IGameEntity dialogEntity = gamescene.getDialogController().getDialog().getEntity();
			
			boolean previousValue = dialogEntity.isVisible();
			dialogEntity.setVisible(!previousValue);
			
			return true;
		}
	}
	
	
	protected static class DialogRenderCallback implements IRenderCall {

		private final DialogRenderer dialogRenderer;

		private final IGameState gamestate;
		private final DialogBaseLocalMapGamescene gamescene;
		
		public DialogRenderCallback(IGameState gamestate, DialogBaseLocalMapGamescene gamescene) {
			this.gamestate = gamestate;
			this.gamescene = gamescene;
			
			dialogRenderer = new DialogRendererFactory().createForGamestate(gamestate);
		}
		
		
		public void register() {
			// Add to managed renderers
			gamestate.getStateRenderer().getContextManager().addContext(dialogRenderer);

			// Add to render order
			gamestate.getStateRenderer().getRenderOrder().getStackForLayer(IRenderOrder.LAYER_TOP).addCallbackAt(CALLBACK_POSITION_DIALOG, this);
		}
		
		public void unregister() {
			gamestate.getStateRenderer().getContextManager().removeContext(dialogRenderer);
			gamestate.getStateRenderer().getRenderOrder().getStackForLayer(IRenderOrder.LAYER_TOP).removeCallbackAt(CALLBACK_POSITION_DIALOG);
		}

		
		@Override
		public void render() {
			// This is the render call
			
			dialogRenderer.drawDialog(gamescene.getDialogController());
		}

		@Override
		public String getDescription() {
			return "Renders a dialog.";
		}
	}
	
}
