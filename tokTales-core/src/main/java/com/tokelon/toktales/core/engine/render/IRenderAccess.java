package com.tokelon.toktales.core.engine.render;

import com.tokelon.toktales.core.render.IKeyedTextureManager;
import com.tokelon.toktales.core.render.IKeyedTextureManagerFactory;
import com.tokelon.toktales.core.render.IRenderDriver;
import com.tokelon.toktales.core.render.IRenderDriverFactory;
import com.tokelon.toktales.core.render.IRenderToolkit;
import com.tokelon.toktales.core.render.ITextureManager;
import com.tokelon.toktales.core.render.ITextureManagerFactory;
import com.tokelon.toktales.core.render.IRenderToolkit.IRenderToolkitFactory;
import com.tokelon.toktales.core.util.IParams;

public interface IRenderAccess {

	/*
	 * Maybe hide the register methods and don't have it that dynamic
	 */
	
	
	public <T> IKeyedTextureManager<T> requestKeyedTextureManager(Class<T> keyClass);
	public <T> IKeyedTextureManager<T> requestKeyedTextureManager(Class<T> keyClass, IParams params);
	public void registerKeyedTextureManager(IKeyedTextureManagerFactory keyedTextureManagerFactory);
	
	// Maybe just use the upper for this?
	public ITextureManager requestTextureManager();
	public ITextureManager requestTextureManager(IParams params);
	public void registerTextureManager(ITextureManagerFactory textureManagerFactory);
	
	
	public IRenderToolkit requestToolkit();
	public IRenderToolkit requestToolkit(IParams params);
	public void registerToolkit(IRenderToolkitFactory toolkitFactory);
	
	//public IRenderDriver requestDriverFor(Class modelClass);
	
	public IRenderDriver requestDriver(String supportTarget);
	public IRenderDriver requestDriver(String supportTarget, IParams params);
	public boolean hasDriver(String supportTarget);
	
	
	public void registerDriver(IRenderDriverFactory driverFactory);
	public void unregisterDriver(IRenderDriverFactory driverFactory);
	public boolean isRegisteredDriver(IRenderDriverFactory driverFactory);

	
}
