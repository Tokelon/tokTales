package com.tokelon.toktales.desktop.lwjgl.render;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import com.tokelon.toktales.core.content.sprite.ISprite;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.engine.content.IContentService;
import com.tokelon.toktales.core.game.model.Rectangle2iImpl;
import com.tokelon.toktales.core.prog.annotation.Experimental;
import com.tokelon.toktales.core.prog.annotation.Unmaintained;
import com.tokelon.toktales.core.render.ITextureManager;
import com.tokelon.toktales.core.render.IRenderDriver;
import com.tokelon.toktales.core.render.IRenderDriverFactory;
import com.tokelon.toktales.core.render.ITexture;
import com.tokelon.toktales.core.render.ITextureCoordinator;
import com.tokelon.toktales.core.render.RenderException;
import com.tokelon.toktales.core.render.model.IRenderModel;
import com.tokelon.toktales.core.render.model.ISpriteFontModel;
import com.tokelon.toktales.core.util.IParams;
import com.tokelon.toktales.core.util.options.INamedOptions;
import com.tokelon.toktales.desktop.lwjgl.LWJGLException;
import com.tokelon.toktales.desktop.lwjgl.ShaderProgram;

@Unmaintained
@Experimental
public class GLSpriteFontDriver implements IRenderDriver {

	public static final String TAG = "GLSpriteFontDriver";

	
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
	
	
	
	private final FloatBuffer textureCoordinateBuffer;
	
	private Rectangle2iImpl spriteSourceCoords = new Rectangle2iImpl();
	
	
	private final Map<ISprite, ITexture> textureMap;

	private GLSpriteMesh spriteMesh;
	
	private ShaderProgram mShader;
	
	
	private final IContentService contentService;
	
	@Inject
	public GLSpriteFontDriver(IContentService contentService) {
		this.contentService = contentService;
		
		textureMap = new HashMap<>();

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
			TokTales.getLog().e(TAG, "Failed to create shader program: " +e.getMessage());
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
		if(!(renderModel instanceof ISpriteFontModel)) {
			throw new RenderException("Unsupported model type: " +renderModel.getClass());
		}
		ISpriteFontModel fontModel = (ISpriteFontModel) renderModel;
		
		
		ISprite fontSprite = fontModel.getTargetSprite();
		ITexture fontTexture = fontModel.getTargetTexture();
		
		ITextureCoordinator textureCoordinator = fontModel.getTextureCoordinator();
		ITextureManager globalTextureManager = textureCoordinator.getTextureManager();
		
		
		if(!fontSprite.isEnclosed()) {
			// What about special sprites? (error etc)
			//throw new RenderException("Font sprite must be enclosed");
		}
		
		
		ITexture finalTexture = textureMap.get(fontSprite);
		if(finalTexture == null) {
			
			spriteSourceCoords.set(0, 0, fontSprite.getSpriteset().getSpriteWidth(), fontSprite.getSpriteset().getSpriteHeight());
			
			int spriteOffHor = fontSprite.getSpriteset().getHorizontalOffsetFor(fontSprite.getSpritesetIndex());
			int spriteOffVer = fontSprite.getSpriteset().getVerticalOffsetFor(fontSprite.getSpritesetIndex());
			
			spriteSourceCoords.moveBy(spriteOffHor, spriteOffVer);

			
			ITexture textureRegion = contentService.cropTexture(fontTexture, spriteSourceCoords);
			
			textureMap.put(fontSprite, textureRegion);
			finalTexture = textureRegion;
		}
		
		globalTextureManager.loadTexture(finalTexture);
		
		
		Matrix4f modelMatrix = fontModel.applyModelMatrix();
		
		float[] textureCoordinates = fontModel.applyTextureCoordinates();
		textureCoordinateBuffer.position(0);
		textureCoordinateBuffer.put(textureCoordinates).position(0);
		
		spriteMesh.setTextureCoords(textureCoordinateBuffer);

		
		int textureIndex = textureCoordinator.bindTexture(finalTexture);

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
