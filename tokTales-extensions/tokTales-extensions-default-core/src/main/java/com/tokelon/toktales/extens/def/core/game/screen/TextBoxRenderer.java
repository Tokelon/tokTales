package com.tokelon.toktales.extens.def.core.game.screen;

import com.tokelon.toktales.core.content.IRGBAColor;
import com.tokelon.toktales.core.content.RGBAColorImpl;
import com.tokelon.toktales.core.game.controller.IController;
import com.tokelon.toktales.core.game.screen.ISegmentRenderer;
import com.tokelon.toktales.core.game.states.IGameState;
import com.tokelon.toktales.core.render.AbstractRenderer;
import com.tokelon.toktales.core.util.INamedOptions;
import com.tokelon.toktales.extens.def.core.game.controller.ITextBoxController;
import com.tokelon.toktales.extens.def.core.game.model.ITextBox;
import com.tokelon.toktales.extens.def.core.render.TextRenderer;

public class TextBoxRenderer extends AbstractRenderer implements ISegmentRenderer {

	public static final String DEFAULT_TEXT_BOX_CONTROLLER_NAME = "controller_text_box";
	
	private String textBoxControllername = DEFAULT_TEXT_BOX_CONTROLLER_NAME;

	
	private IRGBAColor color = RGBAColorImpl.createFromCode("FFF");
	
	private TextRenderer textRenderer;
	
	
	private final IGameState gamestate;

	public TextBoxRenderer(IGameState gamestate) {
		this.gamestate = gamestate;
	}

	
	public void setColor(IRGBAColor color) {
		this.color = color;
	}
	
	public IRGBAColor getColor() {
		return color;
	}
	
	
	public void setTextBoxControllerName(String name) {
		this.textBoxControllername = name;
	}
	
	public String getTextBoxControllerName() {
		return textBoxControllername;
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

		IController controller = gamestate.getActiveScene().getControllerManager().getController(textBoxControllername);
		if(!(controller instanceof ITextBoxController)) {
			assert false : "Invalid type for text box controller";
			return;
		}
		ITextBoxController textBoxController = (ITextBoxController) controller;
		ITextBox textBox = textBoxController.getModel();
		
		
		textRenderer.drawTextBox(textBox, color);
	}

	
	@Override
	protected void onContextCreated() {
		textRenderer = new TextRenderer(gamestate.getEngine().getRenderService().getRenderAccess(), gamestate.getStateRender().getTextureCoordinator());
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
	
}
