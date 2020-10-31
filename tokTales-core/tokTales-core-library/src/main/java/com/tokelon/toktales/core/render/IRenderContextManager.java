package com.tokelon.toktales.core.render;

import java.util.List;

public interface IRenderContextManager extends IRenderContext {


	// Move into IRenderContext?
	public boolean isCreated();
	public boolean isValid();

	public void addManagedRenderer(IRenderContext context);
	public boolean removeManagedRenderer(IRenderContext context);
	public boolean hasManagedRenderer(IRenderContext context);

	public List<IRenderContext> getContextList();

}
