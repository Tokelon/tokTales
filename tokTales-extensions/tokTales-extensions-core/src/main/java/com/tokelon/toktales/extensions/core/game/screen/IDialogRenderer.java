package com.tokelon.toktales.extensions.core.game.screen;

import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.game.screen.ISegmentRenderer;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.render.ITextureCoordinator;
import com.tokelon.toktales.extensions.core.game.controller.IDialogController;
import com.tokelon.toktales.tools.core.inject.ISupplier;

public interface IDialogRenderer extends ISegmentRenderer {
	
	public void drawDialog(IDialogController controller);
	//public void drawDialog(IDialogController controller, String layer);
	
	
	public interface IDialogRendererFactory {

		public IDialogRenderer create(
				IEngineContext engineContext,
				ISupplier<ITextureCoordinator> textureCoordinatorSupplier, 
				ISupplier<IDialogController> dialogControllerSupplier
		);

		public IDialogRenderer createForGamestate(IGameState gamestate);

		public IDialogRenderer createForGamestate(IGameState gamestate, ISupplier<IDialogController> dialogControllerSupplier);
	}
	
}