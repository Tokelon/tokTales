package com.tokelon.toktales.desktop.lwjgl.data;

import java.nio.ShortBuffer;

import com.tokelon.toktales.core.content.audio.ISound;
import com.tokelon.toktales.tools.core.dispose.IDisposer;

public class STBSound implements ISound {
	
	
	private boolean disposed = false;

	private ShortBuffer data;
	private final int channels;
	private final int sampleRate;
	private final IDisposer<STBSound> disposer;

	public STBSound(ShortBuffer data, int channels, int sampleRate, IDisposer<STBSound> disposer) {
		this.data = data;
		this.channels = channels;
		this.sampleRate = sampleRate;
		this.disposer = disposer;
	}
	
	
	@Override
	public int getChannels() {
		return channels;
	}

	@Override
	public int getSampleRate() {
		return sampleRate;
	}

	@Override
	public ShortBuffer getData() {
		return data;
	}

	@Override
	public void getData(ShortBuffer buffer) {
		buffer.put(data);
	}


	@Override
	public void dispose() {
		if(!disposed) {
			disposed = true;
			
			disposer.dispose(this);
			
			data = null;
		}
	}
	
}
