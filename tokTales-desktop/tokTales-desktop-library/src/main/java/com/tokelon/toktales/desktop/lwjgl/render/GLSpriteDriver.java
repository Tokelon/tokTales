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
import com.tokelon.toktales.core.values.RenderDriverOptions;
import com.tokelon.toktales.desktop.lwjgl.LWJGLException;
import com.tokelon.toktales.desktop.lwjgl.ShaderProgram;
import com.tokelon.toktales.tools.core.objects.options.INamedOptions;
import com.tokelon.toktales.tools.core.objects.params.IParams;

public class GLSpriteDriver implements IRenderDriver {


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
			"void main() {\n" +
			"  gl_FragColor = texture2D(samplerTexture, exTexCoord);\n" +
			"}\n";
	
	/*
	"  vec4 color = texture2D(samplerTexture, exTexCoord);" +
	"  if(color.a <= 0.00001)" +
	"    discard;" +
	"  gl_FragColor = color;\n" +
	*/
	
	
	
	private GLSpriteMesh spriteMesh;
	
	private ShaderProgram mShader;
	
	private final FloatBuffer textureCoordinateBuffer;

	private final Map<ISprite, ITextureRegion> textureMap;

	private final ILogger logger;
	
	@Inject
	public GLSpriteDriver(ILogging logging) {
		logger = logging.getLogger(getClass());

		textureMap = new HashMap<>(); // Custom load factor etc?

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
		/* TODO: Check if we can optimize by only passing one single Matrix (modelViewProjection)
		 * instead of both model and projection view
		 * 
		 */
		
		
		mShader.bind();
		
		mShader.setUniform("uMVPMatrix", matrixProjectionView);


		glBindVertexArray(spriteMesh.getVaoId());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
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
		
		
		// Model matrix
		Matrix4f modelMatrix = spriteModel.applyModelMatrix();
		
		
		// Texture coordinate setup
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
		
		
		float[] textureCoordinates = spriteModel.applyTextureCoordinates();
		textureCoordinateBuffer.position(0);
		textureCoordinateBuffer.put(textureCoordinates).position(0);
		
		// Pass the texture coordinates to the GPU
		spriteMesh.setTextureCoords(textureCoordinateBuffer);


		int textureIndex = textureCoordinator.bindTexture(finalTexture);


		mShader.setUniform("uModelMatrix", modelMatrix);
		mShader.setUniform("samplerTexture", textureIndex);
		

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
