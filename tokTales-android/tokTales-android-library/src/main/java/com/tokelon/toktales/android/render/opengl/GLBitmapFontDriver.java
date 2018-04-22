package com.tokelon.toktales.android.render.opengl;

import java.nio.FloatBuffer;
import java.util.List;

import org.joml.Matrix4f;

import com.tokelon.toktales.android.render.opengl.program.OpenGLException;
import com.tokelon.toktales.android.render.opengl.program.ShaderProgram;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.game.model.Rectangle2iImpl;
import com.tokelon.toktales.core.render.IRenderDriver;
import com.tokelon.toktales.core.render.IRenderDriverFactory;
import com.tokelon.toktales.core.render.IRenderTexture;
import com.tokelon.toktales.core.render.ITextureManager;
import com.tokelon.toktales.core.render.RenderException;
import com.tokelon.toktales.core.render.model.IRenderModel;
import com.tokelon.toktales.core.render.model.ITextureFontModel;
import com.tokelon.toktales.core.util.INamedOptions;
import com.tokelon.toktales.core.util.IParams;

import android.opengl.GLES20;

public class GLBitmapFontDriver implements IRenderDriver {
	
	public static final String TAG = "GLBitmapFontDriver";
	
	
	
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
	

	public GLBitmapFontDriver() {

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
		if(!(renderModel instanceof ITextureFontModel)) {
			throw new RenderException("Unsupported model type: " +renderModel.getClass());
		}
		ITextureFontModel fontModel = (ITextureFontModel) renderModel;

		
		IRenderTexture fontTexture = fontModel.getTargetTexture();
		ITextureManager textureManager = fontModel.getTextureManager();
		
		if(!(fontModel.getTextureManager().hasTexture(fontTexture))) {
			rectSpriteSourceCoordsStatic.set(0, 0, fontTexture.getBitmap().getWidth(), fontTexture.getBitmap().getHeight());

			rectSpriteSourceCoordsStatic.moveBy(0, 0);	// needed?
			
			// Add the new texture (also binds it)
			textureManager.addTexture(fontTexture);
		}

		

		Matrix4f modelMatrix = fontModel.applyModelMatrix();

		float[] textureCoords = fontModel.applyTextureCoordinates();
		FloatBuffer textureCoordinateBuffer = spriteMesh.setTextureCoords(textureCoords);



		mShader.setUniform("uModelMatrix", modelMatrix);
		mShader.setUniform("samplerTexture", fontModel.getTextureManager().getTextureIndex());
		mShader.setUniform("colorOver", fontModel.getColor());
		
		mShader.setAttribute("a_vTexCoord", 2, textureCoordinateBuffer);

		
		textureManager.bindTexture(fontTexture);
		
		
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
		return ITextureFontModel.class.getName();
	}
	
	
	public static class GLBitmapFontDriverFactory implements IRenderDriverFactory {

		@Override
		public boolean supports(String target) {
			return supportedTarget().equals(target);
		}

		@Override
		public IRenderDriver newDriver(IParams params) {
			return new GLBitmapFontDriver();
		}
	}
	
	
}
