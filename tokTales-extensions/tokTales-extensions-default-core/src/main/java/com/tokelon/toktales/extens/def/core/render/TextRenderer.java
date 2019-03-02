package com.tokelon.toktales.extens.def.core.render;

import org.joml.Vector4f;

import com.tokelon.toktales.core.content.graphics.IRGBAColor;
import com.tokelon.toktales.core.content.text.ITextureFont;
import com.tokelon.toktales.core.engine.render.IRenderAccess;
import com.tokelon.toktales.core.render.AbstractRenderer;
import com.tokelon.toktales.core.render.IRenderDriver;
import com.tokelon.toktales.core.render.ITexture;
import com.tokelon.toktales.core.render.ITextureCoordinator;
import com.tokelon.toktales.core.render.RenderException;
import com.tokelon.toktales.core.render.model.ITextureFontModel;
import com.tokelon.toktales.core.render.model.TextureFontModel;
import com.tokelon.toktales.core.util.NamedOptionsImpl;
import com.tokelon.toktales.extens.def.core.game.model.ITextBox;

public class TextRenderer extends AbstractRenderer implements ITextRenderer {

	
	private final TextureFontModel fontModel = new TextureFontModel();
	private final Vector4f colorVector = new Vector4f();

	private final NamedOptionsImpl drawingOptions = new NamedOptionsImpl();


	private boolean isInBatchDraw = false;

	private IRenderDriver fontDriver;
	
	private final IRenderAccess renderAccess;
	
	
	public TextRenderer(IRenderAccess renderAccess, ITextureCoordinator textureCoordinator) {
		this.renderAccess = renderAccess;
		
		fontModel.setTextureCoordinator(textureCoordinator);
		fontModel.setInvertYAxis(true);
		fontModel.setTargetColor(colorVector);
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
	public void drawTextBox(ITextBox textBox, IRGBAColor color) {
		if(!hasView()) {
			assert false : "Cannot draw without view";
			return;
		}
		
		colorVector.set(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
		
		if(!isInBatchDraw) {
			fontDriver.use(getMatrixProjectionAndView());	
		}
		
		
		float textBoxWidth = textBox.getEntity().getWidth();
		float textBoxHeight = textBox.getEntity().getHeight();
		
		float textBoxPosX = textBox.getEntity().getBoundsBack().left();
		float textBoxPosY = textBox.getEntity().getBoundsBack().top();

		int textBoxWordCount = textBox.getWordCount();
		
		ITextureFont font = textBox.getFont();
		if(font == null) {
			// Cannot draw without a font
			return;
		}
		
		
		
		float charHeight = textBox.getTextSize();
		
		int wordIndex = 0;
		float heightIndex = 0.0f;
		boolean endText = false;
		
		while(!endText) {
			float heightLeft = textBoxHeight - heightIndex;
			
			if(heightLeft < charHeight) {	// If heightleft is not enough to render a char, end the text
				endText = true;
			}
			else {
				float widthIndex = 0.0f;
				boolean endLine = false;
				float widthLeft = textBoxWidth;	// reset the width

				
				while(!endLine) {
					widthLeft = textBoxWidth - widthIndex;
					
					
					if(wordIndex >= textBoxWordCount) {
						endLine = true;
						break;
					}
					
					
					float charSpacing = textBox.getCharSpacing();
					String currentWord = textBox.getWord(wordIndex);
					int wordCodepointCount = currentWord.codePointCount(0, currentWord.length());
					
					float wordWidth = textBox.getWordWidth(wordIndex) + (wordCodepointCount * charSpacing);
					
					
					
					if(wordWidth <= widthLeft) {	//&& wordWidth <= textBoxWidth	// Add

						for(int i = 0; i < wordCodepointCount + 1; i++) {	// Iterate through the codepoints of the word
							
							int codepoint;
							if(i == wordCodepointCount) {
								codepoint = ' ';
								
								widthIndex += textBox.getSpaceWidth();
								continue;
							}
							else {
								codepoint = currentWord.codePointAt(i);
							}

							
							ITexture texture = font.getCodepointTexture(codepoint);
							// Has texture for codepoint?

							
							int textureOffsetX = font.getCodepointBitmapOffsetX(codepoint);
							int textureOffsetY = font.getCodepointBitmapOffsetY(codepoint);
							
							
							float texScale = textBox.getTextSize() / font.getFontPixelHeight();
							
							float tileTop = texScale * textureOffsetY;
							float tileLeft = texScale * textureOffsetX;

							// TODO: Important - BUG here cameraToScreenX for Y ? -> fixed for now but check later
							fontModel.setTranslation2D(getViewTransformer().cameraToScreenX(textBoxPosX + widthIndex), getViewTransformer().cameraToScreenY(textBoxPosY + heightIndex));

							fontModel.translate2D(getViewTransformer().cameraToScreenX(tileLeft), getViewTransformer().cameraToScreenY(tileTop));	// Translate the codepoint glyph to the right y offset

							
							
							float worldCharWidth = textBox.getCharWidth(codepoint);
							float worldCharHeight = textBox.getCharHeight(codepoint);
							
							float pixelCharWidth = getViewTransformer().cameraToScreenX(worldCharWidth);
							float pixelCharHeight = getViewTransformer().cameraToScreenY(worldCharHeight);
							fontModel.setScaling2D(pixelCharWidth, pixelCharHeight);
							
							
							fontModel.setTargetTexture(texture);

							fontDriver.draw(fontModel, drawingOptions);
							
							widthIndex += worldCharWidth + charSpacing;	// Add the width of the char we just renderer to the index
						}
						
						wordIndex++;				// We are finished with this word so take the next one
					}
					else {					// If the word does not fit on this line, end the line
						endLine = true;
					}
				}
				
				heightIndex += charHeight;
			}
		}
		
		if(!isInBatchDraw) {
			fontDriver.release();
		}
	}

}
