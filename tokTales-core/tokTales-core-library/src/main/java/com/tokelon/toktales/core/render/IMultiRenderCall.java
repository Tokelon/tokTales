package com.tokelon.toktales.core.render;

public interface IMultiRenderCall extends IRenderCall {


	//public void startMultiRender();

	/** Changes the current position to target the given values.
	 *
	 * @param layer The layer name of the calling context.
	 * @param position the stack position of the calling context.
	 */
	public void updatePosition(String layer, double position);

}
