package com.tokelon.toktales.extensions.core.game.renderer;

import com.tokelon.toktales.core.content.graphics.IRGBAColor;
import com.tokelon.toktales.core.content.graphics.RGBAColor;
import com.tokelon.toktales.core.content.manage.codepoint.ICodepointAssetManager;
import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.core.engine.render.IRenderService;
import com.tokelon.toktales.core.game.state.GameStateSuppliers;
import com.tokelon.toktales.core.game.state.IGameState;
import com.tokelon.toktales.core.render.renderer.AbstractRenderer;
import com.tokelon.toktales.core.render.renderer.ISingleRenderer;
import com.tokelon.toktales.core.render.texture.ITextureCoordinator;
import com.tokelon.toktales.extensions.core.game.controller.ITextBoxController;
import com.tokelon.toktales.extensions.core.game.model.ITextBox;
import com.tokelon.toktales.extensions.core.render.TextRenderer;
import com.tokelon.toktales.extensions.core.values.ControllerExtensionsValues;
import com.tokelon.toktales.tools.core.inject.ISupplier;

public class TextBoxRenderer extends AbstractRenderer implements ISingleRenderer {


	private IRGBAColor color = RGBAColor.createFromCode("FFF");
	
	private TextRenderer textRenderer;

	
	private final ILogger logger;
	private final IRenderService renderService;
	private final ICodepointAssetManager codepointAssetManager;
	private final ISupplier<ITextureCoordinator> textureCoordinatorSupplier;
	private final ISupplier<ITextBoxController> textBoxControllerSupplier;
	
	public TextBoxRenderer(
			ILogging logging,
			IEngine engine,
			ICodepointAssetManager codepointAssetManager,
			ISupplier<ITextureCoordinator> textureCoordinatorSupplier,
			ISupplier<ITextBoxController> textBoxControllerSupplier
	) {
		this.logger = logging.getLogger(getClass());
		this.renderService = engine.getRenderService();
		this.codepointAssetManager = codepointAssetManager;
		this.textureCoordinatorSupplier = textureCoordinatorSupplier;
		this.textBoxControllerSupplier = textBoxControllerSupplier;
	}

	
	public void setColor(IRGBAColor color) {
		this.color = color;
	}
	
	public IRGBAColor getColor() {
		return color;
	}

	

	@Override
	protected void onContextCreated() {
		textRenderer = new TextRenderer(renderService.getRenderAccess(), codepointAssetManager, textureCoordinatorSupplier.get());
		textRenderer.contextCreated();
	}

	@Override
	protected void onContextChanged() {
		textRenderer.contextChanged(getViewTransformer(), getMatrixProjection());		
	}

	@Override
	protected void onContextDestroyed() {
		textRenderer.contextDestroyed();		
	}
	

	@Override
	public void renderContents() {
		ITextBoxController textBoxController = textBoxControllerSupplier.get();
		if(textBoxController == null) {
			logger.info("Draw was called but no text box is available");
			return;
		}
		
		ITextBox textBox = textBoxController.getModel();
		
		textRenderer.drawTextBox(textBox, color);
	}
	
	
	public static class TextBoxRendererFactory {
		
		public TextBoxRenderer create(
				IEngineContext engineContext,
				ISupplier<ITextureCoordinator> textureCoordinatorSupplier,
				ISupplier<ITextBoxController> textBoxControllerSupplier
		) {
			return new TextBoxRenderer(
					engineContext.getLogging(),
					engineContext.getEngine(),
					engineContext.getGame().getContentManager().getCodepointAssetManager(),
					textureCoordinatorSupplier,
					textBoxControllerSupplier
			);
		}
		
		public TextBoxRenderer createForGamestate(IGameState gamestate) {
			return new TextBoxRenderer(
					gamestate.getLogging(),
					gamestate.getEngine(),
					gamestate.getGame().getContentManager().getCodepointAssetManager(),
					() -> gamestate.getStateRenderer().getTextureCoordinator(),
					GameStateSuppliers.ofControllerFromManager(gamestate, ControllerExtensionsValues.CONTROLLER_TEXTBOX, ITextBoxController.class)
			);
		}
		
		public TextBoxRenderer createForGamestate(IGameState gamestate, ISupplier<ITextBoxController> textBoxControllerSupplier) {
			return new TextBoxRenderer(
					gamestate.getLogging(),
					gamestate.getEngine(),
					gamestate.getGame().getContentManager().getCodepointAssetManager(),
					() -> gamestate.getStateRenderer().getTextureCoordinator(),
					textBoxControllerSupplier
			);
		}
	}

}
