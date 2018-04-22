package com.tokelon.toktales.android.render.opengl;

import java.nio.FloatBuffer;
import java.util.List;

import org.joml.Matrix4f;

import android.opengl.GLES20;

import com.tokelon.toktales.android.render.opengl.program.OpenGLException;
import com.tokelon.toktales.android.render.opengl.program.ShaderProgram;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.render.IKeyedTextureManager;
import com.tokelon.toktales.core.render.IRenderDriver;
import com.tokelon.toktales.core.render.IRenderDriverFactory;
import com.tokelon.toktales.core.render.IRenderTexture;
import com.tokelon.toktales.core.render.RenderException;
import com.tokelon.toktales.core.render.model.IRenderModel;
import com.tokelon.toktales.core.render.model.IRenderTextureModel;
import com.tokelon.toktales.core.util.INamedOptions;
import com.tokelon.toktales.core.util.IParams;

public class GLBitmapDriver implements IRenderDriver {
	
	private static final String TAG = "GLBitmapDriver";
	

	private static final String VS_Bitmap = 
			"uniform mat4 uMVPMatrix;" +
			"uniform mat4 uModelMatrix;" +
			"attribute vec4 vPosition;" +
			"attribute vec2 a_vTexCoord;" +
			"varying vec2 v_vTexCoord;" +
			"void main() {" +
			"  gl_Position = uMVPMatrix * uModelMatrix * vPosition;" +
			"  v_vTexCoord = a_vTexCoord;" +
			"}";
	
	private static final String FS_Bitmap = 
			"precision mediump float;" +
			"varying vec2 v_vTexCoord;" +
			"uniform sampler2D samplerTexture;" +
			"void main() {" +
			"  gl_FragColor = texture2D(samplerTexture, v_vTexCoord);" +
			"}";
	

	private static final String MATRIX_MVP_NAME = "uMVPMatrix";
	private static final String MATRIX_MODEL_NAME = "uModelMatrix";
	private static final String SAMPLER_TEXTURE_NAME = "samplerTexture";
	private static final String POSITION_NAME = "vPosition";
	private static final String TEXTURE_COORDINATE_NAME = "a_vTexCoord";

	
	private final IKeyedTextureManager<IRenderTexture> textureManager;
	
	private ShaderProgram mShader;
	
	private GLSpriteMesh bitmapMesh;


	public GLBitmapDriver() {
		
		textureManager = new GLKeyedTextureManager.GLKeyedTextureManagerFactory().newKeyedTextureManager(IRenderTexture.class, null);
		
		
		float[] vertices = new float[]
				{
					0.0f,  1.0f, 0.0f,
					0.0f, 0.0f, 0.0f,
					1.0f, 0.0f, 0.0f,
					1.0f,  1.0f, 0.0f,
				};
		
		bitmapMesh = new GLSpriteMesh(vertices);
	}
	
	@Override
	public void create() {

		try {
			mShader = new ShaderProgram();
			
			mShader.createVertexShader(VS_Bitmap);
			mShader.createFragmentShader(FS_Bitmap);
			
			mShader.link();
			
			
			mShader.createUniform(MATRIX_MVP_NAME);
			mShader.createUniform(MATRIX_MODEL_NAME);
			mShader.createUniform(SAMPLER_TEXTURE_NAME);
			
			mShader.createAttribute(POSITION_NAME);
			mShader.createAttribute(TEXTURE_COORDINATE_NAME);
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
		
		FloatBuffer vertexBuffer = bitmapMesh.getPositions();
		mShader.setAttribute(POSITION_NAME, 3, vertexBuffer);
		
		GLES20.glEnableVertexAttribArray(mShader.getAttributeLocation(POSITION_NAME));
		GLES20.glEnableVertexAttribArray(mShader.getAttributeLocation(TEXTURE_COORDINATE_NAME));
	}

	@Override
	public void draw(IRenderModel renderModel, INamedOptions options) {
		if(!(renderModel instanceof IRenderTextureModel)) {
			throw new RenderException("Unsupported model type: " +renderModel.getClass());
		}
		IRenderTextureModel texturedModel = (IRenderTextureModel) renderModel;
		
		
		textureManager.addTexture(texturedModel.getTargetTexture(), texturedModel.getTargetTexture());
		
		Matrix4f modelMatrix = texturedModel.applyModelMatrix();
		
		
		float[] textureCoordinates = texturedModel.applyTextureCoordinates();
		FloatBuffer textureCoordinateBuffer = bitmapMesh.setTextureCoords(textureCoordinates);

		mShader.setAttribute(TEXTURE_COORDINATE_NAME, 2, textureCoordinateBuffer);

		
		mShader.setUniform(MATRIX_MODEL_NAME, modelMatrix);
		mShader.setUniform(SAMPLER_TEXTURE_NAME, textureManager.getTextureIndex());
		
		textureManager.bindTextureFor(texturedModel.getTargetTexture());
		
		
		GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, bitmapMesh.getVertexCount());

		
		textureManager.removeTextureFor(texturedModel.getTargetTexture());
	}
	

	@Override
	public void drawBatch(List<IRenderModel> modelList, INamedOptions options) {
		for(IRenderModel model: modelList) {
			draw(model, options);
		}
	}
	

	@Override
	public void release() {
		GLES20.glDisableVertexAttribArray(mShader.getAttributeLocation(POSITION_NAME));
		GLES20.glDisableVertexAttribArray(mShader.getAttributeLocation(TEXTURE_COORDINATE_NAME));

		mShader.unbind();
	}
	

	@Override
	public boolean supports(String target) {
		return supportedTarget().equals(target);
	}

	private static String supportedTarget() {
		return IRenderTextureModel.class.getName();
	}
	
	
	public static class GLBitmapDriverFactory implements IRenderDriverFactory {

		@Override
		public boolean supports(String target) {
			return supportedTarget().equals(target);
		}

		@Override
		public IRenderDriver newDriver(IParams params) {
			return new GLBitmapDriver();
		}
	}
	

}
