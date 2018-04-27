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

import com.tokelon.toktales.core.content.sprite.ISprite;
import com.tokelon.toktales.core.engine.TokTales;
import com.tokelon.toktales.core.engine.content.IContentService;
import com.tokelon.toktales.core.game.model.Rectangle2iImpl;
import com.tokelon.toktales.core.render.IRenderDriver;
import com.tokelon.toktales.core.render.IRenderDriverFactory;
import com.tokelon.toktales.core.render.IRenderTexture;
import com.tokelon.toktales.core.render.RenderException;
import com.tokelon.toktales.core.render.model.IRenderModel;
import com.tokelon.toktales.core.render.model.ISpriteModel;
import com.tokelon.toktales.core.util.INamedOptions;
import com.tokelon.toktales.core.util.IParams;
import com.tokelon.toktales.core.values.RenderDriverOptions;
import com.tokelon.toktales.desktop.lwjgl.LWJGLException;
import com.tokelon.toktales.desktop.lwjgl.ShaderProgram;

public class GLSpriteDriver implements IRenderDriver {

	public static final String TAG = "GLSpriteDriver";

	
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
	
	
	private Rectangle2iImpl spriteSourceCoords = new Rectangle2iImpl();

	private GLSpriteMesh spriteMesh;
	
	private ShaderProgram mShader;

	private final FloatBuffer textureCoordinateBuffer;

	private final IContentService contentService;

	@Inject
	public GLSpriteDriver(IContentService contentService) {
		this.contentService = contentService;
		
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
			TokTales.getLog().e(TAG, "Failed to create shader program: " +e.getMessage());
			return;
		}
		
		
		

		float[] positions = new float[]{
				0.0f,  1.0f, -1.05f,
				0.0f, 0.0f, -1.05f,
				1.0f, 0.0f, -1.05f,
				1.0f,  1.0f, -1.05f,
		};
		/*
		float[] positions = new float[]{	// THESE WERE WRONG!!
				-0.5f,  0.5f, -1.05f,
				-0.5f, -0.5f, -1.05f,
				0.5f, -0.5f, -1.05f,
				0.5f,  0.5f, -1.05f,
		};
		*/
		

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
		
		
		ISprite sprite = spriteModel.getSprite();
		
		if(sprite.isEnclosed() && !ignoreSpriteset) {
			
			if(!spriteModel.getTextureManager().hasTextureFor(sprite)) {
				
				spriteSourceCoords.set(0, 0, sprite.getSpriteset().getSpriteWidth(), sprite.getSpriteset().getSpriteHeight());
				
				int spriteOffHor = sprite.getSpriteset().getHorizontalOffsetFor(sprite.getSpritesetIndex());
				int spriteOffVer = sprite.getSpriteset().getVerticalOffsetFor(sprite.getSpritesetIndex());
				
				spriteSourceCoords.moveBy(spriteOffHor, spriteOffVer);
				
				
				IRenderTexture textureRegion = contentService.cropTexture(spriteModel.getTexture(), spriteSourceCoords);
				spriteModel.getTextureManager().addTexture(sprite, textureRegion);
			}
			
			
			// THIS SHOULD BE FINE. IM SETTING IT IN THE RENDERER
			// NOTTODO: This only works for full tile rendering (not for the sides)
			// NOTTODO: Fix / Might be causing flickes on the sides / not rendering of sides
			//spriteModel.getTextureScaling().set(1.0f, 1.0f);
		}
		else {
			
			if(!spriteModel.getTextureManager().hasTextureFor(sprite)) {
				
				// TODO: This will bind special assets the the given sprites. Fix that
				
				spriteModel.getTextureManager().addTexture(sprite, spriteModel.getTexture());
			}
		}
		
		
		Matrix4f modelMatrix = spriteModel.applyModelMatrix();
		
		float[] textureCoordinates = spriteModel.applyTextureCoordinates();
		textureCoordinateBuffer.position(0);
		textureCoordinateBuffer.put(textureCoordinates).position(0);
		

		mShader.setUniform("uModelMatrix", modelMatrix);
		mShader.setUniform("samplerTexture", spriteModel.getTextureManager().getTextureIndex());
		
		spriteModel.getTextureManager().bindTextureFor(sprite);

		
		spriteMesh.setTextureCoords(textureCoordinateBuffer);

		

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
