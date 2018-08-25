package com.tokelon.toktales.core.render;

import org.joml.Vector4f;

import com.tokelon.toktales.core.content.IRGBAColor;
import com.tokelon.toktales.core.content.text.ITextureFont;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.engine.render.IRenderAccess;
import com.tokelon.toktales.core.render.model.ITextureFontModel;
import com.tokelon.toktales.core.render.model.TextureFontModel;
import com.tokelon.toktales.core.util.NamedOptionsImpl;

public class CharRenderer extends AbstractRenderer implements ICharRenderer {

	public static final String TAG = "CharRenderer";
	
	private ITextureFont font;
	private IRGBAColor color;
	
	private float positionX;
	private float positionY;
	private float width;
	private float height;
	
	

	private final TextureFontModel fontModel = new TextureFontModel();
	
	private final Vector4f colorVector = new Vector4f();
	
	private final NamedOptionsImpl drawingOptions = new NamedOptionsImpl();

	
	private boolean isInBatchDraw = false;
	
	private IRenderDriver fontDriver;
	
	private final IRenderAccess renderAccess;
	
	public CharRenderer(IRenderAccess renderAccess, ITextureCoordinator textureCoordinator) {
		this.renderAccess = renderAccess;
		
		fontModel.setTextureCoordinator(textureCoordinator);
		fontModel.setInvertYAxis(true);
	}
	

	@Override
	protected void onContextCreated() {

		fontDriver = renderAccess.requestDriver(ITextureFontModel.class.getName());
		if(fontDriver == null) {
			throw new RenderException("No render driver found for: " +ITextureFontModel.class.getName());
		}
		
		fontDriver.create();
	}
	
	@Override
	protected void onContextChanged() {
		// Nothing
	}
	
	@Override
	protected void onContextDestroyed() {
		if(fontDriver != null) {
			fontDriver.destroy();
			fontDriver = null;
		}
	}

	
	@Override
	public void startBatchDraw() {
		fontDriver.use(getMatrixProjectionAndView());
		
		isInBatchDraw = true;
	}
	
	@Override
	public void finishBatchDraw() {
		isInBatchDraw = false;
		
		fontDriver.release();
	}
	
	
	@Override
	public void drawChar(char ch) {
		drawCodepoint((int) ch);
	}

	
	public void drawCodepoint(int codepoint) {
		if(!hasView()) {
			assert false : "Cannot draw without view";
			return;
		}
		
		if(font == null) {
			TokTales.getLog().e(TAG, "Cannot draw: no font");
			return;
		}
		
		
		float textSize = height > width ? height : width;
		
		
		ITexture texture = font.getCodepointTexture(codepoint);
		// Has texture for codepoint?
		
		int textureOffsetX = font.getCodepointBitmapOffsetX(codepoint);
		int textureOffsetY = font.getCodepointBitmapOffsetY(codepoint);
		
		
		float texScale = textSize / font.getFontPixelHeight();
		
		float tileTop = texScale * textureOffsetY;
		float tileLeft = texScale * textureOffsetX;

		// Fixed the bug here as well (with the Y value)
		fontModel.setTranslation2D(getViewTransformer().cameraToScreenX(positionX), getViewTransformer().cameraToScreenY(positionY));

		fontModel.translate2D(getViewTransformer().cameraToScreenX(tileLeft), getViewTransformer().cameraToScreenY(tileTop));	// Translate the codepoint glyph to the right y offset

		
		float scale = textSize / (float) font.getFontPixelHeight();
		
		
		float worldCharWidth = font.getCodepointPixelWidth(codepoint) * scale;
		float worldCharHeight = font.getCodepointPixelHeight(codepoint) * scale;
		
		float pixelCharWidth = getViewTransformer().cameraToScreenX(worldCharWidth);
		float pixelCharHeight = getViewTransformer().cameraToScreenY(worldCharHeight);
		fontModel.setScaling2D(pixelCharWidth, pixelCharHeight);
		
		fontModel.setTargetTexture(texture);
		
		if(isInBatchDraw) {
			fontDriver.draw(fontModel, drawingOptions);
		}
		else {
			fontDriver.drawQuick(getMatrixProjectionAndView(), fontModel, drawingOptions);	
		}
		
	}
	

	@Override
	public void setFont(ITextureFont font) {
		this.font = font;
	}

	@Override
	public void setColor(IRGBAColor color) {
		this.color = color;
		this.colorVector.set(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
		this.fontModel.setTargetColor(colorVector);
	}
	

	@Override
	public void setPosition(float wx, float wy) {
		this.positionX = wx;
		this.positionY = wy;
	}

	@Override
	public void setSize(float wWidth, float wHeight) {
		this.width = wWidth;
		this.height = wHeight;
	}
	

}
