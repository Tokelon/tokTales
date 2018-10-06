package com.tokelon.toktales.core.engine.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Stage;

public class CoreDebugInjectModule extends AbstractInjectModule {

	
	@Override
	protected void configure() {
		AbstractModule debugModule;
		if(binder().currentStage() == Stage.DEVELOPMENT) {
			debugModule = new CoreDevInjectModule();
		}
		else {
			debugModule = new CoreProdInjectModule();
		}
		
		install(debugModule);
	}

}
