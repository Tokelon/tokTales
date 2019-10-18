package com.tokelon.toktales.tools.core.sub.inject.config;

import java.util.Collection;

import com.google.inject.Module;

public class MasterInjectConfig extends HierarchicalInjectConfig implements IMasterInjectConfig {


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

	
	@Override
	public IMasterInjectConfig extend(IHierarchicalInjectConfig config) {
		extend(config.getModules());
		return this;
	}
	
	@Override
	public IMasterInjectConfig override(IHierarchicalInjectConfig config) {
		override(config.getModules());
		return this;
	}
	
	@Override
	public IMasterInjectConfig filter(IHierarchicalInjectConfig config) {
		filter(config.getFilterModules());
		return this;
	}
	

	// TODO: What's the order here: filter after?
	@Override
	public IMasterInjectConfig extendWithFilters(IHierarchicalInjectConfig config) {
		extend(config.getModules());
		filter(config.getFilterModules());
		return this;
	}
	
	@Override
	public IMasterInjectConfig overrideWithFilters(IHierarchicalInjectConfig config) {
		override(config.getModules());
		filter(config.getFilterModules());
		return this;
	}
	
}
