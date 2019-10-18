package com.tokelon.toktales.tools.core.sub.inject.config;

import java.util.Collection;

import com.google.inject.Module;

public class MasterInjectConfig extends HierarchicalInjectConfig {
	// TODO: Add interface?
	
	
	public MasterInjectConfig() {
		super();
	}

	@SafeVarargs
	public MasterInjectConfig(Class<? extends Module>... filterModules) {
		super(filterModules);
	}

	public MasterInjectConfig(Collection<Class<? extends Module>> filterModules) {
		super(filterModules);
	}

	
	// Maybe name these differently to avoid passing the wrong type
	public MasterInjectConfig extend(HierarchicalInjectConfig config) {
		extend(config.getModules());
		return this;
	}
	
	public MasterInjectConfig override(HierarchicalInjectConfig config) {
		override(config.getModules());
		return this;
	}
	
	public MasterInjectConfig filter(HierarchicalInjectConfig config) {
		filter(config.getFilterModules());
		return this;
	}
	

	// TODO: What's the order here: filter after?
	public MasterInjectConfig extendWithFilters(HierarchicalInjectConfig config) {
		extend(config.getModules());
		filter(config.getFilterModules());
		return this;
	}
	
	public MasterInjectConfig overrideWithFilters(HierarchicalInjectConfig config) {
		override(config.getModules());
		filter(config.getFilterModules());
		return this;
	}
	
}
