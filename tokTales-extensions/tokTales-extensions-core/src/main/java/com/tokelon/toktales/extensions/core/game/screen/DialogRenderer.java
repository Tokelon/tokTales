package com.tokelon.toktales.extensions.core.game.screen;

import com.tokelon.toktales.core.content.manage.codepoint.ICodepointAssetManager;
import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.core.engine.render.IRenderService;
import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.states.GameStateSuppliers;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.render.model.IRectangleModel;
import com.tokelon.toktales.core.render.renderer.AbstractRenderer;
import com.tokelon.toktales.core.render.renderer.ShapeRenderer;
import com.tokelon.toktales.core.render.shapes.RectangleShape;
import com.tokelon.toktales.core.render.texture.ITextureCoordinator;
import com.tokelon.toktales.extensions.core.game.controller.IDialogController;
import com.tokelon.toktales.extensions.core.game.model.IScreenDialog;
import com.tokelon.toktales.extensions.core.render.TextRenderer;
import com.tokelon.toktales.extensions.core.values.ControllerExtensionsValues;
import com.tokelon.toktales.tools.core.inject.ISupplier;
import com.tokelon.toktales.tools.core.objects.options.INamedOptions;

public class DialogRenderer extends AbstractRenderer implements IDialogRenderer {


	private TextRenderer textRenderer;
	private ShapeRenderer shapeRenderer;
	
	private final RectangleShape border;
	private final RectangleShape background;
	
	
	private final ILogger logger;
	private final IRenderService renderService;
	private final ICodepointAssetManager codepointAssetManager;
	private final ISupplier<ITextureCoordinator> textureCoordinatorSupplier;
	private final ISupplier<IDialogController> dialogControllerSupplier;
	
	public DialogRenderer(
			ILogging logging,
			IEngine engine,
			ICodepointAssetManager codepointAssetManager,
			ISupplier<ITextureCoordinator> textureCoordinatorSupplier,
			ISupplier<IDialogController> dialogControllerSupplier
	) {
		this.logger = logging.getLogger(getClass());
		this.renderService = engine.getRenderService();
		this.codepointAssetManager = codepointAssetManager;
		this.textureCoordinatorSupplier = textureCoordinatorSupplier;
		this.dialogControllerSupplier = dialogControllerSupplier;
		
		
		this.border = new RectangleShape();
		this.background = new RectangleShape();
	}
	
	
	
	@Override
	protected void onContextCreated() {
		textRenderer = new TextRenderer(renderService.getRenderAccess(), codepointAssetManager, textureCoordinatorSupplier.get());
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
			logger.info("Draw was called but no dialog is available");
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
				ISupplier<ITextureCoordinator> textureCoordinatorSupplier,
				ISupplier<IDialogController> dialogControllerSupplier
		) {
			return new DialogRenderer(
					engineContext.getLogging(),
					engineContext.getEngine(),
					engineContext.getGame().getContentManager().getCodepointAssetManager(),
					textureCoordinatorSupplier,
					dialogControllerSupplier
			);
		}
		
		
		@Override
		public DialogRenderer createForGamestate(IGameState gamestate) {
			return new DialogRenderer(
					gamestate.getLogging(),
					gamestate.getEngine(),
					gamestate.getGame().getContentManager().getCodepointAssetManager(),
					() -> gamestate.getStateRender().getTextureCoordinator(),
					GameStateSuppliers.ofControllerFromManager(gamestate, ControllerExtensionsValues.CONTROLLER_DIALOG, IDialogController.class)
			);
		}
		
		@Override
		public DialogRenderer createForGamestate(IGameState gamestate, ISupplier<IDialogController> dialogControllerSupplier) {
			return new DialogRenderer(
					gamestate.getLogging(),
					gamestate.getEngine(),
					gamestate.getGame().getContentManager().getCodepointAssetManager(),
					() -> gamestate.getStateRender().getTextureCoordinator(),
					dialogControllerSupplier
			);
		}
	}
	
}
