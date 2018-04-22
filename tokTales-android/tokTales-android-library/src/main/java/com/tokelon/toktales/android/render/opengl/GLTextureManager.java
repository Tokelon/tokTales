package com.tokelon.toktales.android.render.opengl;

import com.tokelon.toktales.core.render.IRenderTexture;
import com.tokelon.toktales.core.render.ITextureManager;
import com.tokelon.toktales.core.render.ITextureManagerFactory;
import com.tokelon.toktales.core.util.IParams;

public class GLTextureManager implements ITextureManager {

	
	private final GLKeyedTextureManager<IRenderTexture> keyedManager;
	
	public GLTextureManager(int glTextureIndex) {
		this.keyedManager = new GLKeyedTextureManager<>(glTextureIndex);
	}
	
	
	@Override
	public void addTexture(IRenderTexture texture) {
		keyedManager.addTexture(texture, texture);
	}

	@Override
	public boolean hasTexture(IRenderTexture texture) {
		return keyedManager.hasTextureFor(texture);
	}

	@Override
	public void removeTexture(IRenderTexture texture) {
		keyedManager.removeTextureFor(texture);
	}

	@Override
	public void bindTexture(IRenderTexture texture) {
		keyedManager.bindTextureFor(texture);
	}

	@Override
	public void clear() {
		keyedManager.clear();
	}

	@Override
	public int getTextureIndex() {
		return keyedManager.getTextureIndex();
	}
	
	
	public static class GLTextureManagerFactory implements ITextureManagerFactory {
		
		@Override
		public ITextureManager newTextureManager(IParams params) {
			return new GLTextureManager(OpenGLUtils.aquireGLTextureIndex());
		}
	}

}
