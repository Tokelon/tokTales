package com.tokelon.toktales.extens.def.core.game.screen;

import java.util.function.Supplier;

import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.game.screen.ISegmentRenderer;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.render.ITextureCoordinator;
import com.tokelon.toktales.extens.def.core.game.controller.IDialogController;

public interface IDialogRenderer extends ISegmentRenderer {
	
	public void drawDialog(IDialogController controller);
	//public void drawDialog(IDialogController controller, String layer);
	
	
	public interface IDialogRendererFactory {

		public IDialogRenderer create(
				IEngineContext engineContext,
				Supplier<ITextureCoordinator> textureCoordinatorSupplier, 
				Supplier<IDialogController> dialogControllerSupplier
		);

		public IDialogRenderer createForGamestate(IGameState gamestate);

		public IDialogRenderer createForGamestate(IGameState gamestate, Supplier<IDialogController> dialogControllerSupplier);
	}
	
}
