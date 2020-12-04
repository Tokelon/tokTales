package com.tokelon.toktales.core.render;

import java.util.List;

public interface IRenderContextManager extends IRenderContext {


	// Move into IRenderContext?
	public boolean isContextCreated();
	public boolean isContextValid();

	public void addContext(IRenderContext context);
	public boolean removeContext(IRenderContext context);
	public boolean hasContext(IRenderContext context);

	public List<IRenderContext> getContextList();

}
