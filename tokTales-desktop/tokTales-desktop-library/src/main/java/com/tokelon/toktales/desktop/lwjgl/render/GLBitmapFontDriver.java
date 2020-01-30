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

import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.core.game.model.Rectangle2iImpl;
import com.tokelon.toktales.core.render.IRenderDriver;
import com.tokelon.toktales.core.render.IRenderDriverFactory;
import com.tokelon.toktales.core.render.ITexture;
import com.tokelon.toktales.core.render.ITextureCoordinator;
import com.tokelon.toktales.core.render.ITextureManager;
import com.tokelon.toktales.core.render.RenderException;
import com.tokelon.toktales.core.render.model.IRenderModel;
import com.tokelon.toktales.core.render.model.IFontModel;
import com.tokelon.toktales.desktop.lwjgl.LWJGLException;
import com.tokelon.toktales.tools.core.objects.options.INamedOptions;
import com.tokelon.toktales.tools.core.objects.params.IParams;

public class GLBitmapFontDriver implements IRenderDriver {


	private static final String VS_Sprite = 
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
	
	private static final String FS_Sprite = 
			"#version 330\n" +
			//"precision mediump float;" +
			"in vec2 exTexCoord;\n" +
			"uniform sampler2D samplerTexture;\n" +
			"uniform vec4 colorOver;\n" +
			"void main() {\n" +
			"  vec4 texCol = texture2D(samplerTexture, exTexCoord);\n" +
			"  gl_FragColor = vec4(colorOver.x, colorOver.y, colorOver.z, texCol.w);\n" +
			"}\n";
	
	
	
	
	private GLSpriteMesh spriteMesh;
	
	private ShaderProgram mShader;
	
	private final Rectangle2iImpl spriteSourceCoords = new Rectangle2iImpl();
	
	private final ILogger logger;
	private final FloatBuffer textureCoordinateBuffer;
	
	@Inject
	public GLBitmapFontDriver(ILogging logging) {
		logger = logging.getLogger(getClass());

		textureCoordinateBuffer = BufferUtils.createFloatBuffer(8);
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
			
		} catch (LWJGLException e) {
			logger.error("Failed to create shader program:", e);
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
		
		mShader.setUniform("uMVPMatrix", matrixProjectionView);


		glBindVertexArray(spriteMesh.getVaoId());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
	}
	
	
	@Override
	public void draw(IRenderModel renderModel, INamedOptions options) {
		if(!(renderModel instanceof IFontModel)) {
			throw new RenderException("Unsupported model type: " +renderModel.getClass());
		}
		IFontModel fontModel = (IFontModel) renderModel;
		

		ITexture fontTexture = fontModel.getTargetTexture();
		ITextureCoordinator textureCoordinator = fontModel.getTextureCoordinator();
		ITextureManager textureManager = textureCoordinator.getTextureManager();
		
		if(!textureManager.hasTextureLoaded(fontTexture)) {
			spriteSourceCoords.set(0, 0, fontTexture.getBitmap().getWidth(), fontTexture.getBitmap().getHeight());
			
			textureManager.loadTexture(fontTexture);
		}
		
		
		
		Matrix4f modelMatrix = fontModel.applyModelMatrix();
		
		float[] textureCoordinates = fontModel.applyTextureCoordinates();
		textureCoordinateBuffer.position(0);
		textureCoordinateBuffer.put(textureCoordinates).position(0);
		
		spriteMesh.setTextureCoords(textureCoordinateBuffer);

		
		int textureIndex = textureCoordinator.bindTexture(fontTexture);

		mShader.setUniform("uModelMatrix", modelMatrix);
		mShader.setUniform("samplerTexture", textureIndex);
		mShader.setUniform("colorOver", fontModel.getTargetColor());
		

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
		return IFontModel.class.getName();
	}
	
	
	public static class GLBitmapFontDriverFactory implements IRenderDriverFactory {
		private final Provider<GLBitmapFontDriver> driverProvider;
		
		@Inject
		public GLBitmapFontDriverFactory(Provider<GLBitmapFontDriver> driverProvider) {
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
