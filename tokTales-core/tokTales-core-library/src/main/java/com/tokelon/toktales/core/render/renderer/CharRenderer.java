package com.tokelon.toktales.core.render.renderer;

import javax.inject.Inject;

import org.joml.Vector4f;

import com.tokelon.toktales.core.content.graphics.IRGBAColor;
import com.tokelon.toktales.core.content.manage.codepoint.ICodepointAsset;
import com.tokelon.toktales.core.content.manage.codepoint.ICodepointAssetManager;
import com.tokelon.toktales.core.content.text.ICodepoint;
import com.tokelon.toktales.core.content.text.IFont;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.core.engine.render.IRenderAccess;
import com.tokelon.toktales.core.render.FontTextSizeHelper;
import com.tokelon.toktales.core.render.IRenderDriver;
import com.tokelon.toktales.core.render.RenderException;
import com.tokelon.toktales.core.render.model.FontModel;
import com.tokelon.toktales.core.render.model.IFontModel;
import com.tokelon.toktales.core.render.texture.ITexture;
import com.tokelon.toktales.core.render.texture.ITextureCoordinator;
import com.tokelon.toktales.tools.core.objects.options.NamedOptionsImpl;

public class CharRenderer extends AbstractRenderer implements ICharRenderer {


	private IFont font;
	
	private float positionX;
	private float positionY;
	private float width;
	private float height;
	

	private final FontModel fontModel = new FontModel();
	private final Vector4f colorVector = new Vector4f();
	private final NamedOptionsImpl drawingOptions = new NamedOptionsImpl();

	
	private boolean isInBatchDraw = false;
	
	private IRenderDriver fontDriver;
	
	private final ILogger logger;
	private final IRenderAccess renderAccess;
	private final ICodepointAssetManager codepointManager;

	@Inject
	public CharRenderer(ILogging logging, IRenderAccess renderAccess, ITextureCoordinator textureCoordinator, ICodepointAssetManager codepointManager) {
		this.logger = logging.getLogger(getClass());
		this.renderAccess = renderAccess;
		this.codepointManager = codepointManager;

		fontModel.setTextureCoordinator(textureCoordinator);
		fontModel.setInvertYAxis(true);
	}


	@Override
	protected void onContextCreated() {
		fontDriver = renderAccess.requestDriver(IFontModel.class.getName());
		if(fontDriver == null) {
			throw new RenderException("No render driver found for: " + IFontModel.class.getName());
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
	public void startBatch() {
		fontDriver.use(getMatrixProjectionAndView());
		
		isInBatchDraw = true;
	}
	
	@Override
	public void finishBatch() {
		isInBatchDraw = false;
		
		fontDriver.release();
	}
	
	
	@Override
	public float drawChar(char ch) {
		return drawCodepoint((int) ch);
	}

	
	public float drawCodepoint(int codepoint) {
		if(!hasView()) {
			logger.warnOnce("Cannot draw without view");
			assert false : "Cannot draw without view";
			return 0f;
		}
		
		if(font == null) {
			logger.warnOnce("Cannot draw: no font");
			return 0f;
		}
		
		float textSize = height > width ? height : width;

		
		float textPixelSize = getViewTransformer().cameraToViewportY(textSize);
		float fontHeight = FontTextSizeHelper.getBestFontPixelHeight(font, textPixelSize);
		ICodepointAsset asset = codepointManager.getCodepointAsset(font, codepoint, fontHeight);
		if(!codepointManager.isAssetValid(asset)) {
			return 0f;
		}
		ICodepoint assetCodepoint = asset.getCodepoint();
		
		
		ITexture texture = assetCodepoint.getTexture();
		
		// Calculate texture scaling and translation
		int textureOffsetX = assetCodepoint.getBitmapOffsetX();
		int textureOffsetY = assetCodepoint.getBitmapOffsetY();
		
		float texScale = textSize / assetCodepoint.getFontPixelHeight();
		
		float tileTop = texScale * textureOffsetY;
		float tileLeft = texScale * textureOffsetX;

		// Translate to position
		float posTranslationX = getViewTransformer().cameraToScreenX(positionX);
		float posTranslationY = getViewTransformer().cameraToScreenY(positionY);
		fontModel.setTranslation2D(posTranslationX, posTranslationY);

		// Translate to glyph offset
		float glyphTranslationX = getViewTransformer().cameraToScreenX(tileLeft);
		float glyphTranslationY = getViewTransformer().cameraToScreenY(tileTop);
		fontModel.translate2D(glyphTranslationX, glyphTranslationY);


		float widthScale = assetCodepoint.getPixelWidth() / assetCodepoint.getFontPixelHeight();
		float heightScale = assetCodepoint.getPixelHeight() / assetCodepoint.getFontPixelHeight();
		float worldCharWidth = width * widthScale;
		float worldCharHeight = height * heightScale;
		
		float pixelCharWidth = getViewTransformer().cameraToScreenX(worldCharWidth);
		float pixelCharHeight = getViewTransformer().cameraToScreenX(worldCharHeight);

		fontModel.setScaling2D(pixelCharWidth, pixelCharHeight);

		
		fontModel.setTargetTexture(texture);


		if(isInBatchDraw) {
			fontDriver.draw(fontModel, drawingOptions);
		}
		else {
			fontDriver.drawQuick(getMatrixProjectionAndView(), fontModel, drawingOptions);	
		}
		
		return worldCharWidth;
	}
	
	

	@Override
	public void setFont(IFont font) {
		this.font = font;
	}

	@Override
	public void setColor(IRGBAColor color) {
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
