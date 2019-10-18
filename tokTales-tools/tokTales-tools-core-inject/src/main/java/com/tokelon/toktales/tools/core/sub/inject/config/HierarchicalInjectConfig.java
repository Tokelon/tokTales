package com.tokelon.toktales.tools.core.sub.inject.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.google.inject.Module;
import com.google.inject.Stage;
import com.google.inject.util.Modules;

public class HierarchicalInjectConfig implements IHierarchicalInjectConfig {
	// TODO: Test configure and constructor parameter filters


	private boolean isConfigured = false;

	private Stage defaultStage = Stage.PRODUCTION;

	private List<Module> configModules = new ArrayList<>();

	private List<Class<? extends Module>> configFilterModules = new ArrayList<>();


	public HierarchicalInjectConfig() {
		configure();
	}

	@SafeVarargs
	public HierarchicalInjectConfig(Class<? extends Module>... filterModules) {
		configFilterModules.addAll(Arrays.asList(filterModules));
		configure();
	}

	public HierarchicalInjectConfig(Collection<Class<? extends Module>> filterModules) {
		configFilterModules.addAll(filterModules);
		configure();
	}


	/** Called when this inject config is created.
	 * <p>
	 * This is where you would call methods to configure your modules.<br>
	 * Note that at this point you cannot rely on this config being initialized.
	 * <p>
	 */
	protected void configure() {
		if(isConfigured) {
			throw new IllegalStateException("Config has already been configured");
		}

		isConfigured = true;
	}


	@Override
	public HierarchicalInjectConfig extend(Module... modules) {
		return extend(Arrays.asList(modules));
	}

	@Override
	public HierarchicalInjectConfig extend(Collection<Module> modules) {
		Collection<Module> filteredModules = filterModules(modules);
		configModules.addAll(filteredModules);
		return this;
	}


	@Override
	public HierarchicalInjectConfig override(Module... modules) {
		return override(Arrays.asList(modules));
	}

	@Override
	public HierarchicalInjectConfig override(Collection<Module> modules) {
		Collection<Module> filteredModules = filterModules(modules);
		if(configModules.isEmpty()) {
			configModules.addAll(filteredModules);
		}
		else {
			Module overrideModule = Modules.override(configModules).with(filteredModules);

			configModules = new ArrayList<>();
			configModules.add(overrideModule);    
		}

		return this;
	}


	@Override
	public IHierarchicalInjectConfig filter(@SuppressWarnings("unchecked") Class<? extends Module>... modules) {
		return filter(Arrays.asList(modules));
	}

	@Override
	public IHierarchicalInjectConfig filter(Collection<Class<? extends Module>> modules) {
		configFilterModules.addAll(modules);
		return this;
	}


	protected Collection<Module> filterModules(Collection<Module> modules) {
		if(modules.isEmpty()) {
			return modules;
		}
		
		List<Module> result = new ArrayList<>();
		for(Module module: modules) {
			boolean isFilterModule = false;
			
			for(Class<? extends Module> configFilterModule: configFilterModules) {
				if(configFilterModule.isInstance(module)) {
					isFilterModule = true;
					break;
				}
			}
			
			if(!isFilterModule) {
				result.add(module);
			}
		}

		return result;
	}


	@Override
	public HierarchicalInjectConfig setDefaultStage(Stage stage) {
		this.defaultStage = stage;
		return this;
	}

	@Override
	public Stage getDefaultStage() {
		return defaultStage;
	}

	@Override
	public Collection<Module> getModules() {
		return configModules;
	}

	@Override
	public Collection<Class<? extends Module>> getFilterModules() {
		return configFilterModules;
	}

}
