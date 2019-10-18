package com.tokelon.toktales.tools.core.sub.inject.config;

public interface IMasterInjectConfig extends IHierarchicalInjectConfig {
	// TODO: Maybe name methods differently to avoid passing the wrong type


	public IMasterInjectConfig extend(IHierarchicalInjectConfig config);

	public IMasterInjectConfig override(IHierarchicalInjectConfig config);

	public IMasterInjectConfig filter(IHierarchicalInjectConfig config);

	
	public IMasterInjectConfig extendWithFilters(IHierarchicalInjectConfig config);

	public IMasterInjectConfig overrideWithFilters(IHierarchicalInjectConfig config);

}