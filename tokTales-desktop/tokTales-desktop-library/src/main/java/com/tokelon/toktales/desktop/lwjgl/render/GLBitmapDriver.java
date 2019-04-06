package com.tokelon.toktales.desktop.lwjgl.render;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.nio.FloatBuffer;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.render.ITextureManager;
import com.tokelon.toktales.core.render.IRenderDriver;
import com.tokelon.toktales.core.render.IRenderDriverFactory;
import com.tokelon.toktales.core.render.ITexture;
import com.tokelon.toktales.core.render.ITextureCoordinator;
import com.tokelon.toktales.core.render.RenderException;
import com.tokelon.toktales.core.render.model.IManagedTextureModel;
import com.tokelon.toktales.core.render.model.IRenderModel;
import com.tokelon.toktales.core.util.IParams;
import com.tokelon.toktales.core.util.options.INamedOptions;
import com.tokelon.toktales.desktop.lwjgl.LWJGLException;
import com.tokelon.toktales.desktop.lwjgl.ShaderProgram;

public class GLBitmapDriver implements IRenderDriver {

	public static final String TAG = "GLBitmapDriver";
	

	private static final String VS_Bitmap = 
			"#version 330\n" +
			"layout(location = 0) in vec3 vPosition;\n" +
			"layout(location = 1) in vec2 vTexCoord;\n" +
			"out vec2 exTexCoord;\n" +
			"uniform mat4 uMVPMatrix;\n" +
			"uniform mat4 uModelMatrix;\n" +
			"void main() {\n" +
			"  gl_Position = uMVPMatrix * uModelMatrix * vec4(vPosition, 1.0);\n" +
			"  exTexCoord = vTexCoord;\n" +
			"}\n";
	
	private static final String FS_Bitmap = 
			"#version 330\n" +
			//"precision mediump float;" +
			"in vec2 exTexCoord;\n" +
			"uniform sampler2D samplerTexture;\n" +
			"void main() {\n" +
			"  gl_FragColor = texture2D(samplerTexture, exTexCoord);\n" +
			"}\n";

	
	private static final String MATRIX_MVP_NAME = "uMVPMatrix";
	private static final String MATRIX_MODEL_NAME = "uModelMatrix";
	private static final String SAMPLER_TEXTURE_NAME = "samplerTexture";
	

	private final FloatBuffer textureCoordinateBuffer;

	private GLSpriteMesh spriteMesh;
	
	private ShaderProgram mShader;
	
	
	@Inject
	public GLBitmapDriver() {
		textureCoordinateBuffer = BufferUtils.createFloatBuffer(8);
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
			
		} catch(LWJGLException ex) {
			TokTales.getLog().e(TAG, "Failed to create shader program: " +ex.getMessage());
			return;
		}
		

		float[] positions = new float[]{
				0.0f,  1.0f, -1.05f,
				0.0f, 0.0f, -1.05f,
				1.0f, 0.0f, -1.05f,
				1.0f,  1.0f, -1.05f,
		};
		

		int[] indices = new int[]{
				0, 1, 3, 3, 1, 2,
		};
		
		
		spriteMesh = new GLSpriteMesh(positions, indices);
		
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
		
		mShader.setUniform(MATRIX_MVP_NAME, matrixProjectionView);

		glBindVertexArray(spriteMesh.getVaoId());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
	}

	@Override
	public void draw(IRenderModel renderModel, INamedOptions options) {
		if(!(renderModel instanceof IManagedTextureModel)) {
			throw new RenderException("Unsupported model type: " +renderModel.getClass());
		}
		IManagedTextureModel texturedModel = (IManagedTextureModel) renderModel;
		
		
		ITexture texture = texturedModel.getTargetTexture();
		ITextureCoordinator textureCoordinator = texturedModel.getTextureCoordinator();
		ITextureManager textureManager = textureCoordinator.getTextureManager();
		
		textureManager.loadTexture(texture);

		
		Matrix4f modelMatrix = texturedModel.applyModelMatrix();

		float[] textureCoordinates = texturedModel.applyTextureCoordinates();
		textureCoordinateBuffer.position(0);
		textureCoordinateBuffer.put(textureCoordinates).position(0);
		
		spriteMesh.setTextureCoords(textureCoordinateBuffer);

		
		int textureIndex = textureCoordinator.bindTexture(texture);

		mShader.setUniform(MATRIX_MODEL_NAME, modelMatrix);
		mShader.setUniform(SAMPLER_TEXTURE_NAME, textureIndex);

		
		glDrawElements(GL_TRIANGLES, spriteMesh.getVertexCount(), GL_UNSIGNED_INT, 0);

	}

	
	@Override
	public void drawBatch(List<IRenderModel> modelList, INamedOptions options) {
		for(IRenderModel model: modelList) {
			draw(model, options);
		}
	}

	
	@Override
	public void release() {
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glBindVertexArray(0);
		
		mShader.unbind();		
	}
	

	@Override
	public boolean supports(String target) {
		return supportedTarget().equals(target);
	}
	
	private static String supportedTarget() {
		return IManagedTextureModel.class.getName();
	}
	
	
	public static class GLBitmapDriverFactory implements IRenderDriverFactory {
		private final Provider<GLBitmapDriver> driverProvider;
		
		@Inject
		public GLBitmapDriverFactory(Provider<GLBitmapDriver> driverProvider) {
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
