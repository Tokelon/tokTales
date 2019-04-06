package com.tokelon.toktales.extens.def.core.game.screen;

import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.render.IRenderService;
import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.states.GameStateSuppliers;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.render.AbstractRenderer;
import com.tokelon.toktales.core.render.ITextureCoordinator;
import com.tokelon.toktales.core.render.ShapeRenderer;
import com.tokelon.toktales.core.render.model.IRectangleModel;
import com.tokelon.toktales.core.render.shapes.RectangleShape;
import com.tokelon.toktales.core.util.function.Supplier;
import com.tokelon.toktales.core.util.options.INamedOptions;
import com.tokelon.toktales.extens.def.core.game.controller.IDialogController;
import com.tokelon.toktales.extens.def.core.game.model.IScreenDialog;
import com.tokelon.toktales.extens.def.core.render.TextRenderer;
import com.tokelon.toktales.extens.def.core.values.ControllerExtensionsValues;

public class DialogRenderer extends AbstractRenderer implements IDialogRenderer {
	
	public static final String TAG = "DialogRenderer";

	
	private TextRenderer textRenderer;
	private ShapeRenderer shapeRenderer;
	
	private final RectangleShape border;
	private final RectangleShape background;
	
	
	private final ILogger logger;
	private final IRenderService renderService;
	private final Supplier<ITextureCoordinator> textureCoordinatorSupplier;
	private final Supplier<IDialogController> dialogControllerSupplier;
	
	public DialogRenderer(
			ILogger logger,
			IEngine engine,
			Supplier<ITextureCoordinator> textureCoordinatorSupplier,
			Supplier<IDialogController> dialogControllerSupplier
	) {
		this.logger = logger;
		this.renderService = engine.getRenderService();
		this.textureCoordinatorSupplier = textureCoordinatorSupplier;
		this.dialogControllerSupplier = dialogControllerSupplier;
		
		
		this.border = new RectangleShape();
		this.background = new RectangleShape();
	}
	
	
	
	@Override
	protected void onContextCreated() {
		textRenderer = new TextRenderer(renderService.getRenderAccess(), textureCoordinatorSupplier.get());
		shapeRenderer = new ShapeRenderer(renderService.getRenderAccess());
		
		textRenderer.contextCreated();
		shapeRenderer.contextCreated();
	}
	
	@Override
	protected void onContextChanged() {
		textRenderer.contextChanged(getViewTransformer(), getMatrixProjection());
		shapeRenderer.contextChanged(getViewTransformer(), getMatrixProjection());
	}
	
	@Override
	protected void onContextDestroyed() {
		textRenderer.contextDestroyed();
		shapeRenderer.contextDestroyed();
	}
	
	
	@Override
	public void prepare(long currentTimeMillis) {
		// if !view
		
		// Nothing yet
	}

	@Override
	public void drawLayer(INamedOptions options, String layerName) {
		assert false : "Not supported";
	}

	@Override
	public void drawFull(INamedOptions options) {
		IDialogController dialogController = dialogControllerSupplier.get();
		if(dialogController == null) {
			logger.i(TAG, "Draw was called but no dialog is available");
			return;
		}
		
		
		drawDialog(dialogController);
	}
	
	@Override
	public void drawDialog(IDialogController dialogController) {
		if(!hasView()) {
			assert false : "Cannot draw without view";
			return;
		}
		
		IScreenDialog dialog = dialogController.getDialog();
		
		// If invisible
		if(!dialog.getEntity().isVisible()) {
			return;
		}
		
		
		ICamera camera = getViewTransformer().getCurrentCamera();
		

		float bottomPadding = 5f;
		float textBoxPaddingHor = 30f;
		float textBoxPaddingVer = 10f;
		float textBoxWidth = dialog.getEntity().getWidth();
		float textBoxHeight = dialog.getEntity().getHeight();
		

		float borderPosx = (camera.getWidth() - textBoxWidth - textBoxPaddingHor) / 2f;
		float borderPosy = (camera.getHeight() - textBoxHeight - textBoxPaddingVer) - bottomPadding;
		
		border.setShape(textBoxWidth, textBoxHeight);
		border.setPosition(borderPosx, borderPosy);
		
		background.setShape(textBoxWidth, textBoxHeight);
		float backgroundPosx = (camera.getWidth() - textBoxWidth - textBoxPaddingHor) / 2f;
		float backgroundPosy = (camera.getHeight() - textBoxHeight - textBoxPaddingVer) - bottomPadding;
		background.setPosition(backgroundPosx, backgroundPosy);

		float textBoxPosx = (camera.getWidth() - textBoxWidth) / 2f;
		float textBoxPosy = (camera.getHeight() - textBoxHeight) - bottomPadding;
		dialog.getEntity().setWorldCoordinates(textBoxPosx, textBoxPosy);
		
		
		shapeRenderer.setFill(true);
		shapeRenderer.setColor(dialog.getBackgroundColor());
		shapeRenderer.drawRectangleShape(background);

		shapeRenderer.setOutlineType(IRectangleModel.OUTLINE_TYPE_OUTER);
		shapeRenderer.setFill(false);
		shapeRenderer.setOutlineWidth(dialog.getBorderSize());
		shapeRenderer.setColor(dialog.getBorderColor());
		shapeRenderer.drawRectangleShape(border);
		
		
		textRenderer.drawTextBox(dialog, dialog.getTextColor());
	}
	
	
	public static class DialogRendererFactory implements IDialogRendererFactory {
		
		@Override
		public DialogRenderer create(
				IEngineContext engineContext,
				Supplier<ITextureCoordinator> textureCoordinatorSupplier,
				Supplier<IDialogController> dialogControllerSupplier
		) {
			return new DialogRenderer(
					engineContext.getLog(),
					engineContext.getEngine(),
					textureCoordinatorSupplier,
					dialogControllerSupplier
			);
		}
		
		
		@Override
		public DialogRenderer createForGamestate(IGameState gamestate) {
			return new DialogRenderer(
					gamestate.getLog(),
					gamestate.getEngine(),
					() -> gamestate.getStateRender().getTextureCoordinator(),
					GameStateSuppliers.ofControllerFromManager(gamestate, ControllerExtensionsValues.CONTROLLER_DIALOG, IDialogController.class)
			);
		}
		
		@Override
		public DialogRenderer createForGamestate(IGameState gamestate, Supplier<IDialogController> dialogControllerSupplier) {
			return new DialogRenderer(
					gamestate.getLog(),
					gamestate.getEngine(),
					() -> gamestate.getStateRender().getTextureCoordinator(),
					dialogControllerSupplier
			);
		}
	}
	
}
