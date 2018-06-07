package com.tokelon.toktales.extens.def.core.game.states.localmap;

import com.tokelon.toktales.core.game.controller.IControllerManager;
import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.screen.order.IRenderCallback;
import com.tokelon.toktales.core.game.screen.order.IRenderOrder;
import com.tokelon.toktales.core.game.states.IControlHandler;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.extens.def.core.game.controller.DefaultDialogController;
import com.tokelon.toktales.extens.def.core.game.controller.IDialogController;
import com.tokelon.toktales.extens.def.core.game.model.ScreenDialog;
import com.tokelon.toktales.extens.def.core.game.screen.DialogRenderer;
import com.tokelon.toktales.extens.def.core.game.states.localmap.ILocalMapControlHandler.EmptyLocalMapControlHandler;

public class DialogBaseLocalMapGamescene extends LocalMapGamescene {

	
	private static final double CALLBACK_POSITION_DIALOG = 2d;
	

	
	private DialogRenderCallback dialogRenderCallback;
	
	private IDialogController dialogController;
	
	
	public DialogBaseLocalMapGamescene() {
		this.dialogController = new DefaultDialogController(new ScreenDialog(10, 10, 10));
	}
	
	@Override
	protected void initSceneDependencies(
			IControllerManager defaultControllerManager,
			ICamera defaultCamera,
			IControlHandler defaultControlHandler
	) {
		
		super.initSceneDependencies(defaultControllerManager, defaultCamera, new DialogBaseLocalMapControlHandler());
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

		dialogRenderCallback = new DialogRenderCallback(getGamestate());
	}
	
	
	
	protected class DialogBaseLocalMapControlHandler extends EmptyLocalMapControlHandler {
		@Override
		public boolean handleInteract() {
			boolean previousValue = getDialogController().getDialog().getEntity().isVisible();
			getDialogController().getDialog().getEntity().setVisible(!previousValue);
			return true;
		}
	}
	
	
	private class DialogRenderCallback implements IRenderCallback {

		private static final String DIALOG_RENDERER_NAME = "DialogBaseLocalMapGamescene_DialogRenderer";
		
		private final DialogRenderer dialogRenderer;
		
		public DialogRenderCallback(IGameState gamestate) {
			dialogRenderer = new DialogRenderer(gamestate); // gamestate is not actually needed
			
			// Add to managed renderers
			gamestate.getStateRender().addManagedRenderer(DIALOG_RENDERER_NAME, dialogRenderer);
			
			// Add to render order
			gamestate.getRenderOrder().getStackForLayer(IRenderOrder.LAYER_TOP).addCallbackAt(CALLBACK_POSITION_DIALOG, this);
		}
		
		@Override
		public void renderCall(String layerName, double stackPosition) {
			// This is the render call
			
			dialogRenderer.drawDialog(getDialogController());
		}

		@Override
		public String getDescription() {
			return "renders a dialog";
		}
	}
	
}
