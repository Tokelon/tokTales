package com.tokelon.toktales.tools.core.sub.inject.config;

import java.util.Collection;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Stage;
import com.tokelon.toktales.tools.core.annotations.compatibility.CompatFunctionalInterface;

@CompatFunctionalInterface
public interface IInjectConfig {


	public Collection<Module> getModules();


	public default Injector createInjector() {
		return Guice.createInjector(getDefaultStage(), getModules());
	}

	public default Injector createInjector(Stage stage) {
		return Guice.createInjector(stage, getModules());
	}

	public default Stage getDefaultStage() {
		return Stage.PRODUCTION;
	}

}
