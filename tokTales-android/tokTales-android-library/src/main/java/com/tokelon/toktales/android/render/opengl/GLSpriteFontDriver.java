package com.tokelon.toktales.android.render.opengl;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import org.joml.Matrix4f;
import org.joml.Vector2f;

import com.tokelon.toktales.android.render.opengl.program.OpenGLException;
import com.tokelon.toktales.android.render.opengl.program.ShaderProgram;
import com.tokelon.toktales.core.content.IContentUtils;
import com.tokelon.toktales.core.content.sprite.ISprite;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.core.game.model.Rectangle2iImpl;
import com.tokelon.toktales.core.prog.annotation.Experimental;
import com.tokelon.toktales.core.prog.annotation.Unmaintained;
import com.tokelon.toktales.core.render.IRenderDriver;
import com.tokelon.toktales.core.render.IRenderDriverFactory;
import com.tokelon.toktales.core.render.ITexture;
import com.tokelon.toktales.core.render.ITextureCoordinator;
import com.tokelon.toktales.core.render.ITextureManager;
import com.tokelon.toktales.core.render.RenderException;
import com.tokelon.toktales.core.render.model.IRenderModel;
import com.tokelon.toktales.core.render.model.ISpriteFontModel;
import com.tokelon.toktales.core.util.IParams;
import com.tokelon.toktales.core.util.options.INamedOptions;

import android.opengl.GLES20;

@Unmaintained
@Experimental
public class GLSpriteFontDriver implements IRenderDriver {


	private static final String VS_Sprite = 
			"uniform mat4 uMVPMatrix;" +
			"uniform mat4 uModelMatrix;" +
			"attribute vec4 vPosition;" +
			"attribute vec2 a_vTexCoord;" +
			"varying vec2 v_vTexCoord;" +
			"void main() {" +
			"  gl_Position = uMVPMatrix * uModelMatrix * vPosition;" +
			"  v_vTexCoord = a_vTexCoord;" +
			"}";
	
	private static final String FS_Sprite = 
			"precision mediump float;" +
			"varying vec2 v_vTexCoord;" +
			"uniform sampler2D samplerTexture;" +
			"uniform vec4 colorOver;" +
			"void main() {" +
			"  vec4 texCol = texture2D(samplerTexture, v_vTexCoord);" +
			"  gl_FragColor = vec4(colorOver.x, colorOver.y, colorOver.z, texCol.w);" +
			"}";
	

	
	

	private Rectangle2iImpl rectSpriteSourceCoordsStatic = new Rectangle2iImpl();

	private final Map<ISprite, ITexture> textureMap;
	
	
	private ShaderProgram mShader;
	
	private final GLSpriteMesh spriteMesh;
	
	private final ILogger logger;
	private final IContentUtils contentUtils;
	
	@Inject
	public GLSpriteFontDriver(ILogging logging, IContentUtils contentUtils) {
		this.logger = logging.getLogger(getClass());
		this.contentUtils = contentUtils;
		
		textureMap = new HashMap<>();
		

		float[] vertices = new float[]
				{
					0.0f,  1.0f, 0.0f,
					0.0f, 0.0f, 0.0f,
					1.0f, 0.0f, 0.0f,
					1.0f,  1.0f, 0.0f,
				};
		
		
		spriteMesh = new GLSpriteMesh(vertices);
	}
	
	
	@Override
	public void create() {
		
		try {
			mShader = new ShaderProgram();
			
			mShader.createVertexShader(VS_Sprite);
			mShader.createFragmentShader(FS_Sprite);
			
			mShader.link();
			
			
			mShader.createUniform("uMVPMatrix");
			mShader.createUniform("uModelMatrix");
			mShader.createUniform("samplerTexture");
			mShader.createUniform("colorOver");
			
			mShader.createAttribute("vPosition");
			mShader.createAttribute("a_vTexCoord");
		}
		catch (OpenGLException oglex) {
			logger.error("Failed to create shader program:", oglex);
			return;
		}
	}
	
	@Override
	public void destroy() {
		mShader.cleanup();
		mShader = null;
	}


	
	@Override
	public void drawQuick(Matrix4f matrixProjectionView, IRenderModel renderModel, INamedOptions options) {
		use(matrixProjectionView);
		
		draw(renderModel, options);
		
		release();
	}
	
	
	@Override
	public void use(Matrix4f matrixProjectionView) {

		mShader.bind();
		
		mShader.setUniform("uMVPMatrix", matrixProjectionView);
		
		
		FloatBuffer vertexBuffer = spriteMesh.getPositions();

		mShader.setAttribute("vPosition", 3, vertexBuffer);
		
		
		GLES20.glEnableVertexAttribArray(mShader.getAttributeLocation("vPosition"));
		GLES20.glEnableVertexAttribArray(mShader.getAttributeLocation("a_vTexCoord"));

	}


	@Override
	public void draw(IRenderModel renderModel, INamedOptions options) {
		if(!(renderModel instanceof ISpriteFontModel)) {
			throw new RenderException("Unsupported model type: " +renderModel.getClass());
		}
		ISpriteFontModel fontModel = (ISpriteFontModel) renderModel;
		
		
		ISprite fontSprite = fontModel.getTargetSprite();
		ITexture fontTexture = fontModel.getTargetTexture();
		
		ITextureCoordinator textureCoordinator = fontModel.getTextureCoordinator();
		ITextureManager globalTextureManager = textureCoordinator.getTextureManager();
		
		
		ITexture finalTexture = textureMap.get(fontSprite);
		if(finalTexture == null) {
			rectSpriteSourceCoordsStatic.set(
					0,
					0,
					fontSprite.getSpriteset().getSpriteWidth(),
					fontSprite.getSpriteset().getSpriteHeight()
			);

			
			int offHor = fontSprite.getSpriteset().getHorizontalOffsetFor(fontSprite.getSpritesetIndex());
			int offVer = fontSprite.getSpriteset().getVerticalOffsetFor(fontSprite.getSpritesetIndex());


			rectSpriteSourceCoordsStatic.moveBy(offHor, offVer);

			
			ITexture textureRegion = contentUtils.cropTexture(fontTexture, rectSpriteSourceCoordsStatic);
			
			
			textureMap.put(fontSprite, textureRegion);
			finalTexture = textureRegion;
		}
		
		globalTextureManager.loadTexture(finalTexture);
		
		
		
		// TODO: This is not needed anymore - test first then remove
		// We assume we only have one size (max) for our buffer
		float scalingHor = fontSprite.getSpriteset().getSpriteWidth() / (float) finalTexture.getBitmap().getWidth();
		float scalingVer = fontSprite.getSpriteset().getSpriteHeight() / (float) finalTexture.getBitmap().getHeight();

		// TODO: Reset the scaling to the previous after drawing?
		// We apply the scaling on top of the scaling we can have because of rendering only part of a sprite
		fontModel.scaleTexture(scalingHor, scalingVer);


		// And now we adjust the texture translation to the new scaling
		Vector2f texTranslation = fontModel.getTextureTranslation();
		float scaledTranslationX = scalingHor * texTranslation.x;
		float scaledTranslationY = scalingVer * texTranslation.y;
		fontModel.setTextureTranslation(scaledTranslationX, scaledTranslationY);
		
		


		Matrix4f modelMatrix = fontModel.applyModelMatrix();

		float[] textureCoords = fontModel.applyTextureCoordinates();
		FloatBuffer textureCoordinateBuffer = spriteMesh.setTextureCoords(textureCoords);



		int textureIndex = textureCoordinator.bindTexture(finalTexture);

		mShader.setUniform("uModelMatrix", modelMatrix);
		mShader.setUniform("samplerTexture", textureIndex);
		mShader.setUniform("colorOver", fontModel.getTargetColor());
		
		mShader.setAttribute("a_vTexCoord", 2, textureCoordinateBuffer);

		
		GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, spriteMesh.getVertexCount());

	}


	@Override
	public void drawBatch(List<IRenderModel> modelList, INamedOptions options) {
		for(IRenderModel model: modelList) {
			draw(model, options);
		}
	}
	
	
	@Override
	public void release() {

		GLES20.glDisableVertexAttribArray(mShader.getAttributeLocation("vPosition"));
		GLES20.glDisableVertexAttribArray(mShader.getAttributeLocation("a_vTexCoord"));

		mShader.unbind();
	}
	
	
	
	@Override
	public boolean supports(String target) {
		return supportedTarget().equals(target);
	}
	
	private static String supportedTarget() {
		return ISpriteFontModel.class.getName();
	}
	
	
	public static class GLSpriteFontDriverFactory implements IRenderDriverFactory {
		private final Provider<GLSpriteFontDriver> driverProvider;

		@Inject
		public GLSpriteFontDriverFactory(Provider<GLSpriteFontDriver> driverProvider) {
			this.driverProvider = driverProvider;
		}
		
		@Override
		public boolean supports(String target) {
			return supportedTarget().equals(target);
		}

		@Override
		public IRenderDriver newDriver(IParams params) {
			return driverProvider.get();
		}
	}
	
}
