package com.tokelon.toktales.core.content.audio;

import java.nio.ShortBuffer;

import com.tokelon.toktales.tools.core.dispose.IDisposable;

public interface ISound extends IDisposable {

	
	public int getChannels();
	
	public int getSampleRate();
	
	
	public ShortBuffer getData();
	
	public void getData(ShortBuffer buffer);
	
}
