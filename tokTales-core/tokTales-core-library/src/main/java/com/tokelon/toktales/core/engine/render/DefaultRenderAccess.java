package com.tokelon.toktales.core.engine.render;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tokelon.toktales.core.render.IRenderDriver;
import com.tokelon.toktales.core.render.IRenderDriverFactory;
import com.tokelon.toktales.core.render.IRenderToolkit;
import com.tokelon.toktales.core.render.IRenderToolkit.IRenderToolkitFactory;
import com.tokelon.toktales.core.util.IParams;
import com.tokelon.toktales.core.util.ParamsImpl;

public class DefaultRenderAccess implements IRenderAccess {


	private final ParamsImpl emptyParams = new ParamsImpl();


	private IRenderToolkitFactory toolkitFactory;
	
	private List<IRenderDriverFactory> drivers;
	
	public DefaultRenderAccess() {
		drivers = Collections.synchronizedList(new ArrayList<IRenderDriverFactory>());
	}
	
	
	
	@Override
	public IRenderToolkit requestToolkit() {
		return requestToolkit(emptyParams);
	}
	
	@Override
	public IRenderToolkit requestToolkit(IParams params) {
		return toolkitFactory == null ? null : toolkitFactory.newRenderToolkit(params);
	}
	
	@Override
	public void registerToolkit(IRenderToolkitFactory toolkitFactory) {
		this.toolkitFactory = toolkitFactory;
	}
	
	
	
	private IRenderDriverFactory findDriver(String modelType) {
		IRenderDriverFactory found = null;
		synchronized (drivers) {
			
			for(int i = 0; i < drivers.size(); i++) {
				IRenderDriverFactory driverFactory = drivers.get(i);
				
				if(driverFactory.supports(modelType)) {
					found = driverFactory;
					break;
				}
			}
		}
		
		return found;
	}
	

	@Override
	public IRenderDriver requestDriver(String supportTarget) {
		return requestDriver(supportTarget, emptyParams);
	}
	
	@Override
	public IRenderDriver requestDriver(String supportTarget, IParams params) {
		
		IRenderDriverFactory findResult = findDriver(supportTarget);
		return findResult == null ? null : findResult.newDriver(params);
	}

	@Override
	public boolean hasDriver(String supportTarget) {
		return findDriver(supportTarget) != null;
	}

	
	@Override
	public void registerDriver(IRenderDriverFactory driverFactory) {
		drivers.add(driverFactory);
	}

	@Override
	public void unregisterDriver(IRenderDriverFactory driverFactory) {
		drivers.remove(driverFactory);
	}

	@Override
	public boolean isRegisteredDriver(IRenderDriverFactory driverFactory) {
		return drivers.contains(driverFactory);
	}

}
