package com.tokelon.toktales.desktop.lwjgl.data;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.stb.STBVorbis;
import org.lwjgl.system.MemoryStack;

import com.google.common.io.ByteStreams;
import com.tokelon.toktales.core.content.manage.IAssetDecoder;
import com.tokelon.toktales.core.content.manage.sound.ISoundAsset;
import com.tokelon.toktales.core.content.manage.sound.ISoundAssetKey;
import com.tokelon.toktales.core.content.manage.sound.SoundAsset;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.util.options.INamedOptions;

public class STBVorbisSoundDecoder implements IAssetDecoder<ISoundAsset, ISoundAssetKey, INamedOptions> {

	
	@Override
	public ISoundAsset decode(InputStream inputstream, ISoundAssetKey key) throws ContentException {
		byte[] bytes;
		try {
			bytes = ByteStreams.toByteArray(inputstream);
		} catch (IOException e) {
			throw new ContentException(e);
		}
		
		int channels;
		int sampleRate;
		ShortBuffer rawAudioBuffer;
		
		try (MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer channelsBuffer = stack.mallocInt(1);
			IntBuffer sampleRateBuffer = stack.mallocInt(1);
			
			
			ByteBuffer buffer = stack.malloc(bytes.length);
			buffer.put(bytes);

			rawAudioBuffer = STBVorbis.stb_vorbis_decode_memory(buffer, channelsBuffer, sampleRateBuffer);


			//Retrieve the extra information that was stored in the buffers by the function
			channels = channelsBuffer.get();
			sampleRate = sampleRateBuffer.get();
		}
		
		return new SoundAsset(new STBSound(channels, sampleRate, rawAudioBuffer));
	}

	@Override
	public ISoundAsset decode(InputStream inputstream, ISoundAssetKey key, INamedOptions options) throws ContentException {
		return decode(inputstream, key);
	}

}
