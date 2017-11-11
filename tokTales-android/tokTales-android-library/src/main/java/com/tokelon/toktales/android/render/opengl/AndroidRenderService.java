package com.tokelon.toktales.android.render.opengl;

import com.tokelon.toktales.core.engine.AbstractEngineService;
import com.tokelon.toktales.core.engine.render.DefaultRenderAccess;
import com.tokelon.toktales.core.engine.render.DefaultSurfaceHandler;
import com.tokelon.toktales.core.engine.render.IRenderAccess;
import com.tokelon.toktales.core.engine.render.IRenderService;
import com.tokelon.toktales.core.engine.render.ISurfaceHandler;

public class AndroidRenderService extends AbstractEngineService implements IRenderService {

	
	private final DefaultSurfaceHandler mSurfaceHandler;
	private final DefaultRenderAccess mRenderAccess;
	
	public AndroidRenderService() {
		mSurfaceHandler = new DefaultSurfaceHandler();
		mRenderAccess = new DefaultRenderAccess();
	}
	
	
	@Override
	public ISurfaceHandler getSurfaceHandler() {
		return mSurfaceHandler;
	}


	@Override
	public IRenderAccess getRenderAccess() {
		return mRenderAccess;
	}
	
	
}
