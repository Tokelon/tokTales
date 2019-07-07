package com.tokelon.toktales.desktop.lwjgl.data;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.stb.STBVorbis;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import com.google.common.io.ByteStreams;
import com.tokelon.toktales.core.content.audio.ISound;
import com.tokelon.toktales.core.content.manage.sound.ISoundAsset;
import com.tokelon.toktales.core.content.manage.sound.ISoundAssetDecoder;
import com.tokelon.toktales.core.content.manage.sound.ISoundAssetKey;
import com.tokelon.toktales.core.content.manage.sound.SoundAsset;
import com.tokelon.toktales.core.engine.content.ContentException;
import com.tokelon.toktales.core.util.options.INamedOptions;
import com.tokelon.toktales.desktop.lwjgl.LWJGLException;

public class STBVorbisSoundDecoder implements ISoundAssetDecoder {


	@Override
	public ISoundAsset decode(InputStream inputstream, ISoundAssetKey key, INamedOptions options) throws ContentException {
		try {
			byte[] bytes = ByteStreams.toByteArray(inputstream);
			
			ByteBuffer buffer = MemoryUtil.memAlloc(bytes.length);
			try {
				buffer.put(bytes);
				buffer.flip();
				
				ISound sound = createSound(buffer);
				return new SoundAsset(sound);
			}
			finally {
				MemoryUtil.memFree(buffer);
			}
		} catch (IOException e) {
			throw new ContentException(e);
		} catch (LWJGLException lwe) {
			throw new ContentException(lwe);
		}
	}
	
	
	public ISound createSound(ByteBuffer buffer) throws LWJGLException {
		try (MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer channelsBuffer = stack.callocInt(1);
			IntBuffer sampleRateBuffer = stack.callocInt(1);
			
			ShortBuffer rawAudioBuffer = STBVorbis.stb_vorbis_decode_memory(buffer, channelsBuffer, sampleRateBuffer);
			if(rawAudioBuffer == null) {
				throw new LWJGLException("Failed to load sound"); // TODO: Append further information if possible
			}

			return new STBSound(rawAudioBuffer, channelsBuffer.get(0), sampleRateBuffer.get(0), (sound) -> MemoryUtil.memFree(sound.getData())); //Use LibCStdlib.free(data) ?
		}
	}

}
