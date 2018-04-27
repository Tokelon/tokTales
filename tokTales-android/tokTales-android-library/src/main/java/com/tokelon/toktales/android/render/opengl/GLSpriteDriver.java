package com.tokelon.toktales.android.render.opengl;

import java.nio.FloatBuffer;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector2f;

import com.tokelon.toktales.android.data.AndroidContentService;
import com.tokelon.toktales.android.render.opengl.program.OpenGLException;
import com.tokelon.toktales.android.render.opengl.program.ShaderProgram;
import com.tokelon.toktales.core.content.sprite.ISprite;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.game.model.Rectangle2iImpl;
import com.tokelon.toktales.core.render.IKeyedTextureManager;
import com.tokelon.toktales.core.render.IRenderDriver;
import com.tokelon.toktales.core.render.IRenderDriverFactory;
import com.tokelon.toktales.core.render.IRenderTexture;
import com.tokelon.toktales.core.render.RenderException;
import com.tokelon.toktales.core.render.model.IRenderModel;
import com.tokelon.toktales.core.render.model.ISpriteModel;
import com.tokelon.toktales.core.util.INamedOptions;
import com.tokelon.toktales.core.util.IParams;
import com.tokelon.toktales.core.values.RenderDriverOptions;

import android.opengl.GLES20;

public class GLSpriteDriver implements IRenderDriver {
	
	public static final String TAG = "GLSpriteDriver";
	
	
	
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
	

	
	private Rectangle2iImpl rectSpriteSourceCoordsStatic = new Rectangle2iImpl();
	
	private ISprite lastTileSprite;
	

	private ShaderProgram mShader;
	
	private GLSpriteMesh spriteMesh;
	

	public GLSpriteDriver() {
		// TRIANGLE_FAN
		/*
		mFloatArrayVertices = new float[]
				{
					0.0f, 0.0f, 0.0f,
					0.0f, 1.0f, 0.0f,
					1.0f, 1.0f, 0.0f,
					1.0f, 0.0f, 0.0f
				};
		*/

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
			TokTales.getLog().e(TAG, "Failed to create shader program: " + oglex.getMessage());
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
		/* Phases:
		 * 1. Assume destination bounds being setup already
		 * 2. Setup source (texture) bounds
		 * 2.1 If sprite is not a texture yet, load into a new texture
		 * 3. Set handle values
		 * 4. Draw
		 * 5. Unset some handle values
		 * 
		 */


		if(!(renderModel instanceof ISpriteModel)) {
			throw new RenderException("Unsupported model type: " +renderModel.getClass());
		}
		ISpriteModel spriteModel = (ISpriteModel) renderModel;

		boolean ignoreSpriteset = (Boolean) options.getOrError(RenderDriverOptions.DRAWING_OPTION_IGNORE_SPRITESET);


		ISprite sprite = spriteModel.getSprite();
		IKeyedTextureManager<ISprite> textureManager = spriteModel.getTextureManager();


		// Before draw

		if(sprite.isEnclosed() && !ignoreSpriteset) {

			IRenderTexture finalTexture = textureManager.getTextureFor(sprite);
			if(finalTexture == null) {
				/* 1. Calculate Sprite coordinates in the Spriteset
				 * 2. Copy only the Sprite into a buffer bitmap
				 * 3. Load the buffer bitmap as a new texture
				 * 
				 */


				/* This contains complete source block without any "cutting" applied to it
				 * Scaling is not needed since we take the values directly from the sprite dimensions
				 */
				rectSpriteSourceCoordsStatic.set(0, 0, sprite.getSpriteset().getSpriteWidth(), sprite.getSpriteset().getSpriteHeight());



				/* This applies the offsets that are needed to get the actual indexed sprite from spriteset.
				 * 
				 */
				int offHor = sprite.getSpriteset().getHorizontalOffsetFor(sprite.getSpritesetIndex());
				int offVer = sprite.getSpriteset().getVerticalOffsetFor(sprite.getSpritesetIndex());


				// The always uncut block coords
				rectSpriteSourceCoordsStatic.moveBy(offHor, offVer);


				/* Copies the single Sprite we want to draw from the Spriteset
				 * This is done so that only the Sprite has to be loaded into the texture instead of the complete Spriteset
				 *
				 * TODO: Important - Instead of cropping the texture, use subTex
				 */
				IRenderTexture textureRegion = AndroidContentService.cropTextureStatic(spriteModel.getTexture(), rectSpriteSourceCoordsStatic);
				
				// Add the new texture (also binds it)
				textureManager.addTexture(sprite, textureRegion);
				finalTexture = textureRegion;
			}

			/* If we are going to support different texture sizes with different sized bitmaps
			 * then we would need a way of knowing with which size the texture was loaded here
			 * 
			 */
			//textureManager.getSavedSizeForTexture


			// TODO: This is not needed anymore - test first then remove
			// We assume we only have one size (max) for our buffer

			/* Because our buffer can be bigger than our sprite,
			 * we need to adjust the texture scaling to limit it to the actual sprite.
			 * 
			 * (We wouldn't have this problem if our buffer was always the size of the sprite (not the spriteset size))
			 * 
			 */
			float scalingHor = sprite.getSpriteset().getSpriteWidth() / (float) finalTexture.getBitmap().getWidth();
			float scalingVer = sprite.getSpriteset().getSpriteHeight() / (float) finalTexture.getBitmap().getHeight();

			// We apply the scaling on top of the scaling we can have because of rendering only part of a sprite
			spriteModel.scaleTexture(scalingHor, scalingVer);


			// And now we adjust the texture translation to the new scaling
			Vector2f texTranslation = spriteModel.getTextureTranslation();
			float scaledTranslationX = scalingHor * texTranslation.x;
			float scaledTranslationY = scalingVer * texTranslation.y;
			spriteModel.setTextureTranslation(scaledTranslationX, scaledTranslationY);

		}
		else {
			// In case the Sprite is not enclosed


			if(!(textureManager.hasTextureFor(sprite))) {

				// TODO: This will bind special assets the the given sprites. Fix that


				textureManager.addTexture(sprite, spriteModel.getTexture());
			}
		}


		// 1. Apply translation, rotation and scaling to the model matrix
		Matrix4f modelMatrix = spriteModel.applyModelMatrix();


		// 2. Apply texture coordinates to the float buffer
		float[] textureCoords = spriteModel.applyTextureCoordinates();
		FloatBuffer textureCoordinateBuffer = spriteMesh.setTextureCoords(textureCoords);



		mShader.setUniform("uModelMatrix", modelMatrix);
		mShader.setUniform("samplerTexture", spriteModel.getTextureManager().getTextureIndex());	//GL_TEXTUREi

		
		mShader.setAttribute("a_vTexCoord", 2, textureCoordinateBuffer);


		if(sprite.equals(lastTileSprite)) {
			// Do not change texture
		}
		else if(sprite.isEnclosed() && !ignoreSpriteset) {

			// Bind the texture for this sprite
			textureManager.bindTextureFor(sprite);
		}
		else {
			// Sprite is not enclosed or special asset
			// Use spriteBitmap instead of textureBufferBitmap


			textureManager.bindTextureFor(sprite);
		}


		// Draw

		// Draw 2 triangles with 4 points
		// 3 points define the first which is then connected to the 4th point forming another triangle
		GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, spriteMesh.getVertexCount());


		lastTileSprite = sprite;
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

		@Override
		public boolean supports(String target) {
			return supportedTarget().equals(target);
		}

		@Override
		public IRenderDriver newDriver(IParams params) {
			return new GLSpriteDriver();
		}
	}
	
}
