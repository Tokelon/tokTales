package com.tokelon.toktales.extens.def.core.game.screen;

import org.joml.Matrix4f;
import org.joml.Vector4f;

import com.tokelon.toktales.core.content.sprite.ISprite;
import com.tokelon.toktales.core.content.sprite.ISpriteAsset;
import com.tokelon.toktales.core.content.sprite.ISpriteManager;
import com.tokelon.toktales.core.content.text.ISpriteFont;
import com.tokelon.toktales.core.engine.IEngine;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.game.screen.ISegmentRenderer;
import com.tokelon.toktales.core.game.screen.view.IViewTransformer;
import com.tokelon.toktales.core.render.IKeyedTextureManager;
import com.tokelon.toktales.core.render.IRenderDriver;
import com.tokelon.toktales.core.render.IRenderTexture;
import com.tokelon.toktales.core.render.RenderException;
import com.tokelon.toktales.core.render.model.ISpriteFontModel;
import com.tokelon.toktales.core.render.model.SpriteFontModel;
import com.tokelon.toktales.core.util.INamedOptions;
import com.tokelon.toktales.core.util.NamedOptionsImpl;
import com.tokelon.toktales.extens.def.core.game.model.ISprifoTextBox;

public class SprifoTextRenderer implements ISegmentRenderer {

	
	private final Matrix4f matrixProjection = new Matrix4f();
	private final Matrix4f matrixView = new Matrix4f();
	private final Matrix4f matrixProjectionAndView = new Matrix4f();
	
	private final SpriteFontModel fontModel = new SpriteFontModel();
	
	private final NamedOptionsImpl drawingOptions = new NamedOptionsImpl();

	
	private IKeyedTextureManager<ISprite> mTextureManager;
	private IRenderDriver fontDriver;
	
	
	private IViewTransformer mViewTransformer;
	
	
	private final IEngine engine;
	private final ISpriteManager spriteManager;
	
	private final ISprifoTextBox textBox;
	
	
	public SprifoTextRenderer(IEngineContext engineContext, ISprifoTextBox textBox) {
		this.engine = engineContext.getEngine();
		this.spriteManager = engineContext.getGame().getContentManager().getSpriteManager();
		
		this.textBox = textBox;
		
		fontModel.setInvertYAxis(true);
		fontModel.setTargetColor(new Vector4f(2.0f, 0.9f, 0.6f, 0.0f));
	}
	
	
	
	
	@Override
	public void contextCreated() {
		
		fontDriver = engine.getRenderService().getRenderAccess().requestDriver(ISpriteFontModel.class.getName());
		if(fontDriver == null) {
			throw new RenderException("No render driver found for: " +ISpriteFontModel.class.getName());
		}
		
		fontDriver.create();
		
		
		mTextureManager = engine.getRenderService().getRenderAccess().requestKeyedTextureManager(ISprite.class);
		if(mTextureManager == null) {
			throw new RenderException("No texture manager found");
		}
		
		fontModel.setTextureManager(mTextureManager);
	}
	

	@Override
	public void contextChanged(IViewTransformer viewTransformer, Matrix4f projectionMatrix) {
		this.mViewTransformer = viewTransformer;
		
		
		matrixProjection.set(projectionMatrix);
		
		matrixView.lookAt(
				0.0f, 0.0f, 0.0f,
				0.0f, 0.0f, -1.0f,
				0.0f, 1.0f, 0.0f);
		
		matrixProjection.mul(matrixView, matrixProjectionAndView);
	}
	
	
	@Override
	public void contextDestroyed() {
		if(fontDriver != null) {
			fontDriver.destroy();
			fontDriver = null;
		}
		
		if(mTextureManager != null) {
			mTextureManager.clear();
			mTextureManager = null;
		}
		
		fontModel.setTextureManager(null);
		
		mViewTransformer = null;
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
		if(mViewTransformer == null) {
			assert false : "Cannot draw without view";
			return;
		}
		
		fontDriver.use(matrixProjectionAndView);
		
		
		float charWidth = textBox.getCharWidth();
		float charHeight = textBox.getCharHeight();
		
		float textBoxWidth = textBox.getEntity().getWidth();
		float textBoxHeight = textBox.getEntity().getHeight();
		
		float textBoxPosX = textBox.getEntity().getBoundsBack().left();
		float textBoxPosY = textBox.getEntity().getBoundsBack().top();

		int textBoxWordCount = textBox.getWordCount();
		
		ISpriteFont font = textBox.getFont();
		if(font == null) {
			// Cannot draw without a font
			return;
		}
		
		
		float charWidthPixels = mViewTransformer.cameraToViewportX(charWidth);
		float charHeightPixels = mViewTransformer.cameraToViewportY(charHeight);
		
		fontModel.setScaling2D(charWidthPixels, charHeightPixels);

		
		
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
					
					String currentWord = textBox.getWord(wordIndex);
					int wordCodepointCount = currentWord.codePointCount(0, currentWord.length());

					float wordWidth = wordCodepointCount * charWidth;
					
					
					if(wordWidth <= widthLeft) {	//&& wordWidth <= textBoxWidth	// Add

						for(int i = 0; i < wordCodepointCount + 1; i++) {	// Iterate through the codepoints of the word
							
							int codepoint;
							if(i == wordCodepointCount) {
								codepoint = ' ';
							}
							else {
								codepoint = currentWord.codePointAt(i);
							}

							

							ISprite codepointSprite = font.getSpriteForCodepoint(codepoint);
							if(codepointSprite == null) {
								continue;				// unknown codepoint
							}
							
							ISpriteAsset spriteAsset = spriteManager.getSpriteAsset(codepointSprite);
							if(spriteAsset == null) {
								// Asset has not been loaded yet
								continue;
							}
							
							boolean assetIsSpecial = spriteManager.assetIsSpecial(spriteAsset);
							if(assetIsSpecial) {
								/* TODO: 
								 * FontDriver does not support sprites that are not enclosed
								 * therefor it cannot render the special asset
								 * Fix ?
								 */
								continue;
							}
							
							IRenderTexture spriteTexture = engine.getContentService().extractAssetTexture(spriteAsset.getContent());
							if(spriteTexture == null) {
								// TODO: Implement special asset
								continue;
							}
							

							// Had a bug here where it used the cameraToScreenX() for the Y value
							fontModel.setTranslation2D(mViewTransformer.cameraToScreenX(textBoxPosX + widthIndex), mViewTransformer.cameraToScreenY(textBoxPosY + heightIndex));
							
							// This would only be needed if we were cutting off from chars	(reducing their size)
							//fontModel.setTextureScaling(charWidthPixels / codepointSprite.getSpriteset().getSpriteWidth(), charHeightPixels / codepointSprite.getSpriteset().getSpriteHeight());
							fontModel.setTextureScaling(1.0f, 1.0f);	// Set scaling in case it has been modified before
							
							fontModel.setTarget(codepointSprite, spriteTexture);

							fontDriver.draw(fontModel, drawingOptions);
							
							widthIndex += charWidth;	// Add the width of the char we just renderer to the index
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
		
		
		fontDriver.release();
	}

	
}
