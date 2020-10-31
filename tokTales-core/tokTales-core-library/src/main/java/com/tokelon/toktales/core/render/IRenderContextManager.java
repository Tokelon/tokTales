package com.tokelon.toktales.core.render;

import com.tokelon.toktales.core.render.renderer.IRenderer;

public interface IRenderContextManager {


	// TODO: Implement with abstract class
	public void addManagedRenderer(String name, IRenderer renderer); // what if the name is taken?
	public IRenderer getManagedRenderer(String name);
	public IRenderer removeManagedRenderer(String name); // better pass IRenderer instead of name?
	public boolean hasManagedRenderer(String name);

	// Do like above or like below?
	//public void addManagedRenderer(IRenderer renderer);
	//public boolean removeManagedRenderer(IRenderer renderer);

}
