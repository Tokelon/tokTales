package com.tokelon.toktales.desktop.lwjgl.data;

import java.nio.ShortBuffer;

import org.lwjgl.system.libc.LibCStdlib;

import com.tokelon.toktales.core.content.audio.ISound;

public class STBSound implements ISound {
	
	
	private boolean disposed = false;
	
	private final int channels;
	private final int sampleRate;
	private ShortBuffer data;

	public STBSound(int channels, int sampleRate, ShortBuffer data) {
		this.channels = channels;
		this.sampleRate = sampleRate;
		this.data = data;
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
			
			//Free the memory allocated by STB
			LibCStdlib.free(data);
			
			data = null;	
		}
	}
	
}
