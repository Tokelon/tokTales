package com.tokelon.toktales.desktop.content;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public final class ContentHelper {

	private ContentHelper() {
		throw new UnsupportedOperationException();
	}
	
	
	public static ByteBuffer readStreamIntoByteBuffer(InputStream stream, ByteBuffer buffer) throws IOException {
		
		try(ReadableByteChannel rbc = Channels.newChannel(stream);) {
			
			int bytes;
			do {
				bytes = rbc.read(buffer);	// This returns 0 when its unable to write into the buffer because its full
				
				//if(buffer.remaining() == 0)
				// resize buffer
				if(bytes == 0) {
					throw new IllegalArgumentException("Unable to read more data, buffer is full");
				}
			}
			while(bytes != -1);
		}
		finally {
			try {
				stream.close();
			} catch (IOException e) { /* No can do */ }
		}

		buffer.flip();
		
		return buffer;
	}
	
	
}
