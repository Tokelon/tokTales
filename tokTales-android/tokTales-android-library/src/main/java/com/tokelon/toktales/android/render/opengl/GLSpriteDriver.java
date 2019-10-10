package com.tokelon.toktales.android.render.opengl;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import org.joml.Matrix4f;

import com.tokelon.toktales.android.render.opengl.program.OpenGLException;
import com.tokelon.toktales.android.render.opengl.program.ShaderProgram;
import com.tokelon.toktales.core.content.sprite.ISprite;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.core.render.IRenderDriver;
import com.tokelon.toktales.core.render.IRenderDriverFactory;
import com.tokelon.toktales.core.render.ITexture;
import com.tokelon.toktales.core.render.ITextureCoordinator;
import com.tokelon.toktales.core.render.ITextureManager;
import com.tokelon.toktales.core.render.ITextureRegion;
import com.tokelon.toktales.core.render.RenderException;
import com.tokelon.toktales.core.render.TextureRegion;
import com.tokelon.toktales.core.render.model.IRenderModel;
import com.tokelon.toktales.core.render.model.ISpriteModel;
import com.tokelon.toktales.core.util.IParams;
import com.tokelon.toktales.core.values.RenderDriverOptions;
import com.tokelon.toktales.tools.core.objects.options.INamedOptions;

import android.opengl.GLES20;

public class GLSpriteDriver implements IRenderDriver {


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
			"void main() {" +
			"  gl_FragColor = texture2D(samplerTexture, v_vTexCoord);" +
			"}";
	


	private ShaderProgram mShader;
	
	private GLSpriteMesh spriteMesh;

	private final Map<ISprite, ITextureRegion> textureMap;
	
	private final ILogger logger;
	
	@Inject
	public GLSpriteDriver(ILogging logging) {
		logger = logging.getLogger(getClass());

		textureMap = new HashMap<>(); // Custom load factor etc?
		
		
		// TRIANGLE_FAN
		float[] vertices = new float[]	// THIS is the correct one
				{
					0.0f,  1.0f, 0.0f,
					0.0f, 0.0f, 0.0f,
					1.0f, 0.0f, 0.0f,
					1.0f,  1.0f, 0.0f,
				};
		
		// TRIANGLE_STRIP CCW
		/*
		mFloatArrayVertices = new float[]
				{
					0.0f, 0.0f, 0.0f,
					1.0f, 0.0f, 0.0f,
					0.0f, 1.0f, 0.0f,
					1.0f, 1.0f, 0.0f
				};
		*/
		
		// TRIANGLE_STRIP CW
		/*
		mFloatArrayVertices = new float[]
				{
					0.0f, 0.0f, 0.0f,
					0.0f, 1.0f, 0.0f,
					1.0f, 0.0f, 0.0f,
					1.0f, 1.0f, 0.0f
				};
		*/
		
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
		/* TODO: Check if we can optimize by only passing one single Matrix (modelViewProjection)
		 * instead of both model and projection view
		 * 
		 */
		

		mShader.bind();
		
		mShader.setUniform("uMVPMatrix", matrixProjectionView);
		
		
		FloatBuffer vertexBuffer = spriteMesh.getPositions();

		// 3 values for one point
		mShader.setAttribute("vPosition", 3, vertexBuffer);
		
		
		GLES20.glEnableVertexAttribArray(mShader.getAttributeLocation("vPosition"));
		GLES20.glEnableVertexAttribArray(mShader.getAttributeLocation("a_vTexCoord"));
	}


	@Override
	public void draw(IRenderModel renderModel, INamedOptions options) {
		if(!(renderModel instanceof ISpriteModel)) {
			throw new RenderException("Unsupported model type: " +renderModel.getClass());
		}
		ISpriteModel spriteModel = (ISpriteModel) renderModel;

		boolean ignoreSpriteset = (Boolean) options.getOrError(RenderDriverOptions.DRAWING_OPTION_IGNORE_SPRITESET);

		

		// TODO: Handle the case where the texture is a specialAsset - they have to be invalidated
		ISprite sprite = spriteModel.getTargetSprite();
		ITexture texture = spriteModel.getTargetTexture();
		
		ITextureCoordinator textureCoordinator = spriteModel.getTextureCoordinator();
		ITextureManager globalTextureManager = textureCoordinator.getTextureManager();
		

		// Before draw
		
		ITextureRegion textureRegion = textureMap.get(sprite);
		if(textureRegion == null) {
			if(sprite.isEnclosed() && !ignoreSpriteset) {
				int spriteWidth = sprite.getSpriteset().getSpriteWidth();
				int spriteHeight = sprite.getSpriteset().getSpriteHeight();
				int spriteOffHor = sprite.getSpriteset().getHorizontalOffsetFor(sprite.getSpritesetIndex());
				int spriteOffVer = sprite.getSpriteset().getVerticalOffsetFor(sprite.getSpritesetIndex());

				textureRegion = new TextureRegion(
						texture,
						spriteWidth,
						spriteHeight,
						spriteOffHor,
						spriteOffVer);
			}
			else {
				textureRegion = new TextureRegion(texture);
			}
			
			textureMap.put(sprite, textureRegion);
		}
		
		ITexture finalTexture = textureRegion.getTexture();

		// Make sure the texture is actually loaded
		globalTextureManager.loadTexture(finalTexture);
		

		// 1. Apply translation, rotation and scaling to the model matrix
		Matrix4f modelMatrix = spriteModel.applyModelMatrix();


		// 2. Apply texture coordinates to the float buffer
		float regionScaleX = textureRegion.getTextureWidthScaleFactor();
		float regionScaleY = textureRegion.getTextureHeightScaleFactor();

		// Scale (size) to the target texture region
		spriteModel.scaleTexture(regionScaleX, regionScaleY);

		// Because we scale, we have to adjust the previous translation
		spriteModel.setTextureTranslation(
				spriteModel.getTextureTranslation().x * regionScaleX,
				spriteModel.getTextureTranslation().y * regionScaleY
				);

		// And then do the new translation on top
		spriteModel.translateTexture(
				textureRegion.getTextureXScaleFactor(),
				textureRegion.getTextureYScaleFactor()
				);

		
		float[] textureCoords = spriteModel.applyTextureCoordinates();
		FloatBuffer textureCoordinateBuffer = spriteMesh.setTextureCoords(textureCoords);

		
		int textureIndex = textureCoordinator.bindTexture(finalTexture);

		mShader.setUniform("uModelMatrix", modelMatrix);
		mShader.setUniform("samplerTexture", textureIndex);

		mShader.setAttribute("a_vTexCoord", 2, textureCoordinateBuffer);



		// Draw 2 triangles with 4 points
		// 3 points define the first which is then connected to the 4th point forming another triangle
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
		// After draw

		GLES20.glDisableVertexAttribArray(mShader.getAttributeLocation("vPosition"));
		GLES20.glDisableVertexAttribArray(mShader.getAttributeLocation("a_vTexCoord"));

		mShader.unbind();
	}
	
	
	
	@Override
	public boolean supports(String target) {
		return supportedTarget().equals(target);
	}
	
	private static String supportedTarget() {
		return ISpriteModel.class.getName();
	}
	
	
	public static class GLSpriteDriverFactory implements IRenderDriverFactory {
		private final Provider<GLSpriteDriver> driverProvider;

		@Inject
		public GLSpriteDriverFactory(Provider<GLSpriteDriver> driverProvider) {
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
