package com.tokelon.toktales.extens.def.core.game.screen;

import com.tokelon.toktales.core.content.graphics.IRGBAColor;
import com.tokelon.toktales.core.content.graphics.RGBAColorImpl;
import com.tokelon.toktales.core.content.manage.codepoint.ICodepointAssetManager;
import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.render.IRenderService;
import com.tokelon.toktales.core.game.screen.ISegmentRenderer;
import com.tokelon.toktales.core.game.states.GameStateSuppliers;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.render.AbstractRenderer;
import com.tokelon.toktales.core.render.ITextureCoordinator;
import com.tokelon.toktales.core.util.function.Supplier;
import com.tokelon.toktales.core.util.options.INamedOptions;
import com.tokelon.toktales.extens.def.core.game.controller.ITextBoxController;
import com.tokelon.toktales.extens.def.core.game.model.ITextBox;
import com.tokelon.toktales.extens.def.core.render.TextRenderer;
import com.tokelon.toktales.extens.def.core.values.ControllerExtensionsValues;

public class TextBoxRenderer extends AbstractRenderer implements ISegmentRenderer {

	public static final String TAG = "TextBoxRenderer";

	
	private IRGBAColor color = RGBAColorImpl.createFromCode("FFF");
	
	private TextRenderer textRenderer;

	
	private final ILogger logger;
	private final IRenderService renderService;
	private final ICodepointAssetManager codepointAssetManager;
	private final Supplier<ITextureCoordinator> textureCoordinatorSupplier;
	private final Supplier<ITextBoxController> textBoxControllerSupplier;
	
	public TextBoxRenderer(
			ILogger logger,
			IEngine engine,
			ICodepointAssetManager codepointAssetManager,
			Supplier<ITextureCoordinator> textureCoordinatorSupplier,
			Supplier<ITextBoxController> textBoxControllerSupplier
	) {
		this.logger = logger;
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
	public void prepare(long currentTimeMillis) {
		// Nothing
	}

	@Override
	public void drawLayer(INamedOptions options, String layerName) {
		// Not supported
	}

	@Override
	public void drawFull(INamedOptions options) {
		ITextBoxController textBoxController = textBoxControllerSupplier.get();
		if(textBoxController == null) {
			logger.i(TAG, "Draw was called but no text box is available");
			return;
		}
		
		ITextBox textBox = textBoxController.getModel();
		
		textRenderer.drawTextBox(textBox, color);
	}
	
	
	public static class TextBoxRendererFactory {
		
		public TextBoxRenderer create(
				IEngineContext engineContext,
				Supplier<ITextureCoordinator> textureCoordinatorSupplier,
				Supplier<ITextBoxController> textBoxControllerSupplier
		) {
			return new TextBoxRenderer(
					engineContext.getLog(),
					engineContext.getEngine(),
					engineContext.getGame().getContentManager().getCodepointAssetManager(),
					textureCoordinatorSupplier,
					textBoxControllerSupplier
			);
		}
		
		public TextBoxRenderer createForGamestate(IGameState gamestate) {
			return new TextBoxRenderer(
					gamestate.getLog(),
					gamestate.getEngine(),
					gamestate.getGame().getContentManager().getCodepointAssetManager(),
					() -> gamestate.getStateRender().getTextureCoordinator(),
					GameStateSuppliers.ofControllerFromManager(gamestate, ControllerExtensionsValues.CONTROLLER_TEXTBOX, ITextBoxController.class)
			);
		}
		
		public TextBoxRenderer createForGamestate(IGameState gamestate, Supplier<ITextBoxController> textBoxControllerSupplier) {
			return new TextBoxRenderer(
					gamestate.getLog(),
					gamestate.getEngine(),
					gamestate.getGame().getContentManager().getCodepointAssetManager(),
					() -> gamestate.getStateRender().getTextureCoordinator(),
					textBoxControllerSupplier
			);
		}
	}

}
