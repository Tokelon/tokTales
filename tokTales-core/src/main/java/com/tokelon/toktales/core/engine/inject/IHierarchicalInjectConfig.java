package com.tokelon.toktales.core.engine.inject;

import java.util.Collection;

import com.google.inject.Module;
import com.google.inject.Stage;

public interface IHierarchicalInjectConfig extends IInjectConfig {

	/** Combines all current modules with the given ones.
	 * <p>
	 * Note: When passing an array, do not modify it afterwards.
	 * 
	 * @param modules
	 * @return
	 */
	public HierarchicalInjectConfig extend(Module... modules);

	/** Combines all current modules with the given ones.
	 * 
	 * @param modules
	 * @return
	 */
	public HierarchicalInjectConfig extend(Collection<Module> modules);

	/** Combines all current modules with the given ones, but marks the given ones as overrides.
	 * <p>
	 * Note: When passing an array, do not modify it afterwards.
	 * 
	 * @param modules
	 * @return
	 */
	public HierarchicalInjectConfig override(Module... modules);

	/** Combines all current modules with the given ones, but marks the given ones as overrides.
	 * 
	 * @param modules
	 * @return
	 */
	public HierarchicalInjectConfig override(Collection<Module> modules);

	/** Sets the default stage.
	 * 
	 * @param stage The stage that will be used if none is provided.
	 * @return This object.
	 */
	public HierarchicalInjectConfig setDefaultStage(Stage stage);


}