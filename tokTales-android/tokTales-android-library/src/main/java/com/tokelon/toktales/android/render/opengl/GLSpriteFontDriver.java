package com.tokelon.toktales.android.render.opengl;

import java.nio.FloatBuffer;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector2f;

import com.tokelon.toktales.android.data.AndroidBitmap;
import com.tokelon.toktales.android.data.IAndroidBitmap;
import com.tokelon.toktales.android.render.opengl.program.OpenGLException;
import com.tokelon.toktales.android.render.opengl.program.ShaderProgram;
import com.tokelon.toktales.core.content.IBitmap;
import com.tokelon.toktales.core.content.sprite.ISprite;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.game.model.Rectangle2iImpl;
import com.tokelon.toktales.core.render.IKeyedTextureManager;
import com.tokelon.toktales.core.render.IRenderDriver;
import com.tokelon.toktales.core.render.IRenderDriverFactory;
import com.tokelon.toktales.core.render.IRenderTexture;
import com.tokelon.toktales.core.render.RenderException;
import com.tokelon.toktales.core.render.Texture;
import com.tokelon.toktales.core.render.model.IRenderModel;
import com.tokelon.toktales.core.render.model.ISpriteFontModel;
import com.tokelon.toktales.core.util.INamedOptions;
import com.tokelon.toktales.core.util.IParams;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.opengl.GLES20;

public class GLSpriteFontDriver implements IRenderDriver {
	
	public static final String TAG = "GLSpriteFontDriver";
	
	
	
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
	

	

	private ShaderProgram mShader;
	
	private GLSpriteMesh spriteMesh;

	
	
	private Rectangle2iImpl rectSpriteSourceCoordsStatic = new Rectangle2iImpl();
	
	private Bitmap bitmapTextureBuffer;
	private int[] intArrayTextureBufferPixels;
	
	private ISprite lastTileSprite;
	
	

	public GLSpriteFontDriver() {

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
			TokTales.getLog().e(TAG, "Failed to create shader program: " + oglex.getMessage());
			return;
		}
		

		
		int blockSize = 64;		// TODO: Pass max texture size dynamically
		
		bitmapTextureBuffer = Bitmap.createBitmap(blockSize, blockSize, Config.ARGB_8888);
		intArrayTextureBufferPixels = new int[blockSize * blockSize];
	}
	
	@Override
	public void destroy() {
		mShader.cleanup();
		mShader = null;
		
		bitmapTextureBuffer.recycle();
		bitmapTextureBuffer = null;
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
		
		ISprite fontSprite = fontModel.getSprite();
		
		IKeyedTextureManager<ISprite> textureManager = fontModel.getTextureManager();
		
		
		if(!(textureManager.hasTextureFor(fontSprite))) {
			
			
			rectSpriteSourceCoordsStatic.set(0, 0, fontSprite.getSpriteset().getSpriteWidth(), fontSprite.getSpriteset().getSpriteHeight());

			
			int offHor = fontSprite.getSpriteset().getHorizontalOffsetFor(fontSprite.getSpritesetIndex());
			int offVer = fontSprite.getSpriteset().getVerticalOffsetFor(fontSprite.getSpritesetIndex());


			rectSpriteSourceCoordsStatic.moveBy(offHor, offVer);


			if(rectSpriteSourceCoordsStatic.width() > bitmapTextureBuffer.getWidth()
					|| rectSpriteSourceCoordsStatic.height() > bitmapTextureBuffer.getHeight()) {

				// Our sprite bitmap is too big for our buffer!
				TokTales.getLog().e(TAG, "Cannot draw: Texture buffer (bitmap) does not fit loaded Bitmap!");
				throw new IllegalStateException(TAG +" | Texture buffer (bitmap) does not fit loaded Bitmap");
			}
			
			
			// TODO: Fix! Move resize logic into ContentService
			IRenderTexture texture = fontModel.getTexture();
			IBitmap bitmap = texture.getBitmap();
			if(!(bitmap instanceof IAndroidBitmap)) {
				throw new IllegalArgumentException("Bitmap type is not supported: use IAndroidBitmap");
			}
			IAndroidBitmap androidBitmap = (IAndroidBitmap) bitmap;
			Bitmap spriteBitmap = androidBitmap.getBitmap();


			spriteBitmap.getPixels(intArrayTextureBufferPixels, 0, rectSpriteSourceCoordsStatic.width(),
					rectSpriteSourceCoordsStatic.left(),
					rectSpriteSourceCoordsStatic.top(),
					rectSpriteSourceCoordsStatic.width(),
					rectSpriteSourceCoordsStatic.height());

			bitmapTextureBuffer.setPixels(intArrayTextureBufferPixels, 0, rectSpriteSourceCoordsStatic.width(),
					0,
					0,
					rectSpriteSourceCoordsStatic.width(),
					rectSpriteSourceCoordsStatic.height());


			AndroidBitmap regionBitmap = new AndroidBitmap(bitmapTextureBuffer);
			IRenderTexture textureRegion = new Texture(regionBitmap)
					.setTextureFormat(texture.getTextureFormat())
					.setInternalFormat(texture.getInternalFormat())
					.setDataType(texture.getDataType())
					.setUnpackAlignment(texture.getUnpackAlignment())
					.setFilter(texture.getFilterMin(), texture.getFilterMag())
					.setWrap(texture.getWrapS(), texture.getWrapT());
			

			// Add the new texture (also binds it)
			textureManager.addTexture(fontSprite, textureRegion);
		}

		
		
		
		// We assume we only have one size (max) for our buffer
		float scalingHor = fontSprite.getSpriteset().getSpriteWidth() / (float) bitmapTextureBuffer.getWidth();
		float scalingVer = fontSprite.getSpriteset().getSpriteHeight() / (float) bitmapTextureBuffer.getHeight();

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



		mShader.setUniform("uModelMatrix", modelMatrix);
		mShader.setUniform("samplerTexture", fontModel.getTextureManager().getTextureIndex());
		mShader.setUniform("colorOver", fontModel.getColor());
		
		mShader.setAttribute("a_vTexCoord", 2, textureCoordinateBuffer);

		
		if(fontSprite.equals(lastTileSprite)) {
			// Do not change texture
		}
		else {
			textureManager.bindTextureFor(fontSprite);	
		}
		
		GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, spriteMesh.getVertexCount());


		lastTileSprite = fontSprite;
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

		@Override
		public boolean supports(String target) {
			return supportedTarget().equals(target);
		}

		@Override
		public IRenderDriver newDriver(IParams params) {
			return new GLSpriteFontDriver();
		}
	}
	
	
}
