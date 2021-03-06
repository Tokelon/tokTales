package com.tokelon.toktales.desktop.lwjgl.content;

import com.tokelon.toktales.core.content.graphics.IBitmap;

/** A bitmap loaded from the STB library.
 */
public interface ISTBBitmap extends IBitmap {

	
	/**
	 * @return The number of channels in this bitmap.
	 */
	public int getChannels();
	
	/**
	 * @return The number of channels in the original source.
	 */
	public int getSourceChannels();
	
}
