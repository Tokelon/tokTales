package com.tokelon.toktales.tools.core.sub.inject.config;

import java.util.Collection;

import com.google.inject.Module;
import com.google.inject.Stage;

public interface IHierarchicalInjectConfig extends IInjectConfig {


	/** Combines all current modules with the given ones.
	 * 
	 * @param modules
	 * @return
	 */
	public IHierarchicalInjectConfig extend(Module... modules);

	/** Combines all current modules with the given ones.
	 * 
	 * @param modules
	 * @return
	 */
	public IHierarchicalInjectConfig extend(Collection<Module> modules);

	/** Combines all current modules with the given ones, but marks the given ones as overrides.
	 * 
	 * @param modules
	 * @return
	 */
	public IHierarchicalInjectConfig override(Module... modules);

	/** Combines all current modules with the given ones, but marks the given ones as overrides.
	 * 
	 * @param modules
	 * @return
	 */
	public IHierarchicalInjectConfig override(Collection<Module> modules);


	/** Adds all given modules to the filter modules list. Any modules contained in the list will be filtered out in operations after this one.
	 * 
	 * @param modules
	 * @return
	 */
	public IHierarchicalInjectConfig filter(@SuppressWarnings("unchecked") Class<? extends Module>... modules);

	/** Adds all given modules to the filter modules list. Any modules contained in the list will be filtered out in operations after this one.
	 * 
	 * @param modules
	 * @return
	 */
	public IHierarchicalInjectConfig filter(Collection<Class<? extends Module>> modules);


	/** Sets the default stage.
	 * 
	 * @param stage The stage that will be used if none is provided.
	 * @return This object.
	 */
	public IHierarchicalInjectConfig setDefaultStage(Stage stage);

	
	/**
	 * @return The filter modules currently set.
	 */
	public Collection<Class<? extends Module>> getFilterModules();
	
}