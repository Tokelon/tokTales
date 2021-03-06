package com.tokelon.toktales.core.content.manage.codepoint;

import javax.inject.Inject;

import org.slf4j.ILoggerFactory;

import com.tokelon.toktales.core.content.text.IFont;
import com.tokelon.toktales.tools.assets.loader.IAssetLoader;
import com.tokelon.toktales.tools.assets.manager.DefaultAssetManager;
import com.tokelon.toktales.tools.assets.manager.IAssetStore;
import com.tokelon.toktales.tools.assets.manager.ISpecialAssetManager;
import com.tokelon.toktales.tools.core.objects.options.INamedOptions;
import com.tokelon.toktales.tools.core.objects.pools.SynchronizedPool;

public class CodepointAssetManager extends DefaultAssetManager<ICodepointAsset, ICodepointAssetKey, INamedOptions> implements ICodepointAssetManager {

	
	// TODO: Use a pool? Overhead for synchronization?
	private boolean useObjectPool = true;
	
	private final SynchronizedPool<MutableCodepointKey> codepointKeyPool = new SynchronizedPool<>(() -> new MutableCodepointKey(), 100);

	
	@Inject
	public CodepointAssetManager(ILoggerFactory loggerFactory, ISpecialAssetManager<ICodepointAsset> specialAssetManager, IAssetStore<ICodepointAsset, ICodepointAssetKey> assetStore, IAssetLoader<ICodepointAsset, ICodepointAssetKey, INamedOptions> assetLoader) {
		super(loggerFactory, specialAssetManager, assetStore, assetLoader);
	}


	@Override
	public ICodepointAsset getCodepointAsset(IFont font, int codepoint, float fontPixelHeight) {
		if(useObjectPool) {
			return getCodepointAssetInternal(font, codepoint, fontPixelHeight, null);
		}
		else {
			return getAsset(new CodepointAssetKeyImpl(font, codepoint, fontPixelHeight));
		}
	}

	@Override
	public ICodepointAsset getCodepointAsset(IFont font, int codepoint, float fontPixelHeight, INamedOptions options) {
		if(useObjectPool) {
			return getCodepointAssetInternal(font, codepoint, fontPixelHeight, options);
		}
		else {
			return getAsset(new CodepointAssetKeyImpl(font, codepoint, fontPixelHeight), options);
		}
	}

	protected ICodepointAsset getCodepointAssetInternal(IFont font, int codepoint, float fontPixelHeight, INamedOptions options) {
		MutableCodepointKey key = codepointKeyPool.newObject();
		key.setFont(font);
		key.setCodepoint(codepoint);
		key.setFontPixelHeight(fontPixelHeight);
		
		ICodepointAsset asset;
		try {
			asset = getStore().retrieve(key);
		} finally {
			key.setFont(null);
			codepointKeyPool.free(key);
		}
		
		if(asset == null) {
			CodepointAssetKeyImpl newKey = new CodepointAssetKeyImpl(font, codepoint, fontPixelHeight); // Pass new key instance
			return options == null ? getAsset(newKey) : getAsset(newKey, options);
		}
		else {
			return asset;
		}
	}


	protected class MutableCodepointKey implements ICodepointAssetKey { 
		private IFont font;
		private int codepoint;
		private float fontPixelHeight;

		public void setFont(IFont font) {
			this.font = font;
		}

		public void setCodepoint(int codepoint) {
			this.codepoint = codepoint;
		}
		
		public void setFontPixelHeight(float fontPixelHeight) {
			this.fontPixelHeight = fontPixelHeight;
		}


		@Override
		public IFont getFont() {
			return font;
		}

		@Override
		public int getCodepoint() {
			return codepoint;
		}

		@Override
		public float getFontPixelHeight() {
			return fontPixelHeight;
		}
		

		@Override
		public int hashCode() {
			return 13 + codepoint*37 + font.hashCode()*37 + (int)(fontPixelHeight*13);
		}

		@Override
		public boolean equals(Object obj) {
			if(obj == this) {
				return true;
			}
			if(!(obj instanceof ICodepointAssetKey)) {
				return false;
			}
			ICodepointAssetKey that = (ICodepointAssetKey) obj;

			return this.font.equals(that.getFont()) && this.codepoint == that.getCodepoint() && this.fontPixelHeight == that.getFontPixelHeight(); // Float comparison - use error value?
		}
	}

}
