package com.tokelon.toktales.core.engine.inject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.google.inject.Module;
import com.google.inject.Stage;

public class SimpleInjectConfig implements IInjectConfig {

	private final Collection<Module> moduleCollection;

	private Stage defaultStage = Stage.PRODUCTION;


	public SimpleInjectConfig() {
		moduleCollection = new ArrayList<>();
	}

	public SimpleInjectConfig(Module... modules) {
		moduleCollection = new ArrayList<>(Arrays.asList(modules));
	}

	public SimpleInjectConfig(Collection<Module> modules) {
		moduleCollection = new ArrayList<>(modules);
	}


	/**
	 * @param stage
	 * @return This object.
	 */
	public SimpleInjectConfig setDefaultStage(Stage stage) {
		this.defaultStage = stage;
		return this;
	}

	@Override
	public Stage getDefaultStage() {
		return defaultStage;
	}

	@Override
	public Collection<Module> getModules() {
		return moduleCollection;
	}

}
