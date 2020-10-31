package com.tokelon.toktales.core.render;

import com.tokelon.toktales.tools.core.annotations.compatibility.CompatFunctionalInterface;

@CompatFunctionalInterface
public interface IRenderCall {


	/** Renders this call's content.
	 */
	public void render(); // int calls

	/**
	 * @return A brief description or name for this call.
	 */
	public default String getDescription() {
		return "Renders something";
	}

}
