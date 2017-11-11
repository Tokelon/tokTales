package com.tokelon.toktales.extens.def.core.game.screen;

import com.tokelon.toktales.core.game.controller.IController;
import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.render.AbstractRenderer;
import com.tokelon.toktales.core.render.ShapeRenderer;
import com.tokelon.toktales.core.render.model.IRectangleModel;
import com.tokelon.toktales.core.render.shapes.RectangleShape;
import com.tokelon.toktales.core.util.INamedOptions;
import com.tokelon.toktales.extens.def.core.game.controller.IDialogController;
import com.tokelon.toktales.extens.def.core.game.model.IScreenDialog;
import com.tokelon.toktales.extens.def.core.render.TextRenderer;

public class DialogRenderer extends AbstractRenderer implements IDialogRenderer {

	public static final String DEFAULT_DIALOG_CONTROLLER_NAME = "controller_dialog";
	
	private String dialogControllerName = DEFAULT_DIALOG_CONTROLLER_NAME;
	
	private final TextRenderer textRenderer;
	private final ShapeRenderer shapeRenderer;
	
	private final IGameState gamestate;
	
	private final RectangleShape border;
	private final RectangleShape background;
	
	
	public DialogRenderer(IGameState gamestate) {
		this.gamestate = gamestate;
		
		this.textRenderer = new TextRenderer(gamestate.getEngine().getRenderService().getRenderAccess());
		this.shapeRenderer = new ShapeRenderer(gamestate.getEngine().getRenderService().getRenderAccess());
		
		this.border = new RectangleShape();
		this.background = new RectangleShape();
	}
	
	
	public String getDialogControllerName() {
		return dialogControllerName;
	}
	
	public void setDialogControllerName(String name) {
		this.dialogControllerName = name;
	}
	
	
	@Override
	protected void onContextCreated() {
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
		IController controller = gamestate.getActiveScene().getControllerManager().getController(dialogControllerName);
		if(!(controller instanceof IDialogController)) {
			assert false : "Invalid type for dialog controller";
			return;
		}
		IDialogController dialogController = (IDialogController) controller;
		
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
	
	
}
